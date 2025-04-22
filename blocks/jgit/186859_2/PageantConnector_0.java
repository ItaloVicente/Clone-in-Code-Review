package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.transport.sshd.agent.Connector;
import org.eclipse.jgit.transport.sshd.agent.ConnectorFactory;
import org.eclipse.jgit.util.SystemReader;

public class Factory implements ConnectorFactory {


	@Override
	public Connector create(File homeDir) throws IOException {
		if (SystemReader.getInstance().isWindows()) {
			return new PageantConnector();
		}
		return new UnixDomainSocketConnector();
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
