package org.eclipse.jgit.transport;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.NonceGenerator;
import org.eclipse.jgit.transport.PushCertificate.NonceStatus;

public class HMACSHA1NonceGenerator implements NonceGenerator {

	private String sentNonce;

	public String createNonce(String seed
			throws IllegalStateException {
		String path = repo.getDirectory() != null
				? repo.getDirectory().getPath()
		try {
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes
			mac.init(signingKey);
			sentNonce = String.format("%d-%20X"
			return sentNonce;
		} catch (InvalidKeyException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	public NonceStatus verify(String received
			Repository db
		if (received.isEmpty())
			return NonceStatus.MISSING;
		else if (sentNonce.isEmpty())
			return NonceStatus.UNSOLICITED;
		else if (received.equals(sentNonce))
			return NonceStatus.OK;


		int idxSent = sentNonce.indexOf('-');
		int idxRecv = received.indexOf('-');
		if (idxSent == -1 || idxRecv == -1)
			return NonceStatus.BAD;

		long signedStamp = 0;
		long advertisedStamp = 0;
		try {
			signedStamp = Integer.parseInt(received.substring(0
			advertisedStamp = Integer.parseInt(sentNonce.substring(0
		} catch (Exception e) {
			return NonceStatus.BAD;
		}

		String expect = createNonce(seed

		if (!expect.equals(received))
			return NonceStatus.BAD;

		long nonceStampSlop = Math.abs(advertisedStamp - signedStamp);

		if (allowedSlope != 0 && nonceStampSlop <= allowedSlope) {
			return NonceStatus.OK;
		} else {
			return NonceStatus.SLOP;
		}
	}
}
