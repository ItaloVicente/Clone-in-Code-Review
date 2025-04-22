package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jgit.internal.storage.dfs.DfsRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushCertificate.NonceStatus;

public class HMACSHA1NonceGenerator implements NonceGenerator {

	private Mac mac;

	public HMACSHA1NonceGenerator(String seed) throws IllegalStateException {
		try {
			byte[] keyBytes = seed.getBytes(ISO_8859_1);
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes
			mac.init(signingKey);
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public synchronized String createNonce(Repository repo
			throws IllegalStateException {
		String path;
		if (repo instanceof DfsRepository) {
			path = ((DfsRepository) repo).getDescription().getRepositoryName();
		} else {
			File directory = repo.getDirectory();
			if (directory != null) {
				path = directory.getPath();
			} else {
				throw new IllegalStateException();
			}
		}

		byte[] rawHmac = mac.doFinal(input.getBytes(UTF_8));
	}

	@Override
	public NonceStatus verify(String received
			Repository db
		if (received.isEmpty()) {
			return NonceStatus.MISSING;
		} else if (sent.isEmpty()) {
			return NonceStatus.UNSOLICITED;
		} else if (received.equals(sent)) {
			return NonceStatus.OK;
		}

		if (!allowSlop) {
			return NonceStatus.BAD;
		}

		int idxSent = sent.indexOf('-');
		int idxRecv = received.indexOf('-');
		if (idxSent == -1 || idxRecv == -1) {
			return NonceStatus.BAD;
		}

		String signedStampStr = received.substring(0
		String advertisedStampStr = sent.substring(0
		long signedStamp;
		long advertisedStamp;
		try {
			signedStamp = Long.parseLong(signedStampStr);
			advertisedStamp = Long.parseLong(advertisedStampStr);
		} catch (IllegalArgumentException e) {
			return NonceStatus.BAD;
		}

		String expect = createNonce(db

		if (!expect.equals(received)) {
			return NonceStatus.BAD;
		}

		long nonceStampSlop = Math.abs(advertisedStamp - signedStamp);

		if (nonceStampSlop <= slop) {
			return NonceStatus.OK;
		} else {
			return NonceStatus.SLOP;
		}
	}


	private static String toHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder(2 * bytes.length);
		for (byte b : bytes) {
			builder.append(HEX.charAt((b & 0xF0) >> 4));
			builder.append(HEX.charAt(b & 0xF));
		}
		return builder.toString();
	}
}
