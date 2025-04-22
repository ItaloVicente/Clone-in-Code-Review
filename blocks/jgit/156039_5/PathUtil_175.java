package org.eclipse.jgit.niofs.internal.op.commands;

import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ThreeWayMerger;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.niofs.internal.op.model.MergeCommitContent;
import org.eclipse.jgit.niofs.internal.op.model.MessageCommitInfo;
import org.eclipse.jgit.revwalk.RevCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Merge {

	private Logger logger = LoggerFactory.getLogger(Merge.class);

	private final Git git;
	private final String sourceBranch;
	private final String targetBranch;
	private final boolean noFastForward;

	public Merge(final Git git
		this(git
	}

	public Merge(final Git git

		this.git = checkNotNull("git"
		this.sourceBranch = checkNotEmpty("sourceBranch"
		this.targetBranch = checkNotEmpty("targetBranch"

		this.noFastForward = noFastForward;
	}

	public List<String> execute() throws IOException {
		BranchUtil.existsBranch(git
		BranchUtil.existsBranch(git

		final RevCommit lastSourceCommit = git.getLastCommit(sourceBranch);
		final RevCommit lastTargetCommit = git.getLastCommit(targetBranch);

		final RevCommit commonAncestor = git.getCommonAncestorCommit(sourceBranch

		canMerge(git.getRepository()

		return proceedMerge(commonAncestor
	}

	private List<String> proceedMerge(final RevCommit commonAncestor
			final RevCommit lastTargetCommit) throws IOException {
		final List<DiffEntry> diffBetweenCommits = git.listDiffs(commonAncestor.getName()

		final List<DiffEntry> diffBetweenBranches = diffBetweenCommits.isEmpty() ? Collections.emptyList()
				: git.listDiffs(git.getTreeFromRef(targetBranch)

		if (diffBetweenBranches.isEmpty()) {
			logger.info("There is nothing to merge from branch {} to {}"
			return Collections.emptyList();
		}

		final List<RevCommit> targetCommits = git.listCommits(commonAncestor

		return targetCommits.isEmpty() && !noFastForward ? doFastForward(commonAncestor
				: doMerge(commonAncestor
	}

	private void canMerge(final Repository repo
			final RevCommit targetCommitTree
		try {
			ThreeWayMerger merger = MergeStrategy.RECURSIVE.newMerger(repo
			merger.setBase(commonAncestor);
			boolean canMerge = merger.merge(sourceCommitTree
			if (!canMerge) {
				throw new GitException(String.format("Cannot merge branches from <%s> to <%s>
						sourceBranch
			}
		} catch (IOException e) {
			throw new GitException(String.format("Cannot merge branches from <%s> to <%s>
					sourceBranch
		}
	}

	private List<String> doFastForward(final RevCommit commonAncestor
			throws IOException {
		final List<RevCommit> sourceCommits = git.listCommits(commonAncestor

		Collections.reverse(sourceCommits);

		final String[] commitsIDs = sourceCommits.stream().map(AnyObjectId::getName).toArray(String[]::new);

		git.cherryPick(targetBranch

		if (logger.isDebugEnabled()) {
			logger.debug("Merging commits from <{}> to <{}>"
		}

		return Arrays.asList(commitsIDs);
	}

	private List<String> doMerge(final RevCommit commonAncestorCommit
			final RevCommit lastTargetCommit) {
		try {
			final Map<String
					lastSourceCommit.getName());

			final List<RevCommit> parents = Stream.of(lastTargetCommit

			final boolean effective = git.commit(targetBranch
					false
			if (effective) {
				return Collections.singletonList(git.getLastCommit(targetBranch).getName());
			}
		} catch (Exception e) {
			logger.error(e.getMessage()
		}

		throw new GitException(String.format("Cannot merge branches from <%s> to <%s>"
	}
}
