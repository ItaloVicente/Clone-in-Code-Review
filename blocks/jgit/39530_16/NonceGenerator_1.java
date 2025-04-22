package org.eclipse.jgit.transport;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jgit.internal.storage.dfs.DfsRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.NonceGenerator;
import org.eclipse.jgit.transport.PushCertificate.NonceStatus;

public class HMACSHA1NonceGenerator implements NonceGenerator {

	private Mac mac;

	public HMACSHA1NonceGenerator(String seed) throws IllegalStateException {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes
			mac.init(signingKey);
		} catch (InvalidKeyException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	public synchronized String createNonce(Repository repo
			throws IllegalStateException {
		String path;
		if (repo instanceof DfsRepository)
			path = ((DfsRepository) repo).getDescription().getRepositoryName();
		else if (repo.getDirectory() != null)
			path = repo.getDirectory().getPath();
		else
			throw new IllegalStateException();

		byte[] rawHmac;
		try {
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
		String sentNonce = String.format(
				"%d-%20X"
		return sentNonce;
	}

	@Override
	public NonceStatus verify(String received
			Repository db
		if (received.isEmpty())
			return NonceStatus.MISSING;
		else if (sent.isEmpty())
			return NonceStatus.UNSOLICITED;
		else if (received.equals(sent))
			return NonceStatus.OK;

		if (!allowSlop)
			return NonceStatus.BAD;

		int idxSent = sent.indexOf('-');
		int idxRecv = received.indexOf('-');
		if (idxSent == -1 || idxRecv == -1)
			return NonceStatus.BAD;

		long signedStamp;
		long advertisedStamp;
		try {
			signedStamp = Long.parseLong(received.substring(0
			advertisedStamp = Long.parseLong(sent.substring(0
		} catch (Exception e) {
			return NonceStatus.BAD;
		}

		String expect = createNonce(db

		if (!expect.equals(received))
			return NonceStatus.BAD;

		long nonceStampSlop = Math.abs(advertisedStamp - signedStamp);

		if (nonceStampSlop <= slop) {
			return NonceStatus.OK;
		} else {
			return NonceStatus.SLOP;
		}
	}
}
