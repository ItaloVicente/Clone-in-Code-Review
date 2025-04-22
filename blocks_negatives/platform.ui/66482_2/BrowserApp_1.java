/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.rcp.browser;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * The workbench advisor for the browser example.
 * Configures the workbench as needed for the browser example, including
 * specifying the default perspective id.
 * Creates the workbench window advisor for configuring each new window 
 * as it is being opened.
 * 
 * @since 3.0
 */
public class BrowserAdvisor extends WorkbenchAdvisor {

	/**
	 * Constructs a new <code>BrowserAdvisor</code>.
	 */
	public BrowserAdvisor() {
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.application.WorkbenchAdvisor
     */
    public void initialize(IWorkbenchConfigurer configurer) {
        super.initialize(configurer);
    }
    
	/* (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchAdvisor
	 */
	public String getInitialWindowPerspectiveId() {
		return IBrowserConstants.BROWSER_PERSPECTIVE_ID;
	}
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.application.WorkbenchAdvisor
     */
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
            IWorkbenchWindowConfigurer configurer) {
        return new BrowserWindowAdvisor(configurer);
    }
}
