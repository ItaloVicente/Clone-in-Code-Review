
package org.eclipse.jgit.storage.file;

import javaewah.EWAHCompressedBitmap;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;

abstract class BasePackBitmapIndex extends PackBitmapIndex {
	private final ObjectIdOwnerMap<StoredBitmap> bitmaps;

	BasePackBitmapIndex(ObjectIdOwnerMap<StoredBitmap> bitmaps) {
		this.bitmaps = bitmaps;
	}

	public EWAHCompressedBitmap getBitmap(AnyObjectId objectId) {
		StoredBitmap sb = bitmaps.get(objectId);
		return sb != null ? sb.getBitmap() : null;
	}

	ObjectIdOwnerMap<StoredBitmap> getBitmaps() {
		return bitmaps;
	}

	static final class StoredBitmap extends ObjectIdOwnerMap.Entry {
		private volatile Object bitmapContainer;

		StoredBitmap(AnyObjectId objectId
				StoredBitmap xorBitmap) {
			super(objectId);
			this.bitmapContainer = xorBitmap == null
					? bitmap
					: new XorCompressedBitmap(bitmap
		}

		EWAHCompressedBitmap getBitmap() {
			Object r = bitmapContainer;
			if (r instanceof EWAHCompressedBitmap)
				return (EWAHCompressedBitmap) r;

			EWAHCompressedBitmap out = ((XorCompressedBitmap) r).bitmap;
			StoredBitmap sb = this;
			while ((sb = sb.xorBitmap()) != null)
				out = out.xor(sb.bitmap());
			bitmapContainer = out;
			return out;
		}

		private EWAHCompressedBitmap bitmap() {
			Object r = bitmapContainer;
			if (r instanceof EWAHCompressedBitmap)
				return (EWAHCompressedBitmap) r;
			return ((XorCompressedBitmap) r).bitmap;
		}

		private StoredBitmap xorBitmap() {
			Object r = bitmapContainer;
			if (r instanceof EWAHCompressedBitmap)
				return null;
			return ((XorCompressedBitmap) r).xorBitmap;
		}
	}

	private static final class XorCompressedBitmap {
		final EWAHCompressedBitmap bitmap;
		final StoredBitmap xorBitmap;

		XorCompressedBitmap(EWAHCompressedBitmap b
			bitmap = b;
			xorBitmap = xb;
		}
	}
}
