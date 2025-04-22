package org.eclipse.jgit.lfs.test;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lfs.lib.LongObjectId;

public class LongObjectIdTestUtils {

	public static LongObjectId hash(String s) {
		MessageDigest md = Constants.newMessageDigest();
		md.update(s.getBytes(UTF_8));
		return LongObjectId.fromRaw(md.digest());
	}

	public static LongObjectId hash(Path file)
			throws FileNotFoundException
		MessageDigest md = Constants.newMessageDigest();
		try (InputStream is = new BufferedInputStream(
				Files.newInputStream(file))) {
			final byte[] buffer = new byte[4096];
			for (int read = 0; (read = is.read(buffer)) != -1;) {
				md.update(buffer
			}
		}
		return LongObjectId.fromRaw(md.digest());
	}
}
