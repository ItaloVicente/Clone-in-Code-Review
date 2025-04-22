package org.eclipse.jgit.transport;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jgit.transport.NonceGenerator;

public class HMACSHA1NonceGenerator implements NonceGenerator {

	public String createNonce(String seed
			throws IllegalStateException {

		byte[] keyBytes = key.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes

		Mac mac = null;
		try {
			mac.init(signingKey);
		} catch (InvalidKeyException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
		byte[] rawHmac = mac.doFinal(seed.getBytes());
		String hexString = String.format("%20X"
		return hexString;
	}
}
