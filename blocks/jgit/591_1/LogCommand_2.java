package org.eclipse.jgit.api;

import org.eclipse.jgit.lib.Repository;

public class Git {
	private Repository repo;

	public Git(Repository repo) {
		this.repo = repo;
	}

	public CommitCommand commit() {
		return (new CommitCommand(this));
	}

	public LogCommand log() {
		return (new LogCommand(this));
	}

	public Repository getRepository() {
		return(repo);
	}
}
