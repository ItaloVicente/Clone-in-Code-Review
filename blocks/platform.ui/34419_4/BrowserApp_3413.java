package org.eclipse.ui.examples.rcp.browser;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class BrowserAdvisor extends WorkbenchAdvisor {

	public BrowserAdvisor() {
	}
	
    public void initialize(IWorkbenchConfigurer configurer) {
        super.initialize(configurer);
    }
    
	public String getInitialWindowPerspectiveId() {
		return IBrowserConstants.BROWSER_PERSPECTIVE_ID;
	}
    
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
            IWorkbenchWindowConfigurer configurer) {
        return new BrowserWindowAdvisor(configurer);
    }
}
