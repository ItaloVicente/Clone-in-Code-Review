package org.eclipse.egit.ui.internal.components;

import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

public abstract class PartVisibilityListener implements IPartListener2 {

	private final IWorkbenchPart myself;

	private boolean viewVisible = true;

	public PartVisibilityListener(IWorkbenchPart part) {
		myself = part;
	}

	public boolean isVisible() {
		return viewVisible;
	}

	private void updateHiddenState(IWorkbenchPartReference partRef,
			boolean visible) {
		if (isMe(partRef)) {
			viewVisible = visible;
		}
	}

	protected final boolean isMe(IWorkbenchPartReference partRef) {
		return partRef.getPart(false) == myself;
	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		updateHiddenState(partRef, false);
	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
		updateHiddenState(partRef, false);
	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		updateHiddenState(partRef, true);
	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
		updateHiddenState(partRef, true);
	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
	}

}
