package org.eclipse.egit.gitflow.op;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import static org.eclipse.egit.gitflow.Activator.error;

import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.WrongGitFlowStateException;
import org.eclipse.egit.gitflow.internal.CoreText;
import org.eclipse.jgit.api.MergeResult;

public final class HotfixFinishOperation extends AbstractHotfixOperation {
	private MergeResult mergeResult;

	public HotfixFinishOperation(GitFlowRepository repository, String hotfixName) {
		super(repository, hotfixName);
	}

	public HotfixFinishOperation(GitFlowRepository repository)
			throws WrongGitFlowStateException, CoreException, IOException {
		this(repository, getHotfixName(repository));
	}

	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		String hotfixBranchName = repository.getHotfixBranchName(versionName);
		mergeResult = mergeTo(monitor, hotfixBranchName,
				repository.getMaster());
		if (!mergeResult.getMergeStatus().isSuccessful()) {
			throw new CoreException(
					error(CoreText.HotfixFinishOperation_mergeFromHotfixToMasterFailed));
		}

		mergeResult = finish(monitor, hotfixBranchName);
		if (!mergeResult.getMergeStatus().isSuccessful()) {
			return;
		}

		safeCreateTag(monitor, versionName,
				CoreText.HotfixFinishOperation_hotfix + versionName);
	}

	public MergeResult getOperationResult() {
		return mergeResult;
	}
}
