
package org.eclipse.jgit.internal.storage.pack;

import java.io.IOException;
import java.io.OutputStream;

public class DeltaIndex {

	public static long estimateIndexSize(int sourceLength) {
		return sourceLength + (sourceLength * 3 / 4);
	}

	private static final int MAX_CHAIN_LENGTH = 64;

	private final byte[] src;

	private final int[] table;

	private final long[] entries;

	private final int tableMask;

	public DeltaIndex(byte[] sourceBuffer) {
		src = sourceBuffer;

		DeltaIndexScanner scan = new DeltaIndexScanner(src

		table = scan.table;
		tableMask = scan.tableMask;

		entries = new long[1 + countEntries(scan)];
		copyEntries(scan);
	}

	private int countEntries(DeltaIndexScanner scan) {
		int cnt = 0;
		for (int i = 0; i < table.length; i++) {
			int h = table[i];
			if (h == 0)
				continue;

			int len = 0;
			do {
				if (++len == MAX_CHAIN_LENGTH) {
					scan.next[h] = 0;
					break;
				}
				h = scan.next[h];
			} while (h != 0);
			cnt += len;
		}
		return cnt;
	}

	private void copyEntries(DeltaIndexScanner scan) {
		int next = 1;
		for (int i = 0; i < table.length; i++) {
			int h = table[i];
			if (h == 0)
				continue;

			table[i] = next;
			do {
				entries[next++] = scan.entries[h];
				h = scan.next[h];
			} while (h != 0);
		}
	}

	public long getSourceSize() {
		return src.length;
	}

	public long getIndexSize() {
		sz += sizeOf(src);
		sz += sizeOf(table);
		sz += sizeOf(entries);
		return sz;
	}

	private static long sizeOf(byte[] b) {
		return sizeOfArray(1
	}

	private static long sizeOf(int[] b) {
		return sizeOfArray(4
	}

	private static long sizeOf(long[] b) {
		return sizeOfArray(8
	}

	private static int sizeOfArray(int entSize
	}

	public void encode(OutputStream out
		encode(out
	}

	public boolean encode(OutputStream out
			throws IOException {
		final int end = res.length;
		final DeltaEncoder enc = newEncoder(out

		if (end < BLKSZ || table.length == 0)
			return enc.insert(res);

		int blkPtr = 0;
		int blkEnd = BLKSZ;
		int hash = hashBlock(res

		int resPtr = 0;
		while (blkEnd < end) {
			final int tableIdx = hash & tableMask;
			int entryIdx = table[tableIdx];
			if (entryIdx == 0) {
				hash = step(hash
				continue;
			}

			int bestLen = -1;
			int bestPtr = -1;
			int bestNeg = 0;
			do {
				long ent = entries[entryIdx++];
				if (keyOf(ent) == hash) {
					int neg = 0;
					if (resPtr < blkPtr) {
						neg = blkPtr - resPtr;
						neg = negmatch(res
					}

					int len = neg + fwdmatch(res
					if (bestLen < len) {
						bestLen = len;
						bestPtr = valOf(ent);
						bestNeg = neg;
					}
				} else if ((keyOf(ent) & tableMask) != tableIdx)
					break;
			} while (bestLen < 4096 && entryIdx < entries.length);

			if (bestLen < BLKSZ) {
				hash = step(hash
				continue;
			}

			blkPtr -= bestNeg;

			if (resPtr < blkPtr) {
				int cnt = blkPtr - resPtr;
				if (!enc.insert(res
					return false;
			}

			if (!enc.copy(bestPtr - bestNeg
				return false;

			blkPtr += bestLen;
			resPtr = blkPtr;
			blkEnd = blkPtr + BLKSZ;

			if (end <= blkEnd)
				break;

			hash = hashBlock(res
		}

		if (resPtr < end) {
			int cnt = end - resPtr;
			return enc.insert(res
		}
		return true;
	}

	private DeltaEncoder newEncoder(OutputStream out
			throws IOException {
		return new DeltaEncoder(out
	}

	private static int fwdmatch(byte[] res
		int start = resPtr;
		for (; resPtr < res.length && srcPtr < src.length; resPtr++
			if (res[resPtr] != src[srcPtr])
				break;
		}
		return resPtr - start;
	}

	private static int negmatch(byte[] res
			int limit) {
		if (srcPtr == 0)
			return 0;

		resPtr--;
		srcPtr--;
		int start = resPtr;
		do {
			if (res[resPtr] != src[srcPtr])
				break;
			resPtr--;
			srcPtr--;
		} while (0 <= srcPtr && 0 < --limit);
		return start - resPtr;
	}

	@Override
	@SuppressWarnings("nls")
	public String toString() {
		String[] units = { "bytes"
		long sz = getIndexSize();
		int u = 0;
		while (1024 <= sz && u < units.length - 1) {
			int rem = (int) (sz % 1024);
			sz /= 1024;
			if (rem != 0)
				sz++;
			u++;
		}
		return "DeltaIndex[" + sz + " " + units[u] + "]";
	}

	static int hashBlock(byte[] raw
		int hash;

				| (raw[ptr + 3] & 0xff);
		hash ^= T[hash >>> 31];

		hash = ((hash << 8) | (raw[ptr + 4] & 0xff)) ^ T[hash >>> 23];
		hash = ((hash << 8) | (raw[ptr + 5] & 0xff)) ^ T[hash >>> 23];
		hash = ((hash << 8) | (raw[ptr + 6] & 0xff)) ^ T[hash >>> 23];
		hash = ((hash << 8) | (raw[ptr + 7] & 0xff)) ^ T[hash >>> 23];

		hash = ((hash << 8) | (raw[ptr + 8] & 0xff)) ^ T[hash >>> 23];
		hash = ((hash << 8) | (raw[ptr + 9] & 0xff)) ^ T[hash >>> 23];
		hash = ((hash << 8) | (raw[ptr + 10] & 0xff)) ^ T[hash >>> 23];
		hash = ((hash << 8) | (raw[ptr + 11] & 0xff)) ^ T[hash >>> 23];

		hash = ((hash << 8) | (raw[ptr + 12] & 0xff)) ^ T[hash >>> 23];
		hash = ((hash << 8) | (raw[ptr + 13] & 0xff)) ^ T[hash >>> 23];
		hash = ((hash << 8) | (raw[ptr + 14] & 0xff)) ^ T[hash >>> 23];
		hash = ((hash << 8) | (raw[ptr + 15] & 0xff)) ^ T[hash >>> 23];

		return hash;
	}

	private static int step(int hash
		hash ^= U[toRemove & 0xff];
		return ((hash << 8) | (toAdd & 0xff)) ^ T[hash >>> 23];
	}

	private static int keyOf(long ent) {
		return (int) (ent >>> 32);
	}

	private static int valOf(long ent) {
		return (int) ent;
	}

	private static final int[] T = { 0x00000000
			0xa98d665a
			0x5ca23386
			0xa6359968
			0x10c90156
			0xea5eabb8
			0x1f71fe64
			0xdb05a842
			0x5cd9d7db
			0xfaec4eb3
			0x6241cf4e
			0x98d665a0
			0x43a829bf
			0xb93f8351
			0x31d72f1d
			0x97e2b675
			0x103ec9ec
			0xdb8984a5
			0x2ea6d179
			0xd4317b97
			0x6d0ef8c6
			0x97995228
			0x62b607f4
			0xa9014abd
			0x2edd3524
			0x88e8ac4c
			0x3f0c6dbc
			0xc59bc752
			0x1ee58b4d
			0xe47221a3
			0x7cdfa05e
			0xdaea3936
			0x5d3646af
			0x99421089
			0x6c6d4555
			0x96faefbb
			0x20067785
			0xda91dd6b
			0x2fbe88b7
			0xf44ce84f
			0x739097d6
			0xd5a50ebe
			0x4d088f43
			0xb79f25ad
			0x6ce169b2
			0x9676c35c
			0x011859ce
			0xa72dc0a6
			0x20f1bf3f
			0xeb46f276
			0x1e69a7aa
			0xe4fe0d44

	private static final int[] U = { 0x00000000
			0x374b3b11
			0x42f1fb55
			0x1b2cb666
			0x74a89799
			0x2d75daaa
			0x58cf1aee
			0x644ad12c
			0x181a4e01
			0x0336f867
			0x27a97da4
			0x7e743097
			0x40d554ef
			0x190819dc
			0x72c56757
			0x69e9d131
			0x15b94e1c
			0x5fb0bdfd
			0x2a0a7db9
			0x73d7308a
			0x6adf2956
			0x33026465
			0x46b8a421
			0x0cb157c0
			0x70e1c8ed
			0x6bcd7e8b
			0x73bd86d6
			0x2a60cbe5
			0x14c1af9d
			0x4d1ce2ae
			0x6983676d
			0x72afd10b
			0x0eff4e26
			0x327a85e4
			0x47c045a0
			0x1e1d0893
			0x7199296c
			0x2844645f
			0x5dfea41b
			0x58a5acb2
			0x24f5339f
			0x3fd985f9
			0x1b46003a
			0x429b4d09
			0x7c3a2971
			0x25e76442
			0x77f4d9a2
			0x6cd86fc4
			0x1088f0e9
			0x5a810308
			0x2f3bc34c
			0x76e68e7f
}
