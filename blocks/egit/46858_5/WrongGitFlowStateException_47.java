package org.eclipse.egit.gitflow;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_TAGS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.egit.gitflow.internal.CoreText;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class GitFlowRepository {

	private Repository repository;

	private GitFlowConfig config;

	public GitFlowRepository(Repository repository) {
		Assert.isNotNull(repository);
		this.repository = repository;
		this.config = new GitFlowConfig(repository);
	}

	public boolean hasBranches() {
		List<Ref> branches;
		try {
			branches = Git.wrap(repository).branchList().call();
			return !branches.isEmpty();
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean hasBranch(String branch) throws GitAPIException {
		String fullBranchName = R_HEADS + branch;
		List<Ref> branchList = Git.wrap(repository).branchList().call();
		for (Ref ref : branchList) {
			if (fullBranchName.equals(ref.getTarget().getName())) {
				return true;
			}
		}

		return false;
	}

	public Ref findBranch(String branchName) throws IOException {
		return repository.getRef(R_HEADS + branchName);
	}

	public boolean isFeature() throws IOException {
		return repository.getBranch()
				.startsWith(getConfig().getFeaturePrefix());
	}

	public boolean isDevelop() throws IOException {
		return repository.getBranch().equals(getConfig().getDevelop());
	}

	public boolean isMaster() throws IOException {
		return repository.getBranch().equals(getConfig().getMaster());
	}

	public boolean isRelease() throws IOException {
		return repository.getBranch().startsWith(getConfig().getReleasePrefix());
	}

	public boolean isHotfix() throws IOException {
		return repository.getBranch().startsWith(getConfig().getHotfixPrefix());
	}

	public RevCommit findHead() throws WrongGitFlowStateException {
		try (RevWalk walk = new RevWalk(repository)) {
			try {
				ObjectId head = repository.resolve(HEAD);
				return walk.parseCommit(head);
			} catch (MissingObjectException e) {
				throw new WrongGitFlowStateException(CoreText.GitFlowRepository_gitFlowRepositoryMayNotBeEmpty);
			} catch (RevisionSyntaxException | IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public RevCommit findHead(String branchName) {
		try (RevWalk walk = new RevWalk(repository)) {
			try {
				ObjectId head = repository.resolve(R_HEADS + branchName);
				return walk.parseCommit(head);
			} catch (RevisionSyntaxException | IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public RevCommit findCommit(String sha1) {
		try (RevWalk walk = new RevWalk(repository)) {
			try {
				ObjectId head = repository.resolve(sha1);
				return walk.parseCommit(head);
			} catch (RevisionSyntaxException | IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

	public Repository getRepository() {
		return repository;
	}

	public List<Ref> getFeatureBranches() {
		return getPrefixBranches(R_HEADS + getConfig().getFeaturePrefix());
	}

	public List<Ref> getReleaseBranches() {
		return getPrefixBranches(R_HEADS + getConfig().getReleasePrefix());
	}

	public List<Ref> getHotfixBranches() {
		return getPrefixBranches(R_HEADS + getConfig().getHotfixPrefix());
	}

	private List<Ref> getPrefixBranches(String prefix) {
		try {
			List<Ref> branches = Git.wrap(repository).branchList().call();
			List<Ref> prefixBranches = new ArrayList<Ref>();
			for (Ref ref : branches) {
				if (ref.getName().startsWith(prefix)) {
					prefixBranches.add(ref);
				}
			}

			return prefixBranches;
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		}
	}

	public String getFeatureBranchName(Ref ref) {
		return ref.getName().substring(
				(R_HEADS + getConfig().getFeaturePrefix()).length());
	}

	public RevCommit findCommitForTag(String tagName)
			throws MissingObjectException, IncorrectObjectTypeException,
			IOException {
		try (RevWalk revWalk = new RevWalk(repository)) {
			Ref tagRef = repository.getRef(R_TAGS + tagName);
			if (tagRef == null) {
				return null;
			}
			return revWalk.parseCommit(tagRef.getObjectId());
		}
	}

	public void setRemote(String featureName, String value) throws IOException {
		getConfig().setRemote(featureName, value);
	}

	public void setUpstreamBranchName(String featureName, String value) throws IOException {
		getConfig().setUpstreamBranchName(featureName, value);
	}

	public String getUpstreamBranchName(String featureName) {
		return getConfig().getUpstreamBranchName(featureName);
	}

	public GitFlowConfig getConfig() {
		return this.config;
	}
}
