
package org.eclipse.jgit.storage.file;

import java.util.Collections;
import java.util.Iterator;

import javaewah.EWAHCompressedBitmap;
import javaewah.IntIterator;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BitmapIndex;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.storage.file.BasePackBitmapIndex.StoredBitmap;

public class PackBitmapIndexRemapper extends PackBitmapIndex
		implements Iterable<PackBitmapIndexRemapper.Entry> {

	private final BasePackBitmapIndex oldPackIndex;
	private final PackBitmapIndex newPackIndex;
	private final ObjectIdOwnerMap<StoredBitmap> convertedBitmaps;
	private final BitSet inflated;
	private final int[] prevToNewMapping;

	public static PackBitmapIndexRemapper newPackBitmapIndex(
			BitmapIndex prevBitmapIndex
		if (!(prevBitmapIndex instanceof BitmapIndexImpl))
			return new PackBitmapIndexRemapper(newIndex);

		PackBitmapIndex prevIndex = ((BitmapIndexImpl) prevBitmapIndex)
				.getPackBitmapIndex();
		if (!(prevIndex instanceof BasePackBitmapIndex))
			return new PackBitmapIndexRemapper(newIndex);

		return new PackBitmapIndexRemapper(
				(BasePackBitmapIndex) prevIndex
	}

	private PackBitmapIndexRemapper(PackBitmapIndex newPackIndex) {
		this.oldPackIndex = null;
		this.newPackIndex = newPackIndex;
		this.convertedBitmaps = null;
		this.inflated = null;
		this.prevToNewMapping = null;
	}

	private PackBitmapIndexRemapper(
			BasePackBitmapIndex oldPackIndex
		this.oldPackIndex = oldPackIndex;
		this.newPackIndex = newPackIndex;
		convertedBitmaps = new ObjectIdOwnerMap<StoredBitmap>();
		inflated = new BitSet(newPackIndex.getObjectCount());

		prevToNewMapping = new int[oldPackIndex.getObjectCount()];
		for (int pos = 0; pos < prevToNewMapping.length; pos++)
			prevToNewMapping[pos] = newPackIndex.findPosition(
					oldPackIndex.getObject(pos));
	}

	@Override
	public int findPosition(AnyObjectId objectId) {
		return newPackIndex.findPosition(objectId);
	}

	@Override
	public ObjectId getObject(int position) throws IllegalArgumentException {
		return newPackIndex.getObject(position);
	}

	@Override
	public int getObjectCount() {
		return newPackIndex.getObjectCount();
	}

	@Override
	public EWAHCompressedBitmap ofObjectType(
			EWAHCompressedBitmap bitmap
		return newPackIndex.ofObjectType(bitmap
	}

	public Iterator<Entry> iterator() {
		if (oldPackIndex == null)
			return Collections.<Entry> emptyList().iterator();

		final Iterator<StoredBitmap> it = oldPackIndex.getBitmaps().iterator();
		return new Iterator<Entry>() {
			public boolean hasNext() {
				return it.hasNext();
			}

			public Entry next() {
				StoredBitmap sb = it.next();
				return new Entry(sb
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public EWAHCompressedBitmap getBitmap(AnyObjectId objectId) {
		EWAHCompressedBitmap bitmap = newPackIndex.getBitmap(objectId);
		if (bitmap != null || oldPackIndex == null)
			return bitmap;

		StoredBitmap stored = convertedBitmaps.get(objectId);
		if (stored != null)
			return stored.getBitmap();

		StoredBitmap oldBitmap = oldPackIndex.getBitmaps().get(objectId);
		if (oldBitmap == null)
			return null;

		inflated.clear();
		for (IntIterator i = oldBitmap.getBitmap().intIterator(); i.hasNext();)
			inflated.set(prevToNewMapping[i.next()]);
		bitmap = inflated.toEWAHCompressedBitmap();
		convertedBitmaps.add(
				new StoredBitmap(objectId
		return bitmap;
	}

	public final class Entry extends ObjectId {
		private final int flags;

		private Entry(AnyObjectId src
			super(src);
			this.flags = flags;
		}

		public int getFlags() {
			return flags;
		}
	}
}
