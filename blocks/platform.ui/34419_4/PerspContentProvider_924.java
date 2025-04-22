package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.activities.ws.WorkbenchTriggerPoints;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;

class NewWizardSelectionPage extends WorkbenchWizardSelectionPage {
    private IWizardCategory wizardCategories;

    private NewWizardNewPage newResourcePage;

    private IWizardDescriptor [] primaryWizards;

	private boolean projectsOnly;
	
	private boolean canFinishEarly = false, hasPages = true;
    
    public NewWizardSelectionPage(IWorkbench workbench,
			IStructuredSelection selection, IWizardCategory root,
			IWizardDescriptor[] primary, boolean projectsOnly) {
        super("newWizardSelectionPage", workbench, selection, null, WorkbenchTriggerPoints.NEW_WIZARDS);//$NON-NLS-1$
        
        setTitle(WorkbenchMessages.NewWizardSelectionPage_description); 
        wizardCategories = root;
        primaryWizards = primary;
        this.projectsOnly = projectsOnly;
	}

    public void advanceToNextPageOrFinish() {
    		if (canFlipToNextPage()) {
				getContainer().showPage(getNextPage());
			} else if (canFinishEarly()) {
    			if (getWizard().performFinish()) {
					((WizardDialog)getContainer()).close();
				}
    		}
    }

    @Override
	public void createControl(Composite parent) {
        IDialogSettings settings = getDialogSettings();
        newResourcePage = new NewWizardNewPage(this, wizardCategories,
				primaryWizards, projectsOnly);
        newResourcePage.setDialogSettings(settings);

        Control control = newResourcePage.createControl(parent);
        getWorkbench().getHelpSystem().setHelp(control,
				IWorkbenchHelpContextIds.NEW_WIZARD_SELECTION_WIZARD_PAGE);
        setControl(control);
    }

    protected void saveWidgetValues() {
        newResourcePage.saveWidgetValues();
    }
        
    @Override
	public boolean canFlipToNextPage() {
    		if (hasPages) {
				return super.canFlipToNextPage();
			}
    		return false;
    }

	public void setHasPages(boolean newValue) {
		hasPages = newValue;
	}

	public void setCanFinishEarly(boolean newValue) {
		canFinishEarly = newValue;
	}

	public boolean canFinishEarly() {
		return canFinishEarly;
	}
}
