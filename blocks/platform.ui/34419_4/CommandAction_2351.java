
package org.eclipse.ui.internal.actions;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkingSetFilterActionGroup;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;

public class ClearWorkingSetAction extends Action {
    private WorkingSetFilterActionGroup actionGroup;

    public ClearWorkingSetAction(WorkingSetFilterActionGroup actionGroup) {
        super(WorkbenchMessages.ClearWorkingSetAction_text);
        Assert.isNotNull(actionGroup);
        setToolTipText(WorkbenchMessages.ClearWorkingSetAction_toolTip);
        setEnabled(actionGroup.getWorkingSet() != null);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.CLEAR_WORKING_SET_ACTION);
        this.actionGroup = actionGroup;
    }

    @Override
	public void run() {
        actionGroup.setWorkingSet(null);
    }
}
