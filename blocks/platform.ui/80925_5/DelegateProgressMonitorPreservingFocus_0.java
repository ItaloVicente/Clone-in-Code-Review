package org.eclipse.ui.internal.wizards.datatransfer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

class DelegateProgressMonitorPreservingFocus implements IProgressMonitor {
	private IProgressMonitor delegate;
	private Shell shell;

	public DelegateProgressMonitorPreservingFocus(IProgressMonitor delegate, Shell shell) {
		this.delegate = delegate;
		this.shell = shell;
	}

	@Override
	public void worked(int work) {
		delegate.worked(work);
	}

	@Override
	public void subTask(String name) {
		delegate.subTask(name);
	}

	@Override
	public void setTaskName(String name) {
		delegate.setTaskName(name);
	}

	@Override
	public void setCanceled(boolean value) {
		delegate.setCanceled(value);
	}

	@Override
	public boolean isCanceled() {
		return delegate.isCanceled();
	}

	@Override
	public void internalWorked(double work) {
		delegate.internalWorked(work);
	}

	@Override
	public void done() {
		delegate.done();
	}

	@Override
	public void beginTask(String name, int totalWork) {
		Control focusControl = this.shell.getDisplay().getFocusControl();
		Point selection = null;
		Combo combo = null;
		if (focusControl instanceof Combo) {
			combo = (Combo) focusControl;
			selection = combo.getSelection();
		}
		delegate.beginTask(name, totalWork);
		focusControl.setFocus();
		if (selection != null && combo != null) {
			combo.setSelection(selection);
		}
	}
}
