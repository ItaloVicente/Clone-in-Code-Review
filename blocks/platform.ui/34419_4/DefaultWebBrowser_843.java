package org.eclipse.ui.internal.application;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class CompatibilityWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    private WorkbenchAdvisor wbAdvisor;

    public CompatibilityWorkbenchWindowAdvisor(WorkbenchAdvisor wbAdvisor, IWorkbenchWindowConfigurer windowConfigurer) {
        super(windowConfigurer);
        this.wbAdvisor = wbAdvisor;
    }

    @Override
	public void preWindowOpen() {
        wbAdvisor.preWindowOpen(getWindowConfigurer());
    }

    @Override
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new CompatibilityActionBarAdvisor(wbAdvisor, configurer);
    }
    
    @Override
	public void postWindowRestore() throws WorkbenchException {
        wbAdvisor.postWindowRestore(getWindowConfigurer());
    }

    @Override
	public void openIntro() {
        wbAdvisor.openIntro(getWindowConfigurer());
    }

    @Override
	public void postWindowCreate() {
        wbAdvisor.postWindowCreate(getWindowConfigurer());
    }

    @Override
	public void postWindowOpen() {
        wbAdvisor.postWindowOpen(getWindowConfigurer());
    }

    @Override
	public boolean preWindowShellClose() {
        return wbAdvisor.preWindowShellClose(getWindowConfigurer());
    }

    @Override
	public void postWindowClose() {
        wbAdvisor.postWindowClose(getWindowConfigurer());
    }

    public boolean isApplicationMenu(String menuId) {
        return wbAdvisor.isApplicationMenu(getWindowConfigurer(), menuId);
    }

    public IAdaptable getDefaultPageInput() {
        return wbAdvisor.getDefaultPageInput();
    }

    @Override
	public void createWindowContents(Shell shell) {
        wbAdvisor.createWindowContents(getWindowConfigurer(), shell);
    }

 
}
