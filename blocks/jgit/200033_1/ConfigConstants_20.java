package org.eclipse.jgit.internal.storage.file;

final class UInt24Array {

	public static final UInt24Array EMPTY = new UInt24Array(
			new byte[0]);

	private static final int ENTRY_SZ = 3;

	private final byte[] data;

	private final int size;

	UInt24Array(byte[] data) {
		this.data = data;
		this.size = data.length / ENTRY_SZ;
	}

	boolean isEmpty() {
		return size == 0;
	}

	int size() {
		return size;
	}

	int get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		int offset = index * ENTRY_SZ;
		int e = data[offset] & 0xff;
		e <<= 8;
		e |= data[offset + 1] & 0xff;
		e <<= 8;
		e |= data[offset + 2] & 0xff;
		return e;
	}

	int binarySearch(int needle) {
		if ((needle & 0xff000000) != 0) {
		}
		if (size == 0) {
			return -1;
		}
		int high = size;
		if (high == 0)
			return -1;
		int low = 0;
		do {
			int mid = (low + high) >>> 1;
			int cmp;
			cmp = Integer.compare(needle
			if (cmp < 0)
				high = mid;
			else if (cmp == 0) {
				return mid;
			} else
				low = mid + 1;
		} while (low < high);
		return -1;
	}

	int getLastValue() {
		return get(size - 1);
	}
}
