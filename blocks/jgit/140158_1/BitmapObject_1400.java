
package org.eclipse.jgit.lib;

import java.util.Iterator;

import org.eclipse.jgit.internal.storage.file.PackBitmapIndex;

public interface BitmapIndex {
	Bitmap getBitmap(AnyObjectId objectId);

	BitmapBuilder newBitmapBuilder();

	public interface Bitmap extends Iterable<BitmapObject> {
		Bitmap or(Bitmap other);

		Bitmap andNot(Bitmap other);

		Bitmap xor(Bitmap other);

		@Override
		Iterator<BitmapObject> iterator();
	}

	public interface BitmapBuilder extends Bitmap {

		boolean contains(AnyObjectId objectId);

		BitmapBuilder addObject(AnyObjectId objectId

		void remove(AnyObjectId objectId);

		@Override
		BitmapBuilder or(Bitmap other);

		@Override
		BitmapBuilder andNot(Bitmap other);

		@Override
		BitmapBuilder xor(Bitmap other);

		Bitmap build();

		boolean removeAllOrNone(PackBitmapIndex bitmapIndex);

		int cardinality();

		BitmapIndex getBitmapIndex();
	}
}
