
package org.eclipse.jgit.storage.file;

import java.io.BufferedOutputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.text.MessageFormat;

import javaewah.EWAHCompressedBitmap;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.storage.file.PackBitmapIndexBuilder.StoredEntry;
import org.eclipse.jgit.util.io.SafeBufferedOutputStream;

class PackBitmapIndexWriterV1 {
	private final DigestOutputStream out;
	private final DataOutput dataOutput;

	PackBitmapIndexWriterV1(final OutputStream dst) {
		out = new DigestOutputStream(dst instanceof BufferedOutputStream ? dst
				: new SafeBufferedOutputStream(dst)
				Constants.newMessageDigest());
		dataOutput = new SimpleDataOutput(out);
	}

	public void write(PackBitmapIndexBuilder bitmaps
			throws IOException {
		if (bitmaps == null || packDataChecksum.length != 20)
			throw new IllegalStateException();

		writeHeader(bitmaps.getOptions()
				packDataChecksum);
		writeBody(bitmaps);
		writeFooter();

		out.flush();
	}

	private void writeHeader(
			int options
			throws IOException {
		out.write(PackBitmapIndexV1.MAGIC);
		dataOutput.writeShort(1);
		dataOutput.writeShort(options);
		dataOutput.writeInt(bitmapCount);
		out.write(packDataChecksum);
	}

	private void writeBody(PackBitmapIndexBuilder bitmaps) throws IOException {
		writeBitmap(bitmaps.getCommits());
		writeBitmap(bitmaps.getTrees());
		writeBitmap(bitmaps.getBlobs());
		writeBitmap(bitmaps.getTags());
		writeBitmaps(bitmaps);
	}

	private void writeBitmap(EWAHCompressedBitmap bitmap) throws IOException {
		bitmap.serialize(dataOutput);
	}

	private void writeBitmaps(PackBitmapIndexBuilder bitmaps)
			throws IOException {
		int bitmapCount = 0;
		for (StoredEntry entry : bitmaps.getCompressedBitmaps()) {
			writeBitmapEntry(entry);
			bitmapCount++;
		}

		int expectedBitmapCount = bitmaps.getBitmapCount();
		if (expectedBitmapCount != bitmapCount)
			throw new IOException(MessageFormat.format(
					JGitText.get().expectedGot
					String.valueOf(expectedBitmapCount)
					String.valueOf(bitmapCount)));
	}

	private void writeBitmapEntry(StoredEntry entry) throws IOException {
		dataOutput.writeInt((int) entry.getObjectId());
		out.write(entry.getXorOffset());
		out.write(entry.getFlags());
		writeBitmap(entry.getBitmap());
	}

	private void writeFooter() throws IOException {
		out.on(false);
		out.write(out.getMessageDigest().digest());
	}
}
