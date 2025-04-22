package org.eclipse.egit.core.op;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IEGitOperation {
	void execute(IProgressMonitor monitor) throws CoreException;
}
