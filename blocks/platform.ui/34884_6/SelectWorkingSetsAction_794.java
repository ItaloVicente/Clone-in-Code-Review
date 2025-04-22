
package org.eclipse.ui.internal.actions;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkingSetFilterActionGroup;
import org.eclipse.ui.dialogs.IWorkingSetSelectionDialog;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;

public class SelectWorkingSetAction extends Action {
    private Shell shell;

    private WorkingSetFilterActionGroup actionGroup;

    public SelectWorkingSetAction(WorkingSetFilterActionGroup actionGroup,
            Shell shell) {
        super(WorkbenchMessages.SelectWorkingSetAction_text); 
        Assert.isNotNull(actionGroup);
        setToolTipText(WorkbenchMessages.SelectWorkingSetAction_toolTip); 

        this.shell = shell;
        this.actionGroup = actionGroup;
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.SELECT_WORKING_SET_ACTION);
    }

    @Override
	public void run() {
        IWorkingSetManager manager = PlatformUI.getWorkbench()
                .getWorkingSetManager();
        IWorkingSetSelectionDialog dialog = manager
                .createWorkingSetSelectionDialog(shell, false);
        IWorkingSet workingSet = actionGroup.getWorkingSet();

        if (workingSet != null) {
			dialog.setSelection(new IWorkingSet[] { workingSet });
		}

        if (dialog.open() == Window.OK) {
            IWorkingSet[] result = dialog.getSelection();
            if (result != null && result.length > 0) {
                actionGroup.setWorkingSet(result[0]);
                manager.addRecentWorkingSet(result[0]);
            } else {
                actionGroup.setWorkingSet(null);
            }
        } else {
			actionGroup.setWorkingSet(workingSet);
		}
    }
}
