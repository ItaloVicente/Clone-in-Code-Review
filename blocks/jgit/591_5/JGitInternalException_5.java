package org.eclipse.jgit.api;

import java.util.concurrent.Callable;
import org.eclipse.jgit.lib.Repository;

public abstract class GitCommand<T> implements Callable<T> {
	protected Repository repo;

	private boolean state = true;

	protected GitCommand(Repository repo) {
		this.repo = repo;
	}

	public Repository getRepository() {
		return repo;
	}

	protected void setState(boolean state) {
		this.state = state;
	}

	protected void checkState() {
		if (!state)
			throw new IllegalStateException("Command "
					+ this.getClass().getName()
					+ " was called in the wrong state");
	}
}
