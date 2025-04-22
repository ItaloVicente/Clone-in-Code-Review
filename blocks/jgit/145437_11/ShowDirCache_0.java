package org.eclipse.jgit.junit.time;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

import org.eclipse.jgit.util.FS;

public class TimeUtil {
	public static Instant setLastModifiedWithOffset(Path path
			long offsetMillis) {
		Instant mTime = FS.DETECTED.lastModifiedInstant(path)
				.plusMillis(offsetMillis);
		try {
			Files.setLastModifiedTime(path
			return mTime;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void setLastModifiedOf(Path a
		Instant mTime = FS.DETECTED.lastModifiedInstant(b);
		try {
			Files.setLastModifiedTime(a
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
