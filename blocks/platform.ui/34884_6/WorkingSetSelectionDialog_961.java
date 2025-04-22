package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.dialogs.IWorkingSetNewWizard;
import org.eclipse.ui.dialogs.IWorkingSetPage;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.WorkingSetDescriptor;
import org.eclipse.ui.internal.registry.WorkingSetRegistry;

public class WorkingSetNewWizard extends Wizard implements IWorkingSetNewWizard {
    private WorkingSetTypePage workingSetTypePage;

    private IWorkingSetPage workingSetEditPage;

    private String editPageId;

    private IWorkingSet workingSet;
    
    private WorkingSetDescriptor[] descriptors;
    
    public WorkingSetNewWizard(WorkingSetDescriptor[] descriptors) {
        super();
        Assert.isTrue(descriptors != null && descriptors.length > 0);
        this.descriptors= descriptors;
        setWindowTitle(WorkbenchMessages.WorkingSetNewWizard_title);
    }

    @Override
	public void addPages() {
        super.addPages();

        IWizardPage page;
        WorkingSetRegistry registry = WorkbenchPlugin.getDefault().getWorkingSetRegistry();
        
        if (descriptors.length > 1) {
            page = workingSetTypePage = new WorkingSetTypePage(this.descriptors);
        } else {
            editPageId = descriptors[0].getId();
            page = workingSetEditPage = registry.getWorkingSetPage(editPageId);
        }
        page.setWizard(this);
        addPage(page);
        setForcePreviousAndNextButtons(descriptors.length > 1);
    }

    @Override
	public boolean canFinish() {
        return (workingSetEditPage != null && workingSetEditPage
                .isPageComplete());
    }

    @Override
	public IWizardPage getNextPage(IWizardPage page) {
        if (workingSetTypePage != null && page == workingSetTypePage) {
            String pageId = workingSetTypePage.getSelection();
            if (pageId != null) {
                if (workingSetEditPage == null || pageId != editPageId) {
                    WorkingSetRegistry registry = WorkbenchPlugin.getDefault()
                            .getWorkingSetRegistry();
                    workingSetEditPage = registry.getWorkingSetPage(pageId);
                    addPage(workingSetEditPage);
                    editPageId = pageId;
                }
                return workingSetEditPage;
            }
        }
        return null;
    }

    @Override
	public IWorkingSet getSelection() {
        return workingSet;
    }

    @Override
	public boolean performFinish() {
        workingSetEditPage.finish();
        workingSet = workingSetEditPage.getSelection();
        workingSet.setId(editPageId);
        return true;
    }    
}
