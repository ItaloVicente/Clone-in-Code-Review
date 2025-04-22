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

public class ImportWizard extends Wizard {

    class SelectionPage extends WorkbenchWizardListSelectionPage {
        SelectionPage(IWorkbench w, IStructuredSelection ss, AdaptableList e,
                String s) {
            super(w, ss, e, s, WorkbenchTriggerPoints.IMPORT_WIZARDS);
        }

        @Override
		public void createControl(Composite parent) {
            super.createControl(parent);
            getWorkbench()
					.getHelpSystem()
					.setHelp(
							getControl(),
							IWorkbenchHelpContextIds.IMPORT_WIZARD_SELECTION_WIZARD_PAGE);
        }

        @Override
		public IWizardNode createWizardNode(WorkbenchWizardElement element) {
            return new WorkbenchWizardNode(this, element) {
                @Override
				public IWorkbenchWizard createWizard() throws CoreException {
                    return wizardElement.createWizard();
                }
            };
        }
        
        
    }

    private IStructuredSelection selection;

    private IWorkbench workbench;

    @Override
	public void addPages() {
        addPage(new SelectionPage(this.workbench, this.selection,
                getAvailableImportWizards(), WorkbenchMessages.ImportWizard_selectSource)); 
    }

    protected AdaptableList getAvailableImportWizards() {
		IWizardCategory root = WorkbenchPlugin.getDefault()
				.getImportWizardRegistry().getRootCategory();
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
        this.workbench = aWorkbench;
        this.selection = currentSelection;

        setWindowTitle(WorkbenchMessages.ImportWizard_title); 
        setDefaultPageImageDescriptor(WorkbenchImages
                .getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_IMPORT_WIZ));
        setNeedsProgressMonitor(true);
    }

    @Override
	public boolean performFinish() {
        ((SelectionPage) getPages()[0]).saveWidgetValues();
        return true;
    }
}
