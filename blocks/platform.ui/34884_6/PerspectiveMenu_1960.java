package org.eclipse.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

public abstract class PartEventAction extends Action implements IPartListener {

    private IWorkbenchPart activePart;

    protected PartEventAction(String text) {
        super(text);
    }

    protected PartEventAction(String text, int style) {
        super(text, style);
    }

    public IWorkbenchPart getActivePart() {
        return activePart;
    }

    @Override
	public void partActivated(IWorkbenchPart part) {
        activePart = part;
    }

    @Override
	public void partBroughtToTop(IWorkbenchPart part) {
    }

    @Override
	public void partClosed(IWorkbenchPart part) {
        if (part == activePart) {
			activePart = null;
		}
    }

    @Override
	public void partDeactivated(IWorkbenchPart part) {
        activePart = null;
    }

    @Override
	public void partOpened(IWorkbenchPart part) {
    }
}
