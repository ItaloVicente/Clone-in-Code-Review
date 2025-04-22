package org.eclipse.ui.internal.misc;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

public class TestPartListener implements IPartListener {
    public TestPartListener() {
        super();
    }

    @Override
	public void partActivated(IWorkbenchPart part) {
        System.out.println("partActivated(" + part + ")");//$NON-NLS-2$//$NON-NLS-1$
    }

    @Override
	public void partBroughtToTop(IWorkbenchPart part) {
        System.out.println("partBroughtToTop(" + part + ")");//$NON-NLS-2$//$NON-NLS-1$
    }

    @Override
	public void partClosed(IWorkbenchPart part) {
        System.out.println("partClosed(" + part + ")");//$NON-NLS-2$//$NON-NLS-1$
    }

    @Override
	public void partDeactivated(IWorkbenchPart part) {
        System.out.println("partDeactivated(" + part + ")");//$NON-NLS-2$//$NON-NLS-1$
    }

    @Override
	public void partOpened(IWorkbenchPart part) {
        System.out.println("partOpened(" + part + ")");//$NON-NLS-2$//$NON-NLS-1$
    }
}
