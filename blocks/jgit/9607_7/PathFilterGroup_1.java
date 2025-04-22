
package org.eclipse.jgit.treewalk.filter;

import org.eclipse.jgit.util.RawParseUtils;

class ByteArraySet {

	private int size;

	private int grow;

	private int mask;

	private byte[][] table;

	public ByteArraySet(int capacity) {
		initTable(1 << Integer.highestOneBit((capacity * 2) - 1));
	}

	public byte[] get(final byte[] toFind
		final int msk = mask;
		int i = hash & msk;
		final byte[][] tbl = table;
		byte[] obj;

		while ((obj = tbl[i]) != null) {
			if (equals(obj
				return obj;
			i = (i + 1) & msk;
		}
		return null;
	}

	private static boolean equals(byte[] a
		if (a.length < length || b.length < length)
			return false;
		for (int i = 0; i < length; ++i) {
			if (a[i] != b[i])
				return false;
		}
		return true;
	}

	public boolean contains(final byte[] toFind
		return get(toFind
	}

	public byte[] addIfAbsent(final byte[] newValue
		final int msk = mask;
		int i = hash & msk;
		final byte[][] tbl = table;
		byte[] obj;

		while ((obj = tbl[i]) != null) {
			if (equals(obj
				return obj;
			i = (i + 1) & msk;
		}

		byte[] valueToInsert = copyIfNotSameSize(newValue
		if (++size == grow) {
			grow();
			insert(valueToInsert
		} else {
			tbl[i] = valueToInsert;
		}
		return valueToInsert;
	}

	private static byte[] copyIfNotSameSize(byte[] newValue
		if (newValue.length == length)
			return newValue;
		byte[] ret = new byte[length];
		System.arraycopy(newValue
		return ret;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void insert(final byte[] newValue
		final int msk = mask;
		int j = hash & msk;
		final byte[][] tbl = table;
		while (tbl[j] != null)
			j = (j + 1) & msk;
		tbl[j] = newValue;
	}

	private Hasher hasher = new Hasher(null

	private void grow() {
		final byte[][] oldTable = table;
		final int oldSize = table.length;

		initTable(oldSize << 1);
		for (int i = 0; i < oldSize; i++) {
			final byte[] obj = oldTable[i];
			if (obj != null) {
				hasher.init(obj
				insert(obj
			}
		}
	}

	private void initTable(int sz) {
		if (sz < 2)
			sz = 2;
		grow = sz >> 1;
		mask = sz - 1;
		table = new byte[sz][];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (byte[] b : table) {
			if (b == null)
				continue;
			if (sb.length() > 1)
				sb.append(" 
			sb.append('"');
			sb.append(RawParseUtils.decode(b));
			sb.append('"');
			sb.append('(');
			sb.append(chainlength(b));
			sb.append(')');
		}
		sb.append(']');
		return sb.toString();
	}

	private int chainlength(byte[] b) {
		Hasher h = new Hasher(b
		int hash = h.hash();
		final int msk = mask;
		int i = hash & msk;
		final byte[][] tbl = table;
		byte[] obj;

		int n = 0;
		while ((obj = tbl[i]) != null) {
			if (equals(obj
				return n;
			i = (i + 1) & msk;
			++n;
		}
		return -1;
	}

	static class Hasher {
		private int hash;

		private int pos;

		private byte[] data;

		private int length;

		Hasher(byte[] data
			init(data
		}

		void init(byte[] d
			this.data = d;
			this.length = l;
			pos = 0;
			hash = 0;
		}

		int hash() {
			while (pos < length)
				hash = hash * 31 + data[pos++];
			return hash;
		}

		int nextHash() {
			for (;;) {
				hash = hash * 31 + data[pos];
				++pos;
				if (pos == length || data[pos] == '/')
					return hash;
			}
		}

		int getHash() {
			return hash;
		}

		boolean hasNext() {
			return pos < length;
		}

		public int length() {
			return pos;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < pos; ++i) {
				sb.append((char) data[i]);
			}
			for (int i = pos; i < length; ++i)
				sb.append((char) data[i]);
			return sb.toString();
		}
	}

	byte[][] toArray() {
		byte[][] ret = new byte[size][];
		int i = 0;
		for (byte[] entry : table) {
			if (entry != null)
				ret[i++] = entry;
		}
		return ret;
	}

}
