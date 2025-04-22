package org.eclipse.ui.examples.views.properties.tabbed.logic;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class LogicCreationWizard
    extends Wizard
    implements INewWizard {

    private LogicWizardPage1 logicPage = null;

    private IStructuredSelection selection;

    private IWorkbench workbench;

    public void addPages() {
        logicPage = new LogicWizardPage1(workbench, selection);
        addPage(logicPage);
    }

    public void init(IWorkbench aWorkbench,
            IStructuredSelection currentSelection) {
        workbench = aWorkbench;
        selection = currentSelection;
    }

    public boolean performFinish() {
        return logicPage.finish();
    }

}
