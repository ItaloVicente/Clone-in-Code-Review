package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.activities.ws.WorkbenchTriggerPoints;
import org.eclipse.ui.internal.registry.WizardsRegistryReader;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.wizards.IWizardCategory;

public class ExportWizard extends Wizard {
    private IWorkbench theWorkbench;

    private IStructuredSelection selection;

    class SelectionPage extends WorkbenchWizardListSelectionPage {
        SelectionPage(IWorkbench w, IStructuredSelection ss, AdaptableList e,
                String s) {
            super(w, ss, e, s, WorkbenchTriggerPoints.EXPORT_WIZARDS);
        }

        @Override
		public void createControl(Composite parent) {
            super.createControl(parent);
            workbench.getHelpSystem().setHelp(getControl(),
                    IWorkbenchHelpContextIds.EXPORT_WIZARD_SELECTION_WIZARD_PAGE);
        }

        @Override
		protected IWizardNode createWizardNode(WorkbenchWizardElement element) {
            return new WorkbenchWizardNode(this, element) {
                @Override
				public IWorkbenchWizard createWizard() throws CoreException {
                    return wizardElement.createWizard();
                }
            };
        }
    }

    @Override
	public void addPages() {
        addPage(new SelectionPage(this.theWorkbench, this.selection,
                getAvailableExportWizards(), WorkbenchMessages.ExportWizard_selectDestination));
    }

    protected AdaptableList getAvailableExportWizards() {
		IWizardCategory root = WorkbenchPlugin.getDefault()
				.getExportWizardRegistry().getRootCategory();
		WizardCollectionElement otherCategory = (WizardCollectionElement) root
				.findCategory(new Path(
						WizardsRegistryReader.UNCATEGORIZED_WIZARD_CATEGORY));
		if (otherCategory == null) {
			return new AdaptableList();
		}
		return otherCategory.getWizardAdaptableList();    
	}

    public void init(IWorkbench aWorkbench,
            IStructuredSelection currentSelection) {
        this.theWorkbench = aWorkbench;
        this.selection = currentSelection;

        setWindowTitle(WorkbenchMessages.ExportWizard_title); 
        setDefaultPageImageDescriptor(WorkbenchImages
                .getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_EXPORT_WIZ));
        setNeedsProgressMonitor(true);
    }

    @Override
	public boolean performFinish() {
        ((SelectionPage) getPages()[0]).saveWidgetValues();
        return true;
    }
}
