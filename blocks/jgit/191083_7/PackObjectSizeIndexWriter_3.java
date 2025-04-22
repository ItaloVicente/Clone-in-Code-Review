package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.eclipse.jgit.util.NB;

class PackObjectSizeIndexV1 implements PackObjectSizeIndex {

	private final int[] offsets32;

	private final long[] offsets64;

	private final int[] sizes32;

	private final long[] sizes64;

	PackObjectSizeIndexV1(InputStream in) throws IOException {
		int c32 = readInt(in);
		int c64 = readInt(in);
		offsets32 = readIntArray(in
		offsets64 = readLongArray(in
		sizes32 = readIntArray(in
		int c64sizes = readInt(in);
		if (c64sizes > 0) {
			sizes64 = readLongArray(in
			readInt(in);
		} else {
			sizes64 = new long[0];
		}
	}

	private int readInt(InputStream in) throws IOException {
		return NB.decodeInt32(in.readNBytes(4)
	}

	private int[] readIntArray(InputStream in
			throws IOException {
		byte[] data = in.readNBytes(intsCount*4);
		int[] dest = new int[intsCount];
		for (int i = 0; i < intsCount; i++) {
			dest[i] = NB.decodeInt32(data
		}
		return dest;
	}

	private long[] readLongArray(InputStream in
			throws IOException {
		byte[] data = in.readNBytes(longsCount*8);
		long[] dest = new long[longsCount];
		for (int i = 0; i < longsCount; i++) {
			dest[i] = NB.decodeInt64(data
		}
		return dest;
	}

	@Override
	public long getSize(long offset) {
		int pos;
		if (offset < Integer.MAX_VALUE) {
			pos = Arrays.binarySearch(offsets32
		} else {
			pos = Arrays.binarySearch(offsets64
		}

		if (pos < 0) {
			return -1;
		}

		int sizePos = offset < Integer.MAX_VALUE ? pos : pos + offsets32.length;
		int objSize = sizes32[sizePos];
		if (objSize < 0) {
			int secondPos = Math.abs(objSize) - 1;
			return sizes64[secondPos];
		}
		return objSize;
	}

	@Override
	public long getObjectCount() {
		return offsets32.length + offsets64.length;
	}

}
