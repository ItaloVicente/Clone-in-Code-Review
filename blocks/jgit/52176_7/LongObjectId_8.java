package org.eclipse.jgit.lfs.lib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;

import org.eclipse.jgit.lfs.internal.LfsText;

@SuppressWarnings("nls")
public final class Constants {
	private static final String LONG_HASH_FUNCTION = "SHA-256";

	public static final int LONG_OBJECT_ID_LENGTH = 32;

	public static final int LONG_OBJECT_ID_STRING_LENGTH = LONG_OBJECT_ID_LENGTH

	public static MessageDigest newMessageDigest() {
		try {
			return MessageDigest.getInstance(LONG_HASH_FUNCTION);
		} catch (NoSuchAlgorithmException nsae) {
			throw new RuntimeException(MessageFormat.format(
					LfsText.get().requiredHashFunctionNotAvailable
					LONG_HASH_FUNCTION)
		}
	}

	static {
		if (LONG_OBJECT_ID_LENGTH != newMessageDigest().getDigestLength())
			throw new LinkageError(
					LfsText.get().incorrectLONG_OBJECT_ID_LENGTH);
	}
}
