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
package org.jabylon.resources.persistence.internal;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.view.CDOView;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;

import com.google.common.cache.CacheLoader;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class PropertyFileCacheLoader extends CacheLoader<CDOID, PropertyFile> {

    private CDOView view;

    public PropertyFileCacheLoader(CDOView view) {
        this.view = view;
    }


    @Override
    public PropertyFile load(CDOID key) throws Exception {
        CDOObject object = view.getObject(key);
        if (object instanceof PropertyFileDescriptor) {
            PropertyFileDescriptor descriptor = (PropertyFileDescriptor) object;
            PropertyFile properties = descriptor.loadProperties();
            return properties;
        }
        throw new IllegalArgumentException("Object is not a PropertyFileDescriptor: "+object);
    }

}
