/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.util;

import org.apache.wicket.request.resource.ResourceReference;

public class GlobalResources {

    /**
     * bootstrap integration for datatables
     */
    public static final ResourceReference JS_BOOTSTRAP_DATATABLES = new WebContextUrlResourceReference("js/datatables/dataTables.bootstrap.js");

    /**
     * JQuery portion of datatables
     */
    public static final ResourceReference JS_JQUERY_DATATABLES = new WebContextUrlResourceReference("js/datatables/jquery.datatables.min.js");

    /**
     * custom sort functions for datatables
     */
    public static final ResourceReference JS_DATATABLES_CUSTOMSORT = new WebContextUrlResourceReference("js/datatables/custom.sorting.js");

    /**
     * keyboard shortcuts JS library
     */
    public static final ResourceReference JS_SHORTCUTS = new WebContextUrlResourceReference("js/openjs/shortcuts.js");

    /**
     * shows a confirmation dialog before leaving a page with modifications
     */
    public static final ResourceReference JS_WARN_WHEN_DIRTY = new WebContextUrlResourceReference("js/warnWhenDirty.js");

}
