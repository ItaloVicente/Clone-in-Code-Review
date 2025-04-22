
package org.eclipse.jgit.internal.storage.pack;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import org.eclipse.jgit.storage.pack.PackConfig;

class DeltaCache {
	private final long size;

	private final int entryLimit;

	private final ReferenceQueue<byte[]> queue;

	private long used;

	DeltaCache(PackConfig pc) {
		size = pc.getDeltaCacheSize();
		entryLimit = pc.getDeltaCacheLimit();
		queue = new ReferenceQueue<>();
	}

	boolean canCache(int length
		if (0 < size && size < used + length) {
			checkForGarbageCollectedObjects();
			if (0 < size && size < used + length)
				return false;
		}

		if (length < entryLimit) {
			used += length;
			return true;
		}

		if (length >> 10 < (src.getWeight() >> 20) + (res.getWeight() >> 21)) {
			used += length;
			return true;
		}

		return false;
	}

	void credit(int reservedSize) {
		used -= reservedSize;
	}

	Ref cache(byte[] data
		data = resize(data

		if (reservedSize != data.length) {
			used -= reservedSize;
			used += data.length;
		}
		return new Ref(data
	}

	byte[] resize(byte[] data
		if (data.length != actLen) {
			byte[] nbuf = new byte[actLen];
			System.arraycopy(data
			data = nbuf;
		}
		return data;
	}

	private void checkForGarbageCollectedObjects() {
		Ref r;
		while ((r = (Ref) queue.poll()) != null)
			used -= r.cost;
	}

	static class Ref extends SoftReference<byte[]> {
		final int cost;

		Ref(byte[] array
			super(array
			cost = array.length;
		}
	}
}
