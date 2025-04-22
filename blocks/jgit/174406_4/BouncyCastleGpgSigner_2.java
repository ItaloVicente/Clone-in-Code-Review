package org.eclipse.jgit.gpg.bc.internal;

import org.eclipse.jgit.lib.GpgSignatureVerifier;
import org.eclipse.jgit.lib.GpgSignatureVerifierFactory;

public final class BouncyCastleGpgSignatureVerifierFactory
		extends GpgSignatureVerifierFactory {

	@Override
	public GpgSignatureVerifier getVerifier() {
		return new BouncyCastleGpgSignatureVerifier();
	}

}
