package org.eclipse.jgit.niofs.internal.op.commands;

import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jgit.merge.MergeResult;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.merge.ThreeWayMerger;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.revwalk.RevCommit;

public class ConflictBranchesChecker {

	private final Git git;
	private final String branchA;
	private final String branchB;

	public ConflictBranchesChecker(final Git git
		this.git = checkNotNull("git"
		this.branchA = checkNotEmpty("branchA"
		this.branchB = checkNotEmpty("branchB"
	}

	public List<String> execute() {
		BranchUtil.existsBranch(this.git

		BranchUtil.existsBranch(this.git

		List<String> result = new ArrayList<>();

		try {
			final RevCommit commitA = git.getLastCommit(branchA);
			final RevCommit commitB = git.getLastCommit(branchB);

			final RevCommit commonAncestor = git.getCommonAncestorCommit(branchA

			ThreeWayMerger merger = MergeStrategy.RECURSIVE.newMerger(git.getRepository()
			merger.setBase(commonAncestor);

			boolean canMerge = merger.merge(commitA

			if (!canMerge) {
				ResolveMerger resolveMerger = (ResolveMerger) merger;
				Map<String
				result.addAll(mergeResults.keySet().stream().sorted(String::compareToIgnoreCase)
						.collect(Collectors.toList()));
			}
		} catch (IOException e) {
			throw new GitException(String.format("Error when checking for conflicts between branches %s and %s: %s"
					this.branchA
		}

		return result;
	}
}
