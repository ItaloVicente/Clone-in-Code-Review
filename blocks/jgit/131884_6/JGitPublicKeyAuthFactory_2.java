package org.eclipse.jgit.internal.transport.sshd;

import org.apache.sshd.client.ClientFactoryManager;
import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.client.session.ClientSessionImpl;
import org.apache.sshd.common.io.IoSession;
import org.eclipse.jgit.transport.CredentialsProvider;

public class JGitClientSession extends ClientSessionImpl {

	private HostConfigEntry hostConfig;

	private CredentialsProvider credentialsProvider;

	public JGitClientSession(ClientFactoryManager manager
			throws Exception {
		super(manager
	}

	public HostConfigEntry getHostConfigEntry() {
		return hostConfig;
	}

	public void setHostConfigEntry(HostConfigEntry hostConfig) {
		this.hostConfig = hostConfig;
	}

	public void setCredentialsProvider(CredentialsProvider provider) {
		credentialsProvider = provider;
	}

	public CredentialsProvider getCredentialsProvider() {
		return credentialsProvider;
	}

}
