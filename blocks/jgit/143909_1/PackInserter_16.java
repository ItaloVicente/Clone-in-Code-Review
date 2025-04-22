package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

class PackFileSnapshot extends FileSnapshot {

	private static final ObjectId MISSING_CHECKSUM = ObjectId.zeroId();

	public static PackFileSnapshot save(File path) {
		return new PackFileSnapshot(path);
	}

	private AnyObjectId checksum = MISSING_CHECKSUM;

	private boolean wasChecksumChanged;


	PackFileSnapshot(File packFile) {
		super(packFile);
	}

	void setChecksum(AnyObjectId checksum) {
		this.checksum = checksum;
	}

	@Override
	public boolean isModified(File packFile) {
		if (!super.isModified(packFile)) {
			return false;
		}
		if (wasSizeChanged() || wasFileKeyChanged()
				|| !wasLastModifiedRacilyClean()) {
			return true;
		}
		return isChecksumChanged(packFile);
	}

	boolean isChecksumChanged(File packFile) {
		return wasChecksumChanged = checksum != MISSING_CHECKSUM
				&& !checksum.equals(readChecksum(packFile));
	}

	private AnyObjectId readChecksum(File packFile) {
		try (RandomAccessFile fd = new RandomAccessFile(packFile
			fd.seek(fd.length() - 20);
			final byte[] buf = new byte[20];
			fd.readFully(buf
			return ObjectId.fromRaw(buf);
		} catch (IOException e) {
			return MISSING_CHECKSUM;
		}
	}

	boolean wasChecksumChanged() {
		return wasChecksumChanged;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "PackFileSnapshot [checksum=" + checksum + "
				+ super.toString() + "]";
	}

}
