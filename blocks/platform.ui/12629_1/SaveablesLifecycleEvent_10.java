package org.eclipse.e4.ui.workbench.swt;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.e4.ui.internal.workbench.swt.InternalSaveable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class Saveable extends InternalSaveable implements IAdaptable {

	private Cursor waitCursor;
	private Cursor originalCursor;

	public abstract String getName();

	public abstract String getToolTipText();

	public abstract ImageDescriptor getImageDescriptor();

	public abstract void doSave(IProgressMonitor monitor) throws CoreException;

	public abstract boolean isDirty();

	public abstract boolean equals(Object object);

	public abstract int hashCode();

	public IJobRunnable doSave(IProgressMonitor monitor,
			IShellProvider shellProvider) throws CoreException {
		doSave(monitor);
		return null;
	}

	public void disableUI(MPart[] parts, boolean closing) {
		for (int i = 0; i < parts.length; i++) {
			MPart workbenchPart = parts[i];
			Composite paneComposite = (Composite) workbenchPart.getWidget();
			Control[] paneChildren = paneComposite.getChildren();
			Composite toDisable = ((Composite) paneChildren[0]);
			toDisable.setEnabled(false);
			if (waitCursor == null) {
				waitCursor = new Cursor(toDisable.getDisplay(), SWT.CURSOR_WAIT);
			}
			originalCursor = paneComposite.getCursor();
			paneComposite.setCursor(waitCursor);
		}
	}

	public void enableUI(MPart[] parts) {
		for (int i = 0; i < parts.length; i++) {
			MPart workbenchPart = parts[i];
			Composite paneComposite = (Composite) workbenchPart.getWidget();
			Control[] paneChildren = paneComposite.getChildren();
			Composite toEnable = ((Composite) paneChildren[0]);
			paneComposite.setCursor(originalCursor);
			if (waitCursor != null && !waitCursor.isDisposed()) {
				waitCursor.dispose();
				waitCursor = null;
			}
			toEnable.setEnabled(true);
		}
	}

	public Object getAdapter(Class adapter) {
		return null;
	}
}
