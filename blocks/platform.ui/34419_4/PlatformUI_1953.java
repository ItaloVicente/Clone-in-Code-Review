package org.eclipse.ui;

public class PerspectiveAdapter implements IPerspectiveListener4 {


	@Override
	public void perspectiveOpened(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
	}

	@Override
	public void perspectiveClosed(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
	}

	@Override
	public void perspectiveChanged(IWorkbenchPage page,
			IPerspectiveDescriptor perspective,
			IWorkbenchPartReference partRef, String changeId) {
	}

	@Override
	public void perspectiveActivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
	}

	@Override
	public void perspectiveChanged(IWorkbenchPage page,
			IPerspectiveDescriptor perspective, String changeId) {
	}

	@Override
	public void perspectiveDeactivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
	}

	@Override
	public void perspectiveSavedAs(IWorkbenchPage page,
			IPerspectiveDescriptor oldPerspective,
			IPerspectiveDescriptor newPerspective) {
	}

	@Override
	public void perspectivePreDeactivate(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
	}
}
