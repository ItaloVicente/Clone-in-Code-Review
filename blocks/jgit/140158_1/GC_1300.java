
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.jgit.util.FS;

public class FileSnapshot {
	public static final long UNKNOWN_SIZE = -1;

	public static final FileSnapshot DIRTY = new FileSnapshot(-1

	public static final FileSnapshot MISSING_FILE = new FileSnapshot(0
		@Override
		public boolean isModified(File path) {
			return FS.DETECTED.exists(path);
		}
	};

	public static FileSnapshot save(File path) {
		long read = System.currentTimeMillis();
		long modified;
		long size;
		try {
			BasicFileAttributes fileAttributes = FS.DETECTED.fileAttributes(path);
			modified = fileAttributes.lastModifiedTime().toMillis();
			size = fileAttributes.size();
		} catch (IOException e) {
			modified = path.lastModified();
			size = path.length();
		}
		return new FileSnapshot(read
	}

	public static FileSnapshot save(long modified) {
		final long read = System.currentTimeMillis();
		return new FileSnapshot(read
	}

	private final long lastModified;

	private volatile long lastRead;

	private boolean cannotBeRacilyClean;

	private final long size;

	private FileSnapshot(long read
		this.lastRead = read;
		this.lastModified = modified;
		this.cannotBeRacilyClean = notRacyClean(read);
		this.size = size;
	}

	public long lastModified() {
		return lastModified;
	}

	public long size() {
		return size;
	}

	public boolean isModified(File path) {
		long currLastModified;
		long currSize;
		try {
			BasicFileAttributes fileAttributes = FS.DETECTED.fileAttributes(path);
			currLastModified = fileAttributes.lastModifiedTime().toMillis();
			currSize = fileAttributes.size();
		} catch (IOException e) {
			currLastModified = path.lastModified();
			currSize = path.length();
		}
		return (currSize != UNKNOWN_SIZE && currSize != size) || isModified(currLastModified);
	}

	public void setClean(FileSnapshot other) {
		final long now = other.lastRead;
		if (notRacyClean(now))
			cannotBeRacilyClean = true;
		lastRead = now;
	}

	public boolean equals(FileSnapshot other) {
		return lastModified == other.lastModified;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof FileSnapshot)
			return equals((FileSnapshot) other);
		return false;
	}

	@Override
	public int hashCode() {
		return (int) lastModified;
	}

	@Override
	public String toString() {
		if (this == DIRTY)
		if (this == MISSING_FILE)
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"
				Locale.US);
				+ "
	}

	private boolean notRacyClean(long read) {
		return read - lastModified > 2500;
	}

	private boolean isModified(long currLastModified) {
		if (lastModified != currLastModified)
			return true;

		if (cannotBeRacilyClean)
			return false;

		if (notRacyClean(lastRead)) {
			return false;
		}

		return true;
	}
}
