package org.eclipse.egit.gitflow.op;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.gitflow.GitFlowRepository;

public final class HotfixStartOperation extends AbstractHotfixOperation {
	public HotfixStartOperation(GitFlowRepository repository, String hotfixName) {
		super(repository, hotfixName);
	}

	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		String branchName = repository.getHotfixBranchName(versionName);

		start(monitor, branchName, repository.findHead(repository.getMaster()));
	}
}
