package org.eclipse.jgit.transport;

public interface NonceGenerator {
	public String createNonce(String seed
			throws IllegalStateException;
}
