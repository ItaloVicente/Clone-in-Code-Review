
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.io.SilentFileInputStream;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public abstract class PackBitmapIndex {
	public static final int FLAG_REUSE = 1;

	public static PackBitmapIndex open(
			File idxFile
			throws IOException {
		try (SilentFileInputStream fd = new SilentFileInputStream(
				idxFile)) {
			try {
				return read(fd
			} catch (IOException ioe) {
				throw new IOException(
						MessageFormat.format(JGitText.get().unreadablePackIndex
								idxFile.getAbsolutePath())
						ioe);
			}
		}
	}

	public static PackBitmapIndex read(
			InputStream fd
			throws IOException {
		return new PackBitmapIndexV1(fd
	}

	byte[] packChecksum;

	public abstract int findPosition(AnyObjectId objectId);

	public abstract ObjectId getObject(int position) throws IllegalArgumentException;

	public abstract EWAHCompressedBitmap ofObjectType(
			EWAHCompressedBitmap bitmap

	public abstract EWAHCompressedBitmap getBitmap(AnyObjectId objectId);

	public abstract int getObjectCount();

	public abstract int getBitmapCount();
}
