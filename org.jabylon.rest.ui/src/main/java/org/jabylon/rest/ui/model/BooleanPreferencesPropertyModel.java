/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.rest.ui.model;

import org.apache.wicket.model.Model;
import org.osgi.service.prefs.Preferences;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class BooleanPreferencesPropertyModel extends Model<Boolean> {

    private static final long serialVersionUID = 8851885506195822461L;
    private Preferences prefs;

    public BooleanPreferencesPropertyModel(Preferences prefs, String key, boolean defaultValue) {
        super();
        this.prefs = prefs;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    private String key;
    private boolean defaultValue;

    @Override
    public Boolean getObject() {
        return prefs.getBoolean(key, defaultValue);
    }

    @Override
    public void setObject(Boolean object) {
        prefs.putBoolean(key, object);
    }


}
