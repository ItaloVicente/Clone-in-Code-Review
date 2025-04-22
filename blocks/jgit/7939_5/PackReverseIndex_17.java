
package org.eclipse.jgit.storage.file;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

import javaewah.EWAHCompressedBitmap;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.storage.file.PackBitmapIndexBuilder.StoredEntry;

class PackIndexWriterVE003 extends PackIndexWriterV2 {
	private final DataOutput dataOutput;

	PackIndexWriterVE003(final OutputStream dst) {
		super(dst);
		dataOutput = new SimpleDataOutput(out);
	}

	@Override
	protected void writeImpl() throws IOException {
		if (bitmaps == null)
			throw new IllegalStateException();

		writeTOC(0xE003);
		writeV2Body();
		writeVE003Body();
		writeChecksumFooter();
	}

	private void writeVE003Body() throws IOException {
		writeOptions();
		writeBitmap(bitmaps.getCommits());
		writeBitmap(bitmaps.getTrees());
		writeBitmap(bitmaps.getBlobs());
		writeBitmap(bitmaps.getTags());
		writeBitmaps();
	}

	private void writeOptions() throws IOException {
		out.write(bitmaps.getOptions());
	}

	private void writeBitmap(EWAHCompressedBitmap bitmap) throws IOException {
		bitmap.serialize(dataOutput);
	}

	private void writeBitmaps() throws IOException {
		int expectedBitmapCount = bitmaps.getBitmapCount();
		dataOutput.writeInt(expectedBitmapCount);

		int bitmapCount = 0;
		for (StoredEntry entry : bitmaps.getCompressedBitmaps()) {
			writeBitmapEntry(entry);
			bitmapCount++;
		}

		if (expectedBitmapCount != bitmapCount)
			throw new IOException(MessageFormat.format(
					JGitText.get().expectedGot
					String.valueOf(expectedBitmapCount)
					String.valueOf(bitmapCount)));
	}

	private void writeBitmapEntry(StoredEntry entry) throws IOException {
		dataOutput.writeInt((int) entry.getObjectId());
		out.write(entry.getXorOffset());
		writeBitmap(entry.getBitmap());
	}
}
