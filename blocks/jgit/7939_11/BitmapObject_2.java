
package org.eclipse.jgit.lib;

import java.util.Iterator;

import org.eclipse.jgit.storage.file.PackBitmapIndex;

public interface BitmapIndex {
	Bitmap getBitmap(AnyObjectId objectId);

	BitmapBuilder newBitmapBuilder();

	public interface Bitmap extends Iterable<BitmapObject> {
		Bitmap or(Bitmap other);

		Bitmap andNot(Bitmap other);

		Bitmap xor(Bitmap other);

		Iterator<BitmapObject> iterator();
	}

	public interface BitmapBuilder extends Bitmap {
		boolean add(AnyObjectId objectId

		boolean contains(AnyObjectId objectId);

		void remove(AnyObjectId objectId);

		BitmapBuilder or(Bitmap other);

		BitmapBuilder andNot(Bitmap other);

		BitmapBuilder xor(Bitmap other);

		Bitmap build();

		boolean removeAllOrNone(PackBitmapIndex bitmapIndex);

		int cardinality();
	}
}
