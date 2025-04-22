package org.eclipse.jgit.lib;

import java.util.Iterator;
import java.util.ServiceLoader;
import org.eclipse.jgit.lib.internal.NoopGpgSigner;

public class GpgSignerFactory {

	private static GpgSigner defaultSigner;

	public static GpgSigner getGpgSigner() {
		if(defaultSigner == null) {
			defaultSigner = loadDefaultGpgSigner();
		}
		return defaultSigner;
	}

	public static void setDefault(GpgSigner signer) {
		defaultSigner = signer;
	}

	private static GpgSigner loadDefaultGpgSigner() {
		ServiceLoader<GpgSigner> loader = ServiceLoader.load(GpgSigner.class);
		Iterator<GpgSigner> iter = loader.iterator();
		if(iter.hasNext()) {
			return iter.next();
		}
		return new NoopGpgSigner();
	}
}
