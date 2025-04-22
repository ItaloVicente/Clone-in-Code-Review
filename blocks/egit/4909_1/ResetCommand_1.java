package org.eclipse.egit.ui.internal.operations;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.egit.core.op.IEGitOperation;
import org.eclipse.egit.ui.UIUtils;
import org.eclipse.jgit.errors.LockFailedException;

public class LockOperation implements IEGitOperation {

	private final IEGitOperation op;

	public LockOperation(final IEGitOperation op) {
		this.op = op;
	}

	public LockFailedException unwind(CoreException e) {
		Throwable cause = e.getCause();
		while (!(cause instanceof LockFailedException)) {
			Throwable parent = cause.getCause();
			if (parent == null || parent == cause)
				return null;
			else
				cause = parent;
		}
		return cause instanceof LockFailedException ? (LockFailedException) cause
				: null;
	}

	public void execute(IProgressMonitor monitor) throws CoreException {
		try {
			op.execute(monitor);
		} catch (CoreException e) {
			LockFailedException lockException = unwind(e);
			if (lockException != null)
				UIUtils.handleLockDelete(lockException);
		}
	}

	public ISchedulingRule getSchedulingRule() {
		return op.getSchedulingRule();
	}
}
