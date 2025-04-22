package org.eclipse.jgit.api;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.Transport;

public abstract class TransportCommand<C extends GitCommand
		GitCommand<T> {

	protected CredentialsProvider credentialsProvider;

	protected int timeout;

	protected TransportConfigCallback transportConfigCallback;

	protected TransportCommand(Repository repo) {
		super(repo);
		setCredentialsProvider(CredentialsProvider.getDefault());
	}

	public C setCredentialsProvider(
			final CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		return self();
	}

	public C setTimeout(int timeout) {
		this.timeout = timeout;
		return self();
	}

	public C setTransportConfigCallback(
			final TransportConfigCallback transportConfigCallback) {
		this.transportConfigCallback = transportConfigCallback;
		return self();
	}

	@SuppressWarnings("unchecked")
	protected final C self() {
		return (C) this;
	}

	protected C configure(Transport transport) {
		if (credentialsProvider != null)
			transport.setCredentialsProvider(credentialsProvider);
		transport.setTimeout(timeout);
		if (transportConfigCallback != null)
			transportConfigCallback.configure(transport);
		return self();
	}

	protected C configure(TransportCommand childCommand) {
		childCommand.setCredentialsProvider(credentialsProvider);
		childCommand.setTimeout(timeout);
		childCommand.setTransportConfigCallback(transportConfigCallback);
		return self();
	}
}
