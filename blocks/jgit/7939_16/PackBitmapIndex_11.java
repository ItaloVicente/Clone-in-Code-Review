
package org.eclipse.jgit.storage.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import javaewah.EWAHCompressedBitmap;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

public abstract class PackBitmapIndex {
	public static final int FLAG_REUSE = 1;

	public static PackBitmapIndex open(
			File idxFile
			throws IOException {
		final FileInputStream fd = new FileInputStream(idxFile);
		try {
			return read(fd
		} catch (IOException ioe) {
			final String path = idxFile.getAbsolutePath();
			final IOException err;
			err = new IOException(MessageFormat.format(
					JGitText.get().unreadablePackIndex
			err.initCause(ioe);
			throw err;
		} finally {
			try {
				fd.close();
			} catch (IOException err2) {
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
}
