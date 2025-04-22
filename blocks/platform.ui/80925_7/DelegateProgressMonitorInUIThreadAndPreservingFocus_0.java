package org.eclipse.ui.internal.wizards.datatransfer;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

class DelegateProgressMonitorInUIThreadAndPreservingFocus implements IProgressMonitorWithBlocking {
	private IProgressMonitorWithBlocking delegate;
	private Shell shell;
	private Set<IProgressMonitor> cancelTargets = new HashSet<>();

	public DelegateProgressMonitorInUIThreadAndPreservingFocus(IProgressMonitorWithBlocking delegate, Shell shell) {
		this.delegate = delegate;
		this.shell = shell;
	}

	private void inUIThread(Runnable r) {
		if (shell.getDisplay() == Display.getCurrent()) {
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
		this.cancelTargets.stream().forEach(monitor -> monitor.setCanceled(value));
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
			Control focusControl = this.shell.getDisplay().getFocusControl();
			Point selection = null;
			Combo combo = null;
			if (focusControl instanceof Combo) {
				combo = (Combo) focusControl;
				selection = combo.getSelection();
			}
			delegate.beginTask(name, totalWork);
			if (focusControl != null) {
				focusControl.setFocus();
			}
			if (selection != null && combo != null) {
				combo.setSelection(selection);
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

	public void addCancelTarget(IProgressMonitor cancelTarget) {
		this.cancelTargets.add(cancelTarget);
	}
}
