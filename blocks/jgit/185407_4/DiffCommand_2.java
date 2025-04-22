package org.eclipse.jgit.api;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;

public abstract class CredentialsAwareCommand<C extends GitCommand
		extends GitCommand<T> {
	protected CredentialsProvider credentialsProvider;

	protected CredentialsAwareCommand(Repository repo) {
		super(repo);
		this.credentialsProvider = CredentialsProvider.getDefault();
	}

	public C setCredentialsProvider(
			final CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		return self();
	}

	@SuppressWarnings("unchecked")
	protected final C self() {
		return (C) this;
	}
}
