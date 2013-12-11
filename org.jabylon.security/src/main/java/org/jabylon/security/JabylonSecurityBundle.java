/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.security;

import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.jabylon.cdo.connector.RepositoryConnector;


public class JabylonSecurityBundle implements BundleActivator {
    public static final String BUNDLE_ID = "org.jabylon.security";

    private static BundleContext context;
    private static RepositoryConnector repositoryConnector;
    private CDOTransaction transaction = null;

    @Override
    public void start(BundleContext context) throws Exception {
        JabylonSecurityBundle.context = context;
    }


    @Override
    public void stop(BundleContext context) throws Exception {
        JabylonSecurityBundle.context = context;
    }

    public static BundleContext getBundleContext() {
        return JabylonSecurityBundle.context;
    }

    public void setRepositoryConnector(RepositoryConnector repositoryConnector) {
        JabylonSecurityBundle.repositoryConnector = repositoryConnector;
    }

    public void unsetRepositoryConnector(RepositoryConnector repositoryConnector) {
        JabylonSecurityBundle.repositoryConnector = null;
    }

    public static RepositoryConnector getRepositoryConnector() {
        return JabylonSecurityBundle.repositoryConnector;
    }
}
