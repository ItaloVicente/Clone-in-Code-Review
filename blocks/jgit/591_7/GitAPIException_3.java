package org.eclipse.jgit.api;

import org.eclipse.jgit.lib.Repository;

public class Git {
	private final Repository repo;

	public Git(Repository repo) {
		if (repo == null)
			throw new NullPointerException();
		this.repo = repo;
	}

	public CommitCommand commit() {
		return new CommitCommand(repo);
	}

	public LogCommand log() {
		return new LogCommand(repo);
	}

	public Repository getRepository() {
		return repo;
	}
}
