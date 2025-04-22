
package org.eclipse.jgit.storage.file;

import javaewah.EWAHCompressedBitmap;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

public abstract class PackBitmapIndex {
	public abstract int findPosition(AnyObjectId objectId);

	public abstract ObjectId getObject(int position) throws IllegalArgumentException;

	public abstract EWAHCompressedBitmap ofObjectType(
			EWAHCompressedBitmap bitmap

	public abstract EWAHCompressedBitmap getBitmap(AnyObjectId objectId);

	public abstract int getObjectCount();
}
