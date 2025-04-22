package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushCertificate.NonceStatus;

public interface NonceGenerator {

	public String createNonce(String seed
			throws IllegalStateException;

	public NonceStatus verify(String received
			Repository db
}
