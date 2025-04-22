package org.eclipse.ui.internal.wizards.datatransfer;

import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

class DelegateProgressMonitorInUIThreadAndPreservingFocus implements IProgressMonitorWithBlocking {
	private ProgressMonitorPart delegate;
	private Control focusControl;
	private Display display;

	public DelegateProgressMonitorInUIThreadAndPreservingFocus(ProgressMonitorPart delegate,
			Control focusControl) {
		this.delegate = delegate;
		this.display = focusControl.getDisplay();
		this.focusControl = focusControl;
	}

	private void inUIThread(Runnable r) {
		if (display == Display.getCurrent()) {
			r.run();
		} else {
			Display.getDefault().asyncExec(r);
		}
	}

	@Override
	public void worked(int work) {
		inUIThread(() -> delegate.worked(work));
	}

	@Override
	public void subTask(String name) {
		inUIThread(() -> delegate.subTask(name));
	}

	@Override
	public void setTaskName(String name) {
		inUIThread(() -> delegate.setTaskName(name));
	}

	@Override
	public void setCanceled(boolean value) {
		inUIThread(() -> delegate.setCanceled(value));
	}

	@Override
	public boolean isCanceled() {
		return delegate.isCanceled();
	}

	@Override
	public void internalWorked(double work) {
		inUIThread(() -> delegate.internalWorked(work));
	}

	@Override
	public void done() {
		inUIThread(() -> delegate.done());
	}

	@Override
	public void beginTask(String name, int totalWork) {
		inUIThread(() -> {
			Point initialSelection = null;
			if (focusControl instanceof Combo) {
				initialSelection = ((Combo) focusControl).getSelection();
			}
			delegate.beginTask(name, totalWork);
			focusControl.setFocus();
			if (focusControl instanceof Combo && initialSelection != null) {
				((Combo) focusControl).setSelection(initialSelection);
			}
		});
	}

	@Override
	public void setBlocked(IStatus reason) {
		inUIThread(() -> delegate.setBlocked(reason));
	}

	@Override
	public void clearBlocked() {
		inUIThread(() -> delegate.clearBlocked());
	}
}
