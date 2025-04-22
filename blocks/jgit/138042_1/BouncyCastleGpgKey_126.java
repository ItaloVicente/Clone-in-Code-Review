package org.eclipse.jgit.lib.internal;

import java.nio.file.Path;

import org.bouncycastle.openpgp.PGPSecretKey;

class BouncyCastleGpgKey {

	private PGPSecretKey secretKey;

	private Path origin;

	public BouncyCastleGpgKey(PGPSecretKey secretKey
		this.secretKey = secretKey;
		this.origin = origin;
	}

	public PGPSecretKey getSecretKey() {
		return secretKey;
	}

	public Path getOrigin() {
		return origin;
	}
}
