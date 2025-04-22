package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.jgit.transport.sshd.agent.Connector;
import org.eclipse.jgit.transport.sshd.agent.ConnectorFactory;
import org.eclipse.jgit.util.SystemReader;

public class Factory implements ConnectorFactory {


	@Override
	public Connector create(String identityAgent
			throws IOException {
		if (SystemReader.getInstance().isWindows()) {
			return new PageantConnector();
		}
		return new UnixDomainSocketConnector(identityAgent);
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Collection<ConnectorDescriptor> getSupportedConnectors() {
		return Collections.singleton(getDefaultConnector());
	}

	@Override
	public ConnectorDescriptor getDefaultConnector() {
		if (SystemReader.getInstance().isWindows()) {
			return PageantConnector.DESCRIPTOR;
		}
		return UnixDomainSocketConnector.DESCRIPTOR;
	}
}
