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
package org.jabylon.review.standard;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.jabylon.resources.persistence.PropertyPersistenceService;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class ReviewActivator extends Plugin implements BundleActivator {
    private static ReviewActivator INSTANCE;
    public static final String PLUGIN_ID = "org.jabylon.review.standard";
    private PropertyPersistenceService persistenceService;
    private ServiceReference<PropertyPersistenceService> persistenceReference;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        INSTANCE = this;

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        INSTANCE = null;
        persistenceService = null;
        if(persistenceReference!=null)
            context.ungetService(persistenceReference);
    }


    public static ReviewActivator getDefault() {
        return INSTANCE;
    }

    public PropertyPersistenceService getPersistenceService()
    {
        if(persistenceService==null)
        {
            persistenceReference = getBundle().getBundleContext().getServiceReference(PropertyPersistenceService.class);
            persistenceService = getBundle().getBundleContext().getService(persistenceReference);
        }
        return persistenceService;
    }
}
