package org.eclipse.egit.gitflow.op;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.egit.core.op.CommitOperation;
import org.eclipse.egit.core.op.CreateLocalBranchOperation;

import static org.eclipse.egit.gitflow.Activator.error;
import static org.eclipse.egit.gitflow.GitFlowDefaults.*;

import org.eclipse.egit.gitflow.GitFlowConfig;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.WrongGitFlowStateException;
import org.eclipse.egit.gitflow.internal.CoreText;

import static org.eclipse.egit.gitflow.GitFlowConfig.*;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public final class InitOperation extends GitFlowOperation {
	private String develop;

	private String master;

	private String feature;

	private String release;

	private String hotfix;

	private String versionTag;

	public InitOperation(Repository jGitRepository, String develop,
			String master, String feature, String release, String hotfix,
			String versionTag) {
		super(new GitFlowRepository(jGitRepository));
		this.develop = develop;
		this.master = master;
		this.feature = feature;
		this.release = release;
		this.hotfix = hotfix;
		this.versionTag = versionTag;
	}

	public InitOperation(Repository repository) {
		this(repository, DEVELOP, MASTER, FEATURE_PREFIX, RELEASE_PREFIX,
				HOTFIX_PREFIX);
	}

	public InitOperation(Repository repository, String develop, String master,
			String featurePrefix, String releasePrefix, String hotfixPrefix) {
		this(repository, develop, master, featurePrefix, releasePrefix,
				hotfixPrefix, VERSION_TAG);
	}

	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		try {
			setPrefixes(feature, release, hotfix, versionTag);
			setBranches(develop, master);
			repository.getRepository().getConfig().save();
		} catch (IOException e) {
			throw new CoreException(error(e.getMessage(), e));
		}

		if (!repository.hasBranches()) {
			new CommitOperation(repository.getRepository(),
					repository.getConfig().getUser(), repository.getConfig().getUser(),
					CoreText.InitOperation_initialCommit).execute(monitor);
		}

		try {
			RevCommit head = repository.findHead();
			if (!repository.hasBranch(develop)) {
				CreateLocalBranchOperation branchFromHead = createBranchFromHead(
						develop, head);
				branchFromHead.execute(monitor);
				BranchOperation checkoutOperation = new BranchOperation(
						repository.getRepository(), develop);
				checkoutOperation.execute(monitor);
			}
		} catch (WrongGitFlowStateException e) {
			throw new CoreException(error(e));
		} catch (GitAPIException e) {
			throw new CoreException(error(e.getMessage(), e));
		}
	}

	private void setPrefixes(String feature, String release, String hotfix,
			String versionTag) {
		GitFlowConfig config = repository.getConfig();
		config.setPrefix(FEATURE_KEY, feature);
		config.setPrefix(RELEASE_KEY, release);
		config.setPrefix(HOTFIX_KEY, hotfix);
		config.setPrefix(VERSION_TAG_KEY, versionTag);
	}

	private void setBranches(String develop, String master) {
		GitFlowConfig config = repository.getConfig();
		config.setBranch(DEVELOP_KEY, develop);
		config.setBranch(MASTER_KEY, master);
	}
}
