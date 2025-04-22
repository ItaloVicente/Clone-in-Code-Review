package org.eclipse.ui.internal.application;

import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;

public class CompatibilityActionBarAdvisor extends ActionBarAdvisor {

    private WorkbenchAdvisor wbAdvisor;

    public CompatibilityActionBarAdvisor(WorkbenchAdvisor wbAdvisor, IActionBarConfigurer configurer) {
        super(configurer);
        this.wbAdvisor = wbAdvisor;
    }

    @Override
	public void fillActionBars(int flags) {
        IActionBarConfigurer abc = getActionBarConfigurer();
        wbAdvisor.fillActionBars(abc.getWindowConfigurer().getWindow(), abc, flags);
    }
    
    @Override
	public boolean isApplicationMenu(String menuId) {
        IActionBarConfigurer abc = getActionBarConfigurer();
        return wbAdvisor.isApplicationMenu(abc.getWindowConfigurer(), menuId);
    }
}
