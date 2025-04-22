package org.eclipse.jgit.api;

import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;

public abstract class GitCommand<T> implements Callable<T> {
	final protected Repository repo;

	private AtomicBoolean callable = new AtomicBoolean(true);

	protected GitCommand(Repository repo) {
		this.repo = repo;
	}

	public Repository getRepository() {
		return repo;
	}

	protected void setCallable(boolean callable) {
		this.callable.set(callable);
	}

	protected void checkCallable() {
		if (!callable.get())
			throw new IllegalStateException(MessageFormat.format(
					JGitText.get().commandWasCalledInTheWrongState
					
	}

	@Override
	public abstract T call() throws GitAPIException;
}
