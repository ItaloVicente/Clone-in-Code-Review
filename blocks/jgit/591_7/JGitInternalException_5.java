package org.eclipse.jgit.api;

import java.util.concurrent.Callable;

import org.eclipse.jgit.lib.Repository;

public abstract class GitCommand<T> implements Callable<T> {
	final protected Repository repo;

	private boolean callable = true;

	protected GitCommand(Repository repo) {
		this.repo = repo;
	}

	public Repository getRepository() {
		return repo;
	}

	protected void setCallable(boolean callable) {
		this.callable = callable;
	}

	protected void checkCallable() {
		if (!callable)
			throw new IllegalStateException("Command "
					+ this.getClass().getName()
					+ " was called in the wrong state");
	}
}
