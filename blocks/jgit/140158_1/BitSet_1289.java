
package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;

import com.googlecode.javaewah.EWAHCompressedBitmap;

abstract class BasePackBitmapIndex extends PackBitmapIndex {
	private final ObjectIdOwnerMap<StoredBitmap> bitmaps;

	BasePackBitmapIndex(ObjectIdOwnerMap<StoredBitmap> bitmaps) {
		this.bitmaps = bitmaps;
	}

	@Override
	public EWAHCompressedBitmap getBitmap(AnyObjectId objectId) {
		StoredBitmap sb = bitmaps.get(objectId);
		return sb != null ? sb.getBitmap() : null;
	}

	ObjectIdOwnerMap<StoredBitmap> getBitmaps() {
		return bitmaps;
	}

	static final class StoredBitmap extends ObjectIdOwnerMap.Entry {
		private volatile Object bitmapContainer;
		private final int flags;

		StoredBitmap(AnyObjectId objectId
				StoredBitmap xorBitmap
			super(objectId);
			this.bitmapContainer = xorBitmap == null
					? bitmap
					: new XorCompressedBitmap(bitmap
			this.flags = flags;
		}

		EWAHCompressedBitmap getBitmap() {
			Object r = bitmapContainer;
			if (r instanceof EWAHCompressedBitmap)
				return (EWAHCompressedBitmap) r;

			XorCompressedBitmap xb = (XorCompressedBitmap) r;
			EWAHCompressedBitmap out = xb.bitmap;
			for (;;) {
				r = xb.xorBitmap.bitmapContainer;
				if (r instanceof EWAHCompressedBitmap) {
					out = out.xor((EWAHCompressedBitmap) r);
					out.trim();
					bitmapContainer = out;
					return out;
				}
				xb = (XorCompressedBitmap) r;
				out = out.xor(xb.bitmap);
			}
		}

		int getFlags() {
			return flags;
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
