
package org.eclipse.jgit.internal.storage.file;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.BitmapIndexImpl.CompressedBitmap;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BitmapIndex.Bitmap;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.util.BlockList;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public class PackBitmapIndexBuilder extends BasePackBitmapIndex {
	private static final int MAX_XOR_OFFSET_SEARCH = 10;

	private final EWAHCompressedBitmap commits;
	private final EWAHCompressedBitmap trees;
	private final EWAHCompressedBitmap blobs;
	private final EWAHCompressedBitmap tags;
	private final BlockList<PositionEntry> byOffset;
	final BlockList<StoredBitmap>
			byAddOrder = new BlockList<>();
	final ObjectIdOwnerMap<PositionEntry>
			positionEntries = new ObjectIdOwnerMap<>();

	public PackBitmapIndexBuilder(List<ObjectToPack> objects) {
		super(new ObjectIdOwnerMap<StoredBitmap>());
		byOffset = new BlockList<>(objects.size());
		sortByOffsetAndIndex(byOffset

		int sizeInWords = Math.max(4
		commits = new EWAHCompressedBitmap(sizeInWords);
		trees = new EWAHCompressedBitmap(sizeInWords);
		blobs = new EWAHCompressedBitmap(sizeInWords);
		tags = new EWAHCompressedBitmap(sizeInWords);
		for (int i = 0; i < objects.size(); i++) {
			int type = objects.get(i).getType();
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
		commits.trim();
		trees.trim();
		blobs.trim();
		tags.trim();
	}

	private static void sortByOffsetAndIndex(BlockList<PositionEntry> byOffset
			ObjectIdOwnerMap<PositionEntry> positionEntries
			List<ObjectToPack> entries) {
		for (int i = 0; i < entries.size(); i++) {
			positionEntries.add(new PositionEntry(entries.get(i)
		}
		Collections.sort(entries
			@Override
			public int compare(ObjectToPack a
				return Long.signum(a.getOffset() - b.getOffset());
			}
		});
		for (int i = 0; i < entries.size(); i++) {
			PositionEntry e = positionEntries.get(entries.get(i));
			e.offsetPosition = i;
			byOffset.add(e);
		}
	}

	public ObjectIdOwnerMap<ObjectIdOwnerMap.Entry> getObjectSet() {
		ObjectIdOwnerMap<ObjectIdOwnerMap.Entry> r = new ObjectIdOwnerMap<>();
		for (PositionEntry e : byOffset) {
			r.add(new ObjectIdOwnerMap.Entry(e) {
			});
		}
		return r;
	}

	public void addBitmap(AnyObjectId objectId
		if (bitmap instanceof BitmapBuilder)
			bitmap = ((BitmapBuilder) bitmap).build();

		EWAHCompressedBitmap compressed;
		if (bitmap instanceof CompressedBitmap)
			compressed = ((CompressedBitmap) bitmap).getEwahCompressedBitmap();
		else
			throw new IllegalArgumentException(bitmap.getClass().toString());

		addBitmap(objectId
	}

	public void addBitmap(
			AnyObjectId objectId
		bitmap.trim();
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
			return -1;
		return entry.offsetPosition;
	}

	@Override
	public ObjectId getObject(int position) throws IllegalArgumentException {
		ObjectId objectId = byOffset.get(position);
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

	@Override
	public int getBitmapCount() {
		return getBitmaps().size();
	}

	public void clearBitmaps() {
		byAddOrder.clear();
		getBitmaps().clear();
	}

	@Override
	public int getObjectCount() {
		return byOffset.size();
	}

	public Iterable<StoredEntry> getCompressedBitmaps() {
		return new Iterable<StoredEntry>() {
			@Override
			public Iterator<StoredEntry> iterator() {
				return new Iterator<StoredEntry>() {
					private int index = byAddOrder.size() - 1;

					@Override
					public boolean hasNext() {
						return index >= 0;
					}

					@Override
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
						bestBitmap.trim();
						return new StoredEntry(entry.namePosition
								bestXorOffset
					}

					@Override
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
		private final int flags;

		StoredEntry(long objectId
				int xorOffset
			this.objectId = objectId;
			this.bitmap = bitmap;
			this.xorOffset = xorOffset;
			this.flags = flags;
		}

		public EWAHCompressedBitmap getBitmap() {
			return bitmap;
		}

		public int getXorOffset() {
			return xorOffset;
		}

		public int getFlags() {
			return flags;
		}

		public long getObjectId() {
			return objectId;
		}
	}

	private static final class PositionEntry extends ObjectIdOwnerMap.Entry {
		final int namePosition;

		int offsetPosition;

		PositionEntry(AnyObjectId objectId
			super(objectId);
			this.namePosition = namePosition;
		}
	}
}
