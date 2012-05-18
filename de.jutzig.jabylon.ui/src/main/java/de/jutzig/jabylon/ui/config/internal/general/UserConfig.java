package de.jutzig.jabylon.ui.config.internal.general;

import java.util.HashSet;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.osgi.service.prefs.Preferences;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.components.Section;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.config.ConfigSection;
import de.jutzig.jabylon.ui.container.GenericEObjectContainer;
import de.jutzig.jabylon.ui.styles.JabylonStyle;
import de.jutzig.jabylon.users.Permission;
import de.jutzig.jabylon.users.Role;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UserManagement;
import de.jutzig.jabylon.users.UsersFactory;
import de.jutzig.jabylon.users.UsersPackage;

public class UserConfig extends AbstractConfigSection<Workspace> implements ConfigSection {
	Table userTable = null;
	User selectedUser = null;
	VerticalLayout userConfig = new VerticalLayout();
	Section userDetails = null;
	UserManagement userManagement = null;

	@SuppressWarnings("serial")
	@Override
	public Component createContents() {

		userConfig.setMargin(true);
		userTable = new Table("Users");
		userTable.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());

		userTable.setSizeFull();
		userTable.setSelectable(true);
		userTable.addListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				removeUserDetails();

				selectedUser = (User) event.getItemId();
				if (selectedUser != null)
					addUserDetails();
			}
		});
		userConfig.addComponent(userTable);
		HorizontalLayout buttonLine = new HorizontalLayout();
		Button addUser = new Button("Add User");
		addUser.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addUser();
			}
		});
		Button deleteUser = new Button("Delete User");
		deleteUser.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				deleteUser();
			}
		});
		buttonLine.addComponent(addUser);
		buttonLine.addComponent(deleteUser);
		userConfig.addComponent(buttonLine);
		return userConfig;
	}

	private void removeUserDetails() {
		if (userDetails == null)
			return;
		userConfig.removeComponent(userDetails);
		userDetails = null;
	}

	private void addUserDetails() {
		userDetails = new Section();
		userDetails.setCaption("User: " + selectedUser.getName());
		TwinColSelect permissionSelect = new TwinColSelect("Permissions");
		permissionSelect.setMultiSelect(true);
		permissionSelect.setLeftColumnCaption("Available permissions");
		permissionSelect.setRightColumnCaption("Permissions currently assigned");
		permissionSelect.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				System.out.println(event.getProperty());
			}
		});
		GenericEObjectContainer<Permission> ds = new GenericEObjectContainer<Permission>(userManagement,
				UsersPackage.Literals.USER_MANAGEMENT__PERMISSIONS);
		permissionSelect.setContainerDataSource(ds);
		permissionSelect.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		permissionSelect.setItemCaptionPropertyId(UsersPackage.Literals.PERMISSION__DESCRIPTION);
		permissionSelect.setValue(new HashSet<Permission>(selectedUser.getAllPermissions()));
		userDetails.addComponent(permissionSelect);
		userConfig.addComponent(userDetails);
	}

	private void addUser() {
		final Window addUser = new Window("Add user");
		addUser.setModal(true);
		addUser.setHeight("180px");
		addUser.setWidth("280px");
		final Form addUserForm = new Form();
		User user = UsersFactory.eINSTANCE.createUser();
		addUserForm.setItemDataSource(new BeanItem<User>(user));
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(addUserForm);
		Button ok = new Button("Submit");
		ok.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				CDOTransaction transaction = (CDOTransaction) getDomainObject().cdoView();
				CDOResource resource = transaction.getOrCreateResource(ServerConstants.USERS_RESOURCE);
				resource.getContents().add(((BeanItem<User>) addUserForm.getItemDataSource()).getBean());
				MainDashboard.getCurrent().getMainWindow().removeWindow(addUser);
			}
		});
		layout.addComponent(ok);
		layout.setMargin(true);
		addUser.setContent(layout);
		MainDashboard.getCurrent().getMainWindow().addWindow(addUser);
	}

	private void deleteUser() {
		if (selectedUser == null) {
			MainDashboard.getCurrent().getMainWindow()
					.showNotification("No user select", "Please select a usser", Notification.TYPE_WARNING_MESSAGE);
			return;
		} else {
			CDOTransaction transaction = (CDOTransaction) getDomainObject().cdoView();
			CDOResource resource = transaction.getOrCreateResource(ServerConstants.USERS_RESOURCE);
			resource.getContents().remove(selectedUser);
			selectedUser = null;

		}
	}

	private Container getUsers() {
		return new GenericEObjectContainer<User>(userManagement, UsersPackage.Literals.USER_MANAGEMENT__USERS);
	}

	@Override
	public void commit(Preferences config) {

	}

	@Override
	protected void init(Preferences config) {
		userManagement = (UserManagement)getDomainObject().cdoView().getResource(ServerConstants.USERS_RESOURCE).getContents().get(0);
		userTable.setContainerDataSource(getUsers());
		userTable.setVisibleColumns(new Object[] { UsersPackage.Literals.USER__NAME });
		userTable.setColumnHeader(UsersPackage.Literals.USER__NAME, "Username");
		userTable.addGeneratedColumn("Roles", new ColumnGenerator() {
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				return getRolesList(((User) itemId).getRoles());
			}

			public String getRolesList(EList<Role> roles) {
				StringBuffer sb = new StringBuffer();
				for (Role role : roles)
					sb.append(role.getName()).append(", ");
				return sb.toString();
			}
		});
	}

}
