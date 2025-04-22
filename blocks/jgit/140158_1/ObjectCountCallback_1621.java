package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushCertificate.NonceStatus;

public interface NonceGenerator {

	String createNonce(Repository db
			throws IllegalStateException;

	NonceStatus verify(String received
			Repository db
}
