
package org.eclipse.jgit.storage.file;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import javaewah.EWAHCompressedBitmap;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.storage.file.BasePackBitmapIndex.StoredBitmap;

class PackIndexVE003 extends PackIndexV2 {
	static final int OPT_FULL = 1;

	private static final int MAX_XOR_OFFSET = 126;

	private final EWAHCompressedBitmap commits;

	private final EWAHCompressedBitmap trees;

	private final EWAHCompressedBitmap blobs;

	private final EWAHCompressedBitmap tags;

	private final ObjectIdOwnerMap<StoredBitmap> bitmaps;

	PackIndexVE003(final InputStream fd) throws IOException {
		super(fd);

		if (getObjectCount() > Integer.MAX_VALUE)
			throw new IOException(JGitText.get().indexFileIsTooLargeForJgit);

		int options = fd.read();
		switch (options) {
		case OPT_FULL:
			break;
		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().expectedGot
					Integer.valueOf(options)));
		}

		SimpleDataInput dataInput = new SimpleDataInput(fd);
		this.commits = readBitmap(dataInput);
		this.trees = readBitmap(dataInput);
		this.blobs = readBitmap(dataInput);
		this.tags = readBitmap(dataInput);

		long numEntries = dataInput.readUnsignedInt();
		if (numEntries > Integer.MAX_VALUE)
			throw new IOException(JGitText.get().indexFileIsTooLargeForJgit);

		bitmaps = new ObjectIdOwnerMap<StoredBitmap>();
		StoredBitmap[] recentBitmaps = new StoredBitmap[MAX_XOR_OFFSET];
		for (int i = 0; i < (int) numEntries; i++) {
			int nthObjectId = dataInput.readInt();
			int xorOffset = fd.read();
			EWAHCompressedBitmap bitmap = readBitmap(dataInput);

			if (nthObjectId < 0)
				throw new IOException(MessageFormat.format(
						JGitText.get().invalidId
			if (xorOffset < 0)
				throw new IOException(MessageFormat.format(
						JGitText.get().invalidId
			if (xorOffset > MAX_XOR_OFFSET)
				throw new IOException(MessageFormat.format(
						JGitText.get().expectedLessThanGot
						String.valueOf(MAX_XOR_OFFSET)
						String.valueOf(xorOffset)));
			if (xorOffset > i)
				throw new IOException(MessageFormat.format(
						JGitText.get().expectedLessThanGot
						String.valueOf(xorOffset)));

			ObjectId objectId = getObjectId(nthObjectId);
			StoredBitmap xorBitmap = null;
			if (xorOffset > 0) {
				int index = (i - xorOffset);
				xorBitmap = recentBitmaps[index % recentBitmaps.length];
				if (xorBitmap == null)
					throw new IOException(MessageFormat.format(
							JGitText.get().invalidId
							String.valueOf(xorOffset)));
			}

			StoredBitmap sb = new StoredBitmap(objectId
			bitmaps.add(sb);
			recentBitmaps[i % recentBitmaps.length] = sb;
		}
	}

	@Override
	public boolean hasBitmapIndex() {
		return true;
	}

	@Override
	public PackBitmapIndex getBitmapIndex(PackReverseIndex reverseIndex) {
		return new PackBitmapIndexImpl(reverseIndex);
	}

	private static EWAHCompressedBitmap readBitmap(DataInput dataInput)
			throws IOException {
		EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();
		bitmap.deserialize(dataInput);
		return bitmap;
	}

	private final class PackBitmapIndexImpl extends BasePackBitmapIndex {
		private final PackReverseIndex reverseIndex;

		private PackBitmapIndexImpl(PackReverseIndex reverseIndex) {
			super(bitmaps);
			this.reverseIndex = reverseIndex;
		}

		@Override
		public int findPosition(AnyObjectId objectId) {
			long offset = findOffset(objectId);
			if (offset == -1)
				return -1;
			return reverseIndex.findPostion(offset);
		}

		@Override
		public ObjectId getObject(int position)
				throws IllegalArgumentException {
			ObjectId objectId = reverseIndex.findObjectByPosition(position);
			if (objectId == null)
				throw new IllegalArgumentException();
			return objectId;
		}

		@Override
		public int getObjectCount() {
			return (int) PackIndexVE003.this.getObjectCount();
		}

		@Override
		public EWAHCompressedBitmap ofObjectType(
				EWAHCompressedBitmap bitmap
			switch (type) {
			case Constants.OBJ_BLOB:
				return blobs.and(bitmap);
			case Constants.OBJ_TREE:
				return trees.and(bitmap);
			case Constants.OBJ_COMMIT:
				return commits.and(bitmap);
			case Constants.OBJ_TAG:
				return tags.and(bitmap);
			}
			throw new IllegalArgumentException();
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof PackBitmapIndexImpl)
				return getPackIndex()
						== ((PackBitmapIndexImpl) o).getPackIndex();
			return false;
		}

		@Override
		public int hashCode() {
			return getPackIndex().hashCode();
		}

		PackIndex getPackIndex() {
			return PackIndexVE003.this;
		}
	}
}
