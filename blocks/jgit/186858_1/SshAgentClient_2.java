package org.eclipse.jgit.internal.transport.sshd.agent;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.sshd.agent.SshAgent;
import org.apache.sshd.agent.SshAgentServer;
import org.apache.sshd.common.FactoryManager;
import org.apache.sshd.common.channel.ChannelFactory;
import org.apache.sshd.common.session.ConnectionService;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.transport.sshd.agent.ConnectorFactory;

public class JGitSshAgentFactory implements org.apache.sshd.agent.SshAgentFactory {

	private final @NonNull ConnectorFactory factory;

	private final File homeDir;

	public JGitSshAgentFactory(@NonNull ConnectorFactory factory
			File homeDir) {
		this.factory = factory;
		this.homeDir = homeDir;
	}

	@Override
	public List<ChannelFactory> getChannelForwardingFactories(
			FactoryManager manager) {
		return Collections.emptyList();
	}

	@Override
	public SshAgent createClient(FactoryManager manager) throws IOException {
		return new SshAgentClient(factory.create(homeDir));
	}

	@Override
	public SshAgentServer createServer(ConnectionService service)
			throws IOException {
		return null;
	}
}
