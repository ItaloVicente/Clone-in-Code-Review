package org.eclipse.ui.internal.navigator.resources.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.actions.MoveProjectAction;
import org.eclipse.ui.actions.MoveResourceAction;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;

public class MoveResourceAction2 extends BaseSelectionListenerAction {

	protected final MoveResourceAction moveResourceAction;

	protected final MoveProjectAction moveProjectAction;

	public MoveResourceAction2(IShellProvider provider) {
		super(IDEWorkbenchMessages.MoveResourceAction_text);
		moveResourceAction = new MoveResourceAction(provider);
		moveProjectAction = new MoveProjectAction(provider);

		setToolTipText(IDEWorkbenchMessages.MoveResourceAction_toolTip);
		setId(MoveResourceAction.ID);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IIDEHelpContextIds.MOVE_RESOURCE_ACTION);
	}

	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		moveProjectAction.selectionChanged(getStructuredSelection());

		if (moveProjectAction.isEnabled()) {
			return true;
		}

		moveResourceAction.selectionChanged(getStructuredSelection());
		return moveResourceAction.isEnabled();
	}

	@Override
	public void run() {
		if (moveProjectAction.isEnabled()) {
			moveProjectAction.run();
			return;
		}
		moveResourceAction.run();
	}

}
