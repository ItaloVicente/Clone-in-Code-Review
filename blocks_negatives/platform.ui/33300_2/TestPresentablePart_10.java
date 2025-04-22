/*******************************************************************************
 * Copyright (c) 2004, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.performance.presentations;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * @since 3.1
 */
public class StandaloneViewPerspective2 implements IPerspectiveFactory {

    /* (non-Javadoc)
     * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
     */
    public void createInitialLayout(IPageLayout layout) {
        layout.addStandaloneView(IPageLayout.ID_BOOKMARKS, false, 
                IPageLayout.LEFT, 0.5f, IPageLayout.ID_EDITOR_AREA);
        
        layout.addStandaloneView(IPageLayout.ID_PROBLEM_VIEW, false, 
                IPageLayout.RIGHT, 0.5f, IPageLayout.ID_EDITOR_AREA);
        
        layout.addStandaloneView(IPageLayout.ID_OUTLINE, false, IPageLayout.BOTTOM, 0.5f, IPageLayout.ID_EDITOR_AREA);
    }
}
