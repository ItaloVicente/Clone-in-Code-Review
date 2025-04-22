package org.eclipse.ui.internal.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.LegacyResourceSupport;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class NewWizardShortcutAction extends Action implements
        IPluginContribution {
    private IWizardDescriptor wizardElement;

    private static final int SIZING_WIZARD_WIDTH = 500;

    private static final int SIZING_WIZARD_HEIGHT = 500;
    
    private IWorkbenchWindow window;

    public NewWizardShortcutAction(IWorkbenchWindow window,
            IWizardDescriptor wizardDesc) {
        super(wizardDesc.getLabel());
        setToolTipText(wizardDesc.getDescription());
        setImageDescriptor(wizardDesc.getImageDescriptor());
        setId(ActionFactory.NEW.getId());
        wizardElement = wizardDesc;
        this.window = window;
    }

    public IWizardDescriptor getWizardDescriptor() {
		return wizardElement;
	}
   
    @Override
	public void run() {

        INewWizard wizard;
        try {
            wizard = (INewWizard) wizardElement.createWizard();
        } catch (CoreException e) {
            ErrorDialog.openError(window.getShell(), WorkbenchMessages.NewWizardShortcutAction_errorTitle,
                    WorkbenchMessages.NewWizardShortcutAction_errorMessage,
                    e.getStatus());
            return;
        }

        ISelection selection = window.getSelectionService().getSelection();
        IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
        if (selection instanceof IStructuredSelection) {
            selectionToPass = wizardElement
                    .adaptedSelection((IStructuredSelection) selection);
        } else {
            IWorkbenchPart part = window.getPartService().getActivePart();
            if (part instanceof IEditorPart) {
                IEditorInput input = ((IEditorPart) part).getEditorInput();
                Class fileClass = LegacyResourceSupport.getFileClass();
                if (input != null && fileClass != null) {
                    Object file = Util.getAdapter(input, fileClass);
                    if (file != null) {
                        selectionToPass = new StructuredSelection(file);
                    }
                }
            }
        }

		wizard.init(window.getWorkbench(), selectionToPass);

        Shell parent = window.getShell();
        WizardDialog dialog = new WizardDialog(parent, wizard);
        dialog.create();
        Point defaultSize = dialog.getShell().getSize();
        dialog.getShell().setSize(
                Math.max(SIZING_WIZARD_WIDTH, defaultSize.x),
                Math.max(SIZING_WIZARD_HEIGHT, defaultSize.y));
        window.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
				IWorkbenchHelpContextIds.NEW_WIZARD_SHORTCUT);
        
        if (wizardElement.canFinishEarly() && !wizardElement.hasPages()) {
			wizard.performFinish();
			dialog.close();
		} else {
			dialog.open();
		}
    }

    @Override
	public String getLocalId() {
    	IPluginContribution contribution = getPluginContribution();
    	if (contribution != null) {
			return contribution.getLocalId();
		}
    	return wizardElement.getId();
    }

    @Override
	public String getPluginId() {
    	IPluginContribution contribution = getPluginContribution();
    	if (contribution != null) {
			return contribution.getPluginId();
		}
    	return null;
    }
    
    private IPluginContribution getPluginContribution() {
		return (IPluginContribution) Util.getAdapter(wizardElement,
				IPluginContribution.class);
	}
}
