package org.eclipse.jgit.lib;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GpgSignatureVerifierFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(GpgSignatureVerifierFactory.class);

	private static volatile GpgSignatureVerifierFactory defaultFactory = loadDefault();

	private static GpgSignatureVerifierFactory loadDefault() {
		try {
			ServiceLoader<GpgSignatureVerifierFactory> loader = ServiceLoader
					.load(GpgSignatureVerifierFactory.class);
			Iterator<GpgSignatureVerifierFactory> iter = loader.iterator();
			if (iter.hasNext()) {
				return iter.next();
			}
		} catch (ServiceConfigurationError e) {
			LOG.error(e.getMessage()
		}
		return null;
	}

	public static GpgSignatureVerifierFactory getDefault() {
		return defaultFactory;
	}

	public static void setDefault(GpgSignatureVerifierFactory factory) {
		defaultFactory = factory;
	}

	public abstract GpgSignatureVerifier getVerifier();

}
