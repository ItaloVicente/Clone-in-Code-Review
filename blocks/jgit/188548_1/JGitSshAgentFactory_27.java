package org.eclipse.jgit.internal.transport.sshd.agent;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.eclipse.jgit.transport.sshd.agent.ConnectorFactory;

public final class ConnectorFactoryProvider {

	private static volatile ConnectorFactory INSTANCE = loadDefaultFactory();

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

	public static ConnectorFactory getDefaultFactory() {
		return INSTANCE;
	}

	public static void setDefaultFactory(ConnectorFactory factory) {
		INSTANCE = factory == null ? loadDefaultFactory() : factory;
	}

	private ConnectorFactoryProvider() {
	}
}
