
package org.eclipse.jgit.storage.dht.spi.util;

import java.util.Arrays;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.storage.dht.RowKey;
import org.eclipse.jgit.util.RawParseUtils;

public class ColumnMatcher {
	private final byte[] name;

	public ColumnMatcher(String nameStr) {
		name = Constants.encode(nameStr);
	}

	public byte[] name() {
		return name;
	}

	public boolean sameName(byte[] col) {
		return Arrays.equals(name
	}

	public boolean sameFamily(byte[] col) {
		if (name.length < col.length) {
			for (int i = 0; i < name.length; i++) {
				if (name[i] != col[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public byte[] suffix(byte[] col) {
		byte[] r = new byte[col.length - name.length];
		System.arraycopy(col
		return r;
	}

	public byte[] append(RowKey suffix) {
		return append(suffix.asBytes());
	}

	public byte[] append(byte[] suffix) {
		byte[] r = new byte[name.length + suffix.length];
		System.arraycopy(name
		System.arraycopy(suffix
		return r;
	}

	@Override
	public String toString() {
		return RawParseUtils.decode(name);
	}
}
