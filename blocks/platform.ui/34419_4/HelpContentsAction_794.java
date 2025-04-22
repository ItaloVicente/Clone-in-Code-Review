
package org.eclipse.ui.internal.actions;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkingSetFilterActionGroup;
import org.eclipse.ui.dialogs.IWorkingSetEditWizard;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;

public class EditWorkingSetAction extends Action {
    private Shell shell;

    private WorkingSetFilterActionGroup actionGroup;

    public EditWorkingSetAction(WorkingSetFilterActionGroup actionGroup,
            Shell shell) {
        super(WorkbenchMessages.EditWorkingSetAction_text);
        Assert.isNotNull(actionGroup);
        setToolTipText(WorkbenchMessages.EditWorkingSetAction_toolTip); 

        this.shell = shell;
        this.actionGroup = actionGroup;
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.EDIT_WORKING_SET_ACTION);
    }

    @Override
	public void run() {
        IWorkingSetManager manager = PlatformUI.getWorkbench()
                .getWorkingSetManager();
        IWorkingSet workingSet = actionGroup.getWorkingSet();

        if (workingSet == null) {
            setEnabled(false);
            return;
        }
        IWorkingSetEditWizard wizard = manager
                .createWorkingSetEditWizard(workingSet);
        if (wizard == null) {
            String title = WorkbenchMessages.EditWorkingSetAction_error_nowizard_title; 
            String message = WorkbenchMessages.EditWorkingSetAction_error_nowizard_message;
            MessageDialog.openError(shell, title, message);
            return;
        }
        WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.create();
        if (dialog.open() == Window.OK) {
			actionGroup.setWorkingSet(wizard.getSelection());
		}
    }
}
