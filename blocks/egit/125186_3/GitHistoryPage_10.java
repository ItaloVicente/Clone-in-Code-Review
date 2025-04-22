package org.eclipse.egit.ui.internal.history;

import java.util.Collection;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;

public class FileDiffInput {

	private final Repository repository;

	private final TreeWalk walk;

	private final RevCommit commit;

	private final Collection<String> interestingPaths;

	private final boolean selectMarked;

	public FileDiffInput(Repository repository, TreeWalk walk, RevCommit commit,
			Collection<String> interestingPaths, boolean selectMarked) {
		this.commit = commit;
		this.selectMarked = selectMarked;
		this.repository = repository;
		this.walk = walk;
		this.interestingPaths = interestingPaths;
	}

	public RevCommit getCommit() {
		return commit;
	}

	public boolean isSelectMarked() {
		return selectMarked;
	}

	public Repository getRepository() {
		return repository;
	}

	public TreeWalk getTreeWalk() {
		return walk;
	}

	public @Nullable Collection<String> getInterestingPaths() {
		return interestingPaths;
	}
}
