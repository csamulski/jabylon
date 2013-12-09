package org.jabylon.properties.util.scanner;

import java.io.File;
import java.util.Locale;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.ScanConfiguration;
import org.jabylon.properties.types.PropertyScanner;

public abstract class AbstractScanFileAcceptor implements PropertyFileAcceptor{

    /**
     *
     */
    private final ProjectVersion projectVersion;
    private PropertyScanner scanner;
    private ScanConfiguration config;
    private URI versionPath;

    public AbstractScanFileAcceptor(ProjectVersion projectVersion, PropertyScanner scanner, ScanConfiguration config) {
        this.projectVersion = projectVersion;
        this.scanner = scanner;
        this.config = config;
        versionPath = projectVersion.absolutPath();
    }


    protected URI calculateLocation(File file) {
        URI location = URI.createFileURI(file.getAbsolutePath());
        location = location.deresolve(versionPath); // get rid of the
                                                        // version
        location = URI.createHierarchicalURI(location.scheme(), location.authority(), location.device(), location.segmentsList()
                .subList(1, location.segmentCount()).toArray(new String[location.segmentCount() - 1]), location.query(),
                location.fragment());
        return location;
    }

    protected PropertyFileDescriptor createDescriptor(ProjectLocale projectLocale, URI childURI) {
        PropertyFileDescriptor fileDescriptor = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
        fileDescriptor.setLocation(childURI);
        fileDescriptor.setName(childURI.lastSegment());
        fileDescriptor.setVariant(projectLocale.getLocale());
        projectLocale.getDescriptors().add(fileDescriptor);
        Resolvable<?, Resolvable<?, ?>> parent = getOrCreateParent(projectLocale, childURI);
        parent.getChildren().add(fileDescriptor);
        return fileDescriptor;
    }

    private Resolvable<?, Resolvable<?, ?>> getOrCreateParent(ProjectLocale projectLocale, URI childURI) {
        Resolvable<?, Resolvable<?, ?>> currentParent = projectLocale;
        String[] segments = childURI.segments();
        for (int i = 0; i < segments.length - 1; i++) {
            currentParent = getOrCreate(currentParent, URI.decode(segments[i]));
        }
        return currentParent;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Resolvable<?, Resolvable<?, ?>> getOrCreate(Resolvable<?, Resolvable<?, ?>> currentParent, String child) {
        Resolvable<?, Resolvable<?, ?>> childObject = (Resolvable<?, Resolvable<?, ?>>) currentParent.getChild(child);
        if (childObject == null) {
            childObject = PropertiesFactory.eINSTANCE.createResourceFolder();
            childObject.setName(child);
            EList children = currentParent.getChildren();
            children.add(childObject);
        }
        return childObject;
    }

    public ProjectLocale getOrCreateProjectLocale(Locale locale) {
        ProjectLocale projectLocale = getProjectVersion().getProjectLocale(locale);
        if (projectLocale == null) {
            projectLocale = PropertiesFactory.eINSTANCE.createProjectLocale();
            projectLocale.setLocale(locale);
            getProjectVersion().getChildren().add(projectLocale);
        }
        return projectLocale;
    }

    public ScanConfiguration getScanConfig() {
        return config;
    }

    public ProjectVersion getProjectVersion() {
        return projectVersion;
    }

    public PropertyScanner getPropertyScanner() {
        return scanner;
    }

}