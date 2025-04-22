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

	public LockFailedException unwind(final CoreException e) {
		Throwable cause = e.getCause();
		while (cause != null) {
			if (cause instanceof LockFailedException)
				return (LockFailedException) cause;
			Throwable parent = cause.getCause();
			if (parent == cause)
				return null;
			cause = parent;
		}
		return null;
	}

	public void execute(final IProgressMonitor monitor) throws CoreException {
		try {
			op.execute(monitor);
		} catch (CoreException e) {
			LockFailedException lockException = unwind(e);
			if (lockException != null)
				UIUtils.promptToDeleteLock(lockException);
			else
				throw e;
		}
	}

	public ISchedulingRule getSchedulingRule() {
		return op.getSchedulingRule();
	}
}
