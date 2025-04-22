
package org.eclipse.jgit.storage.dht.spi.memory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.storage.dht.spi.util.ColumnMatcher;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.SystemReader;

public class MTable {
	private final Map<Key

	private final Object lock;

	public MTable() {
		map = new HashMap<Key
		lock = new Object();
	}

	public void put(byte[] row
		synchronized (lock) {
			Key rowKey = new Key(row);
			Map<Key
			if (r == null) {
				r = new HashMap<Key
				map.put(rowKey
			}
			r.put(new Key(col)
		}
	}

	public void deleteRow(byte[] row) {
		synchronized (lock) {
			map.remove(new Key(row));
		}
	}

	public void delete(byte[] row
		synchronized (lock) {
			Key rowKey = new Key(row);
			Map<Key
			if (r == null)
				return;

			r.remove(new Key(col));
			if (r.isEmpty())
				map.remove(rowKey);
		}
	}

	public boolean compareAndSet(byte[] row
			byte[] newVal) {
		synchronized (lock) {
			Key rowKey = new Key(row);
			Key colKey = new Key(col);

			Map<Key
			if (r == null) {
				r = new HashMap<Key
				map.put(rowKey
			}

			Cell oldCell = r.get(colKey);
			if (!same(oldCell
				if (r.isEmpty())
					map.remove(rowKey);
				return false;
			}

			if (newVal != null) {
				r.put(colKey
				return true;
			}

			r.remove(colKey);
			if (r.isEmpty())
				map.remove(rowKey);
			return true;
		}
	}

	private static boolean same(Cell oldCell
		if (oldCell == null)
			return expVal == null;

		if (expVal == null)
			return false;

		return Arrays.equals(oldCell.value
	}

	public Cell get(byte[] row
		synchronized (lock) {
			Map<Key
			return r != null ? r.get(new Key(col)) : null;
		}
	}

	public Iterable<Cell> scanFamily(byte[] row
		synchronized (lock) {
			Map<Key
			if (r == null)
				return Collections.emptyList();

			if (family == null)
				return new ArrayList<Cell>(r.values());

			ArrayList<Cell> out = new ArrayList<Cell>(4);
			for (Cell cell : r.values()) {
				if (family.sameFamily(cell.getName()))
					out.add(cell);
			}
			return out;
		}
	}

	private static class Key {
		final byte[] key;

		Key(byte[] key) {
			this.key = key;
		}

		@Override
		public int hashCode() {
			int hash = 5381;
			for (int ptr = 0; ptr < key.length; ptr++)
				hash = ((hash << 5) + hash) + (key[ptr] & 0xff);
			return hash;
		}

		@Override
		public boolean equals(Object other) {
			if (this == other)
				return true;
			if (other instanceof Key)
				return Arrays.equals(key
			return false;
		}

		@Override
		public String toString() {
			return RawParseUtils.decode(key);
		}
	}

	public static class Cell {
		final byte[] row;

		final byte[] name;

		final byte[] value;

		final long timestamp;

		Cell(byte[] row
			this.row = row;
			this.name = name;
			this.value = value;
			this.timestamp = SystemReader.getInstance().getCurrentTime();
		}

		public byte[] getRow() {
			return row;
		}

		public byte[] getName() {
			return name;
		}

		public byte[] getValue() {
			return value;
		}

		public long getTimestamp() {
			return timestamp;
		}

		@Override
		public String toString() {
			return RawParseUtils.decode(name);
		}
	}
}
