
package org.eclipse.jgit.storage.file;

import java.io.File;

import org.eclipse.jgit.util.SystemReader;

public class FileSnapshot {
	public static final FileSnapshot DIRTY = new FileSnapshot(-1

	public static FileSnapshot save(File path) {
		final long read = SystemReader.getInstance().getCurrentTime();
		final long modified = path.lastModified();
		return new FileSnapshot(read
	}

	private final long lastModified;

	private volatile long lastRead;

	private boolean cannotBeRacilyClean;

	private FileSnapshot(long read
		this.lastRead = read;
		this.lastModified = modified;
		this.cannotBeRacilyClean = notRacyClean(read);
	}

	public boolean isModified(File path) {
		return isModified(path.lastModified());
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

	private boolean notRacyClean(final long read) {
		return read - lastModified > 2500;
	}

	private boolean isModified(final long currLastModified) {
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
