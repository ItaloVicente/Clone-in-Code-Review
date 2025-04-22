package org.eclipse.ui.internal.wizards.datatransfer;

import org.eclipse.core.runtime.IProgressMonitor;

class DelegateProgressMonitorExceptCancel implements IProgressMonitor {
	private boolean canceled;
	private IProgressMonitor delegate;

	public DelegateProgressMonitorExceptCancel(IProgressMonitor delegate) {
		this.delegate = delegate;
	}

	@Override
	public void worked(int work) {
		if (!canceled) {
			delegate.worked(work);
		}
	}

	@Override
	public void subTask(String name) {
		if (!canceled) {
			delegate.subTask(name);
		}
	}

	@Override
	public void setTaskName(String name) {
		if (!canceled) {
			delegate.setTaskName(name);
		}
	}

	@Override
	public void setCanceled(boolean value) {
		this.canceled = value;
	}

	@Override
	public boolean isCanceled() {
		return this.canceled;
	}

	@Override
	public void internalWorked(double work) {
		if (!canceled) {
			delegate.internalWorked(work);
		}
	}

	@Override
	public void done() {
		if (!canceled) {
			delegate.done();
		}
	}

	@Override
	public void beginTask(String name, int totalWork) {
		if (!canceled) {
			delegate.beginTask(name, totalWork);
		}
	}
}
