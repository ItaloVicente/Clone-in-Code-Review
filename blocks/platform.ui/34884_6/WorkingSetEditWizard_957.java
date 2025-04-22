package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.activities.ITriggerPoint;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.model.AdaptableList;

public abstract class WorkbenchWizardSelectionPage extends WizardSelectionPage {

    protected IWorkbench workbench;

    protected AdaptableList wizardElements;

    public TableViewer wizardSelectionViewer;

    protected IStructuredSelection currentResourceSelection;
    
    protected String triggerPointId;

    public WorkbenchWizardSelectionPage(String name, IWorkbench aWorkbench,
            IStructuredSelection currentSelection, AdaptableList elements, 
            String triggerPointId) {
        super(name);
        this.wizardElements = elements;
        this.currentResourceSelection = currentSelection;
        this.workbench = aWorkbench;
        this.triggerPointId = triggerPointId;
        setTitle(WorkbenchMessages.Select);
    }

    protected WorkbenchWizardElement findWizard(String searchId) {
        Object[] children = wizardElements.getChildren();
        for (int i = 0; i < children.length; ++i) {
            WorkbenchWizardElement currentWizard = (WorkbenchWizardElement) children[i];
            if (currentWizard.getId().equals(searchId)) {
				return currentWizard;
			}
        }

        return null;
    }

    public IStructuredSelection getCurrentResourceSelection() {
        return currentResourceSelection;
    }

    public IWorkbench getWorkbench() {
        return this.workbench;
    }

    public void selectWizardNode(IWizardNode node) {
        setSelectedNode(node);
    }

    @Override
	public IWizardPage getNextPage() { 
        ITriggerPoint triggerPoint = getWorkbench().getActivitySupport()
        .getTriggerPointManager().getTriggerPoint(triggerPointId);
        if (triggerPoint == null || WorkbenchActivityHelper.allowUseOf(triggerPoint, getSelectedNode())) {
			return super.getNextPage();
		}
        return null;
    }
}
