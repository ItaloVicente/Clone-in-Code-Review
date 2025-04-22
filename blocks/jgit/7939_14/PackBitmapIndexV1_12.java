
package org.eclipse.jgit.storage.file;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javaewah.EWAHCompressedBitmap;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BitmapIndex.Bitmap;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.storage.file.BitmapIndexImpl.CompressedBitmap;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.util.BlockList;

public class PackBitmapIndexBuilder extends BasePackBitmapIndex {
	private static final int MAX_XOR_OFFSET_SEARCH = 10;

	private final EWAHCompressedBitmap commits;

	private final EWAHCompressedBitmap trees;

	private final EWAHCompressedBitmap blobs;

	private final EWAHCompressedBitmap tags;

	private final ObjectToPack[] byOffset;

	private final BlockList<StoredBitmap>
			byAddOrder = new BlockList<StoredBitmap>();

	private final ObjectIdOwnerMap<PositionEntry>
			positionEntries = new ObjectIdOwnerMap<PositionEntry>();

	public PackBitmapIndexBuilder(List<ObjectToPack> byName) {
		super(new ObjectIdOwnerMap<StoredBitmap>());
		byOffset = sortByOffset(byName);

		int sizeInWords = Math.max(byOffset.length / 64
		commits = new EWAHCompressedBitmap(sizeInWords);
		trees = new EWAHCompressedBitmap(sizeInWords);
		blobs = new EWAHCompressedBitmap(sizeInWords);
		tags = new EWAHCompressedBitmap(sizeInWords);
		for (int i = 0; i < byOffset.length; i++) {
			int type = byOffset[i].getType();
			switch (type) {
			case Constants.OBJ_COMMIT:
				commits.set(i);
				break;
			case Constants.OBJ_TREE:
				trees.set(i);
				break;
			case Constants.OBJ_BLOB:
				blobs.set(i);
				break;
			case Constants.OBJ_TAG:
				tags.set(i);
				break;
			default:
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().badObjectType
			}
		}
	}

	private ObjectToPack[] sortByOffset(List<ObjectToPack> entries) {
		ObjectToPack[] result = new ObjectToPack[entries.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = entries.get(i);
			positionEntries.add(new PositionEntry(result[i]
		}
		Arrays.sort(result
			public int compare(ObjectToPack a
				return Long.signum(a.getOffset() - b.getOffset());
			}
		});
		for (int i = 0; i < result.length; i++)
			positionEntries.get(result[i]).offsetPosition = i;
		return result;
	}

	public void addBitmap(AnyObjectId objectId
		if (bitmap instanceof BitmapBuilder)
			bitmap = ((BitmapBuilder) bitmap).build();

		EWAHCompressedBitmap compressed;
		if (bitmap instanceof CompressedBitmap)
			compressed = ((CompressedBitmap) bitmap).getEwahCompressedBitmap();
		else
			throw new IllegalArgumentException(bitmap.getClass().toString());

		StoredBitmap result = new StoredBitmap(objectId
		getBitmaps().add(result);
		byAddOrder.add(result);
	}

	@Override
	public EWAHCompressedBitmap ofObjectType(
			EWAHCompressedBitmap bitmap
		switch (type) {
		case Constants.OBJ_BLOB:
			return getBlobs().and(bitmap);
		case Constants.OBJ_TREE:
			return getTrees().and(bitmap);
		case Constants.OBJ_COMMIT:
			return getCommits().and(bitmap);
		case Constants.OBJ_TAG:
			return getTags().and(bitmap);
		}
		throw new IllegalArgumentException();
	}

	@Override
	public int findPosition(AnyObjectId objectId) {
		PositionEntry entry = positionEntries.get(objectId);
		if (entry == null)
			throw new IllegalStateException();
		return entry.offsetPosition;
	}

	@Override
	public ObjectId getObject(int position) throws IllegalArgumentException {
		ObjectId objectId = byOffset[position];
		if (objectId == null)
			throw new IllegalArgumentException();
		return objectId;
	}

	public EWAHCompressedBitmap getCommits() {
		return commits;
	}

	public EWAHCompressedBitmap getTrees() {
		return trees;
	}

	public EWAHCompressedBitmap getBlobs() {
		return blobs;
	}

	public EWAHCompressedBitmap getTags() {
		return tags;
	}

	public int getOptions() {
		return PackBitmapIndexV1.OPT_FULL;
	}

	public int getBitmapCount() {
		return getBitmaps().size();
	}

	public void clearBitmaps() {
		byAddOrder.clear();
		getBitmaps().clear();
	}

	@Override
	public int getObjectCount() {
		return byOffset.length;
	}

	public Iterable<StoredEntry> getCompressedBitmaps() {
		return new Iterable<StoredEntry>() {
			public Iterator<StoredEntry> iterator() {
				return new Iterator<StoredEntry>() {
					private int index = byAddOrder.size() - 1;

					public boolean hasNext() {
						return index >= 0;
					}

					public StoredEntry next() {
						if (!hasNext())
							throw new NoSuchElementException();
						StoredBitmap item = byAddOrder.get(index);
						int bestXorOffset = 0;
						EWAHCompressedBitmap bestBitmap = item.getBitmap();

						for (int i = 1; i <= MAX_XOR_OFFSET_SEARCH; i++) {
							int curr = i + index;
							if (curr >= byAddOrder.size())
								break;

							StoredBitmap other = byAddOrder.get(curr);
							EWAHCompressedBitmap bitmap = other.getBitmap()
									.xor(item.getBitmap());

							if (bitmap.sizeInBytes()
									< bestBitmap.sizeInBytes()) {
								bestBitmap = bitmap;
								bestXorOffset = i;
							}
						}
						index--;

						PositionEntry entry = positionEntries.get(item);
						if (entry == null)
							throw new IllegalStateException();
						return new StoredEntry(
								entry.namePosition
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

	public static final class StoredEntry {
		private final long objectId;

		private final EWAHCompressedBitmap bitmap;

		private final int xorOffset;

		private StoredEntry(
				long objectId
			this.objectId = objectId;
			this.bitmap = bitmap;
			this.xorOffset = xorOffset;
		}

		public EWAHCompressedBitmap getBitmap() {
			return bitmap;
		}

		public int getXorOffset() {
			return xorOffset;
		}

		public long getObjectId() {
			return objectId;
		}
	}

	private static final class PositionEntry extends ObjectIdOwnerMap.Entry {
		private final int namePosition;

		private int offsetPosition;

		private PositionEntry(AnyObjectId objectId
			super(objectId);
			this.namePosition = namePosition;
		}
	}
}
