package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.eclipse.jgit.util.NB;

class PackObjectSizeIndexV1 implements PackObjectSizeIndex {

	private final int[] positions;

	private final int[] sizes32;

	private final long[] sizes64;

	PackObjectSizeIndexV1(InputStream in) throws IOException {
		int c = readInt(in);
		positions = readIntArray(in
		sizes32 = readIntArray(in
		int c64sizes = readInt(in);
		sizes64 = readLongArray(in
		if (c64sizes > 0) {
			readInt(in);
		}
	}

	private int readInt(InputStream in) throws IOException {
		return NB.decodeInt32(in.readNBytes(4)
	}

	private int[] readIntArray(InputStream in
			throws IOException {
		if (intsCount == 0) {
			return new int[0];
		}
		byte[] data = in.readNBytes(intsCount*4);
		int[] dest = new int[intsCount];
		for (int i = 0; i < intsCount; i++) {
			dest[i] = NB.decodeInt32(data
		}
		return dest;
	}

	private long[] readLongArray(InputStream in
			throws IOException {
		if (longsCount == 0) {
			return new long[0];
		}
		byte[] data = in.readNBytes(longsCount*8);
		long[] dest = new long[longsCount];
		for (int i = 0; i < longsCount; i++) {
			dest[i] = NB.decodeInt64(data
		}
		return dest;
	}

	@Override
	public long getSize(long position) {
		int pos = Arrays.binarySearch(positions
		if (pos < 0) {
			return -1;
		}

		int objSize = sizes32[pos];
		if (objSize < 0) {
			int secondPos = Math.abs(objSize) - 1;
			return sizes64[secondPos];
		}
		return objSize;
	}

	@Override
	public long getObjectCount() {
		return positions.length;
	}

}
