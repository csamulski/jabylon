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

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

import com.google.common.base.Function;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class ComputableModel<F,T> implements IModel<T>, IDetachable {

    private static final long serialVersionUID = 1L;

    private Function<F, T> loadingFunction;

    private transient T result;

    private F seed;

    public ComputableModel(Function<F, T> loadingFunction, F seed) {
        super();
        this.loadingFunction = loadingFunction;
        this.seed = seed;
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.model.IDetachable#detach()
     */
    @Override
    public void detach() {
        result = null;

    }

    @Override
    public T getObject() {
        if(result == null)
            result = loadingFunction.apply(seed);
        return result;
    }

    @Override
    public void setObject(T object) {
        throw new UnsupportedOperationException("not yet implemented");

    }

}
