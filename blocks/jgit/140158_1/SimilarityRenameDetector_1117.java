
package org.eclipse.jgit.diff;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;

public class SimilarityIndex {
	public static final TableFullException
			TABLE_FULL_OUT_OF_MEMORY = new TableFullException();

	private static final int KEY_SHIFT = 32;

	private static final long MAX_COUNT = (1L << KEY_SHIFT) - 1;

	private long hashedCnt;

	private int idSize;

	private int idGrowAt;

	private long[] idHash;

	private int idHashBits;

	public static SimilarityIndex create(ObjectLoader obj) throws IOException
			TableFullException {
		SimilarityIndex idx = new SimilarityIndex();
		idx.hash(obj);
		idx.sort();
		return idx;
	}

	SimilarityIndex() {
		idHashBits = 8;
		idHash = new long[1 << idHashBits];
		idGrowAt = growAt(idHashBits);
	}

	void hash(ObjectLoader obj) throws MissingObjectException
			TableFullException {
		if (obj.isLarge()) {
			hashLargeObject(obj);
		} else {
			byte[] raw = obj.getCachedBytes();
			hash(raw
		}
	}

	private void hashLargeObject(ObjectLoader obj) throws IOException
			TableFullException {
		boolean text;
		try (ObjectStream in1 = obj.openStream()) {
			text = !RawText.isBinary(in1);
		}

		try (ObjectStream in2 = obj.openStream()) {
			hash(in2
		}
	}

	void hash(byte[] raw
		final boolean text = !RawText.isBinary(raw);
		hashedCnt = 0;
		while (ptr < end) {
			int hash = 5381;
			int blockHashedCnt = 0;
			int start = ptr;

			do {
				int c = raw[ptr++] & 0xff;
				if (text && c == '\r' && ptr < end && raw[ptr] == '\n')
					continue;
				blockHashedCnt++;
				if (c == '\n')
					break;
				hash = (hash << 5) + hash + c;
			} while (ptr < end && ptr - start < 64);
			hashedCnt += blockHashedCnt;
			add(hash
		}
	}

	void hash(InputStream in
			TableFullException {
		byte[] buf = new byte[4096];
		int ptr = 0;
		int cnt = 0;

		while (0 < remaining) {
			int hash = 5381;
			int blockHashedCnt = 0;

			int n = 0;
			do {
				if (ptr == cnt) {
					ptr = 0;
					cnt = in.read(buf
					if (cnt <= 0)
						throw new EOFException();
				}

				n++;
				int c = buf[ptr++] & 0xff;
				if (text && c == '\r' && ptr < cnt && buf[ptr] == '\n')
					continue;
				blockHashedCnt++;
				if (c == '\n')
					break;
				hash = (hash << 5) + hash + c;
			} while (n < 64 && n < remaining);
			hashedCnt += blockHashedCnt;
			add(hash
			remaining -= n;
		}
	}

	void sort() {
		Arrays.sort(idHash);
	}

	public int score(SimilarityIndex dst
		long max = Math.max(hashedCnt
		if (max == 0)
			return maxScore;
		return (int) ((common(dst) * maxScore) / max);
	}

	long common(SimilarityIndex dst) {
		return common(this
	}

	private static long common(SimilarityIndex src
		int srcIdx = src.packedIndex(0);
		int dstIdx = dst.packedIndex(0);
		long[] srcHash = src.idHash;
		long[] dstHash = dst.idHash;
		return common(srcHash
	}

	private static long common(long[] srcHash
			long[] dstHash
		if (srcIdx == srcHash.length || dstIdx == dstHash.length)
			return 0;

		long common = 0;
		int srcKey = keyOf(srcHash[srcIdx]);
		int dstKey = keyOf(dstHash[dstIdx]);

		for (;;) {
			if (srcKey == dstKey) {
				common += Math.min(countOf(srcHash[srcIdx])
						countOf(dstHash[dstIdx]));

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

		int j = slot(key);
		for (;;) {
			long v = idHash[j];
			if (v == 0) {
				if (idGrowAt <= idSize) {
					grow();
					j = slot(key);
					continue;
				}
				idHash[j] = pair(key
				idSize++;
				return;

			} else if (keyOf(v) == key) {
				idHash[j] = pair(key
				return;

			} else if (++j >= idHash.length) {
				j = 0;
			}
		}
	}

	private static long pair(int key
		if (MAX_COUNT < cnt)
			throw new TableFullException();
		return (((long) key) << KEY_SHIFT) | cnt;
	}

	private int slot(int key) {
		return key >>> (31 - idHashBits);
	}

	private static int growAt(int idHashBits) {
		return (1 << idHashBits) * (idHashBits - 3) / idHashBits;
	}

	private void grow() throws TableFullException {
		if (idHashBits == 30)
			throw new TableFullException();

		long[] oldHash = idHash;
		int oldSize = idHash.length;

		idHashBits++;
		idGrowAt = growAt(idHashBits);

		try {
			idHash = new long[1 << idHashBits];
		} catch (OutOfMemoryError noMemory) {
			throw TABLE_FULL_OUT_OF_MEMORY;
		}

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

	private static long countOf(long v) {
		return v & MAX_COUNT;
	}

	public static class TableFullException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
