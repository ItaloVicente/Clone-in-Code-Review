package org.eclipse.jgit.transport.sshd.agent;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.internal.transport.sshd.agent.ConnectorFactoryProvider;

public interface ConnectorFactory {

	static ConnectorFactory getDefault() {
		return ConnectorFactoryProvider.getDefaultFactory();
	}

	static void setDefault(ConnectorFactory factory) {
		ConnectorFactoryProvider.setDefaultFactory(factory);
	}

	@NonNull
	Connector create(String identityAgent
			throws IOException;

	boolean isSupported();

	String getName();

	interface ConnectorDescriptor {

		@NonNull
		String getIdentityAgent();

		@NonNull
		String getDisplayName();
	}

	@NonNull
	Collection<ConnectorDescriptor> getSupportedConnectors();

	ConnectorDescriptor getDefaultConnector();
}
