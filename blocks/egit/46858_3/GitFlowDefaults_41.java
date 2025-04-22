package org.eclipse.egit.gitflow;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

import static org.eclipse.egit.gitflow.Activator.error;

public class BranchNameValidator {
	public static final String ILLEGAL_CHARS = "/ "; //$NON-NLS-1$

	public static boolean featureExists(GitFlowRepository repository,
			String featureName) throws CoreException {
		return branchExists(repository,
				repository.getFullFeatureBranchName(featureName));
	}

	public static boolean hotfixExists(GitFlowRepository repository,
			String hotfixName) throws CoreException {
		return branchExists(repository,
				repository.getFullHotfixBranchName(hotfixName));
	}

	public static boolean releaseExists(GitFlowRepository repository,
			String releaseName) throws CoreException {
		return branchExists(repository,
				repository.getFullReleaseBranchName(releaseName));
	}

	private static boolean branchExists(GitFlowRepository repository,
			String fullBranchName) throws CoreException {
		List<Ref> branches;
		try {
			branches = Git.wrap(repository.getRepository()).branchList().call();
		} catch (GitAPIException e) {
			throw new CoreException(error(e.getMessage(), e));
		}
		for (Ref ref : branches) {
			if (fullBranchName.equals(ref.getTarget().getName())) {
				return true;
			}
		}

		return false;
	}

	public static boolean isBranchNameValid(String name) {
		return Repository.isValidRefName(name);
	}
}
