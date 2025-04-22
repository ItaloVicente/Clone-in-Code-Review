
package org.eclipse.jgit.storage.file;

import javaewah.EWAHCompressedBitmap;
import javaewah.IntIterator;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BitmapIndex;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.storage.file.BasePackBitmapIndex.StoredBitmap;

public class PackBitmapIndexRemapper extends PackBitmapIndex {

	private final PackBitmapIndex oldPackIndex;
	private final PackBitmapIndex newPackIndex;
	private final ObjectIdOwnerMap<StoredBitmap> convertedBitmaps;
	private final BitSet inflated;
	private final int[] prevToNewMapping;

	public static PackBitmapIndex newPackBitmapIndex(
			BitmapIndex prevBitmapIndex
		if (!(prevBitmapIndex instanceof BitmapIndexImpl))
			return newIndex;

		return new PackBitmapIndexRemapper(
				((BitmapIndexImpl) prevBitmapIndex).getPackBitmapIndex()
				newIndex);
	}

	private PackBitmapIndexRemapper(
			PackBitmapIndex oldPackIndex
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

	@Override
	public EWAHCompressedBitmap getBitmap(AnyObjectId objectId) {
		EWAHCompressedBitmap bitmap = newPackIndex.getBitmap(objectId);
		if (bitmap != null)
			return bitmap;

		StoredBitmap stored = convertedBitmaps.get(objectId);
		if (stored != null)
			return stored.getBitmap();

		EWAHCompressedBitmap oldBitmap = oldPackIndex.getBitmap(objectId);
		if (oldBitmap == null)
			return null;

		inflated.clear();
		for (IntIterator ii = oldBitmap.intIterator(); ii.hasNext();)
			inflated.set(prevToNewMapping[ii.next()]);
		bitmap = inflated.toEWAHCompressedBitmap();
		convertedBitmaps.add(new StoredBitmap(objectId
		return bitmap;
	}
}
