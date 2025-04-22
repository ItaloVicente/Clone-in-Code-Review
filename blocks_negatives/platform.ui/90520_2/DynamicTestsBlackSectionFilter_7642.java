/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.tests.views.properties.tabbed.decorations.views;

import org.eclipse.ui.tests.views.properties.tabbed.decorations.TabbedPropertySheetPageWithDecorations;
import org.eclipse.ui.tests.views.properties.tabbed.views.TestsView;
import org.eclipse.ui.views.properties.IPropertySheetPage;

public class DecorationTestsView extends TestsView {


    @Override
	public Object getAdapter(Class adapter) {
        if (adapter == IPropertySheetPage.class) {
            if (tabbedPropertySheetPage == null) {
                tabbedPropertySheetPage = new TabbedPropertySheetPageWithDecorations(this);
            }
            return tabbedPropertySheetPage;
        }
        return super.getAdapter(adapter);
    }

}
