package org.eclipse.jgit.gpg.bc;

import org.eclipse.jgit.gpg.bc.internal.BouncyCastleGpgSigner;
import org.eclipse.jgit.lib.GpgSigner;

public final class BouncyCastleGpgSignerFactory {

	private BouncyCastleGpgSignerFactory() {
	}

	public static GpgSigner create() {
		return new BouncyCastleGpgSigner();
	}
}
