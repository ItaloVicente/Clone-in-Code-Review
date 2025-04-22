
package org.eclipse.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.InternalSaveable;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.progress.IJobRunnable;

public abstract class Saveable extends InternalSaveable implements IAdaptable {

	private Cursor waitCursor;
	private Cursor originalCursor;

	public boolean show(IWorkbenchPage page) {
		if (page == null) {
		}
		return false;
	}

	public abstract String getName();

	public abstract String getToolTipText();

	public abstract ImageDescriptor getImageDescriptor();

	public abstract void doSave(IProgressMonitor monitor) throws CoreException;

	public abstract boolean isDirty();

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract int hashCode();

	public IJobRunnable doSave(IProgressMonitor monitor,
			IShellProvider shellProvider) throws CoreException {
		doSave(monitor);
		return null;
	}

	public void disableUI(IWorkbenchPart[] parts, boolean closing) {
		for (int i = 0; i < parts.length; i++) {
			IWorkbenchPart workbenchPart = parts[i];
			Composite paneComposite = (Composite) ((PartSite) workbenchPart
.getSite()).getModel()
					.getWidget();
			Control[] paneChildren = paneComposite.getChildren();
			Composite toDisable = ((Composite) paneChildren[0]);
			toDisable.setEnabled(false);
			if (waitCursor == null) {
				waitCursor = new Cursor(workbenchPart.getSite().getWorkbenchWindow().getShell().getDisplay(), SWT.CURSOR_WAIT);
			}
			originalCursor = paneComposite.getCursor();
			paneComposite.setCursor(waitCursor);
		}
	}

	public void enableUI(IWorkbenchPart[] parts) {
		for (int i = 0; i < parts.length; i++) {
			IWorkbenchPart workbenchPart = parts[i];
			Composite paneComposite = (Composite) ((PartSite) workbenchPart
.getSite()).getModel()
					.getWidget();
			Control[] paneChildren = paneComposite.getChildren();
			Composite toEnable = ((Composite) paneChildren[0]);
			paneComposite.setCursor(originalCursor);
			if (waitCursor!=null && !waitCursor.isDisposed()) {
				waitCursor.dispose();
				waitCursor = null;
			}
			toEnable.setEnabled(true);
		}
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}
}
