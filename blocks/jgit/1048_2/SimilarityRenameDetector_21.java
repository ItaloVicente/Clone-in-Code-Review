
package org.eclipse.jgit.diff;

import java.util.Arrays;

import org.eclipse.jgit.lib.ObjectLoader;

class SimilarityIndex {
	private static final int MAX_HASH_BITS = 17;

	private static final int MAX_HASH_SIZE = 1 << MAX_HASH_BITS;

	private static final int P = 131071;

	private static final int KEY_SHIFT = 64 - 1 - MAX_HASH_BITS;

	private long fileSize;

	private int idSize;

	private long[] idHash;

	SimilarityIndex() {
		idHash = new long[256];
	}

	long getFileSize() {
		return fileSize;
	}

	void setFileSize(long size) {
		fileSize = size;
	}

	void hash(ObjectLoader obj) {
		byte[] raw = obj.getCachedBytes();
		setFileSize(raw.length);
		hash(raw
	}

	void hash(byte[] raw
		while (ptr < end) {
			int hash = 5381;
			int start = ptr;

			do {
				int c = raw[ptr++] & 0xff;
				if (c == '\n')
					break;
				hash = (hash << 5) ^ c;
			} while (ptr < end && ptr - start < 64);
			add(hash
		}
	}

	void sort() {
		Arrays.sort(idHash);
	}

	int score(SimilarityIndex dst) {
		long max = Math.max(fileSize
		return (int) ((common(dst) * 100L) / max);
	}

	int common(SimilarityIndex dst) {
		return common(this
	}

	private static int common(SimilarityIndex src
		int srcIdx = src.packedIndex(0);
		int dstIdx = dst.packedIndex(0);
		long[] srcHash = src.idHash;
		long[] dstHash = dst.idHash;
		return common(srcHash
	}

	private static int common(long[] srcHash
			long[] dstHash
		if (srcIdx == srcHash.length || dstIdx == dstHash.length)
			return 0;

		int common = 0;
		int srcKey = keyOf(srcHash[srcIdx]);
		int dstKey = keyOf(dstHash[dstIdx]);

		for (;;) {
			if (srcKey == dstKey) {
				common += countOf(dstHash[dstIdx]);

				if (++srcIdx == srcHash.length)
					break;
				srcKey = keyOf(srcHash[srcIdx]);

				if (++dstIdx == dstHash.length)
					break;
				dstKey = keyOf(dstHash[dstIdx]);

			} else if (srcKey < dstKey) {
				if (++srcIdx == srcHash.length)
					break;
				srcKey = keyOf(srcHash[srcIdx]);

				if (++dstIdx == dstHash.length)
					break;
				dstKey = keyOf(dstHash[dstIdx]);
			}
		}

		return common;
	}

	int size() {
		return idSize;
	}

	int key(int idx) {
		return keyOf(idHash[packedIndex(idx)]);
	}

	long count(int idx) {
		return countOf(idHash[packedIndex(idx)]);
	}

	int findIndex(int key) {
		for (int i = 0; i < idSize; i++)
			if (key(i) == key)
				return i;
		return -1;
	}

	private int packedIndex(int idx) {
		return (idHash.length - idSize) + idx;
	}

	void add(int key
		key = hash(key);
		int j = slot(key);
		for (;;) {
			long v = idHash[j];
			if (v == 0) {
				if (shouldGrow()) {
					grow();
					j = slot(key);
					continue;
				}
				idHash[j] = (((long) key) << KEY_SHIFT) | cnt;
				idSize++;
				return;

			} else if (keyOf(v) == key) {
				idHash[j] = v + cnt;
				return;

			} else if (++j >= idHash.length) {
				j = 0;
			}
		}
	}

	private static int hash(int key) {
		return (key >>> 1) % P;
	}

	private int slot(int key) {
		return key % idHash.length;
	}

	private boolean shouldGrow() {
		int n = idHash.length;
		return n < MAX_HASH_SIZE && n <= idSize * 2;
	}

	private void grow() {
		long[] oldHash = idHash;
		int oldSize = idHash.length;

		idHash = new long[2 * oldSize];
		for (int i = 0; i < oldSize; i++) {
			long v = oldHash[i];
			if (v != 0) {
				int j = slot(keyOf(v));
				while (idHash[j] != 0)
					if (++j >= idHash.length)
						j = 0;
				idHash[j] = v;
			}
		}
	}

	private static int keyOf(long v) {
		return (int) (v >>> KEY_SHIFT);
	}

	private static int countOf(long v) {
		return (int) v;
	}
}
