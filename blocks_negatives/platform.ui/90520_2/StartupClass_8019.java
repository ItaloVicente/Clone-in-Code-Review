/*******************************************************************************
 * Copyright (c) 2000, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.api;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;

/**
 * This view is used to test the creation and restoration of
 * view state between sessions.
 */
public class SessionView extends MockViewPart {

    private IMemento memento;

    public static String VIEW_ID = "org.eclipse.ui.tests.api.SessionView";

    /**
     * Constructor for SessionView
     */
    public SessionView() {
        super();
    }

    /**
     * @see IViewPart#init(IViewSite, IMemento)
     */
    @Override
	public void init(IViewSite site, IMemento memento) {
        super.init(site, memento);
        this.memento = memento;
    }

    /**
     * Create an IMemento.
     */
    @Override
	public void saveState(IMemento memento) {
        createMementoState(memento);
    }

    /**
     * Creates an IMemento.
     */
    private void createMementoState(IMemento memento) {
        memento.putFloat("float", 0.50f);
        memento.putInteger("integer", 50);
        memento.putString("string", "50");

        IMemento child = memento.createChild("single");
        child.putInteger("id", 1);

        int count = 10;
        for (int nX = 0; nX < count; nX++) {
            child = memento.createChild("multiple");
            child.putInteger("id", nX);
        }
        memento.putInteger("multiple.count", count);
    }

    /**
     * Restore an IMemento.
     */
    public void testMementoState(TestCase testCase) {
        Assert.assertNotNull(memento);

        Float bigFloat = memento.getFloat("float");
        Assert.assertNotNull(bigFloat);
        Assert.assertEquals(bigFloat.floatValue(), 0.50f, 0.0001);

        Integer bigInt = memento.getInteger("integer");
        Assert.assertEquals(bigInt, Integer.valueOf(50));

        String str = memento.getString("string");
        Assert.assertEquals(str, "50");

        IMemento child = memento.getChild("single");
        Assert.assertNotNull(child);
        bigInt = child.getInteger("id");
        Assert.assertEquals(bigInt, Integer.valueOf(1));

        bigInt = memento.getInteger("multiple.count");
        Assert.assertNotNull(bigInt);
        int count = bigInt.intValue();
        IMemento[] children = memento.getChildren("multiple");
        Assert.assertEquals(count, children.length);
        for (int nX = 0; nX < count; nX++) {
            child = children[nX];
            Assert.assertNotNull(child);
            bigInt = child.getInteger("id");
            Assert.assertEquals(bigInt, Integer.valueOf(nX));
        }
    }
}

