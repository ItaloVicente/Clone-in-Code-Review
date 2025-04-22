package org.eclipse.jgit.internal.transport.sshd.agent;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.eclipse.jgit.transport.sshd.agent.ConnectorFactory;

public final class ConnectorFactoryProvider {

	private static final ConnectorFactory FACTORY = loadDefaultFactory();

	private static ConnectorFactory loadDefaultFactory() {
		ServiceLoader<ConnectorFactory> loader = ServiceLoader
				.load(ConnectorFactory.class);
		Iterator<ConnectorFactory> iter = loader.iterator();
		while (iter.hasNext()) {
			ConnectorFactory candidate = iter.next();
			if (candidate.isSupported()) {
				return candidate;
			}
		}
		return null;

	}

	private ConnectorFactoryProvider() {
	}

	public static ConnectorFactory getDefaultFactory() {
		return FACTORY;
	}
}
