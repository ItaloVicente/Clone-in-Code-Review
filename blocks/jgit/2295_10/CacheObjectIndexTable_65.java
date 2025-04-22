
package org.eclipse.jgit.storage.dht.spi.cache;

import java.util.Arrays;

import org.eclipse.jgit.storage.dht.RowKey;
import org.eclipse.jgit.util.RawParseUtils;

public class CacheKey {
	private final Namespace ns;

	private final byte[] key;

	private volatile int hashCode;

	public CacheKey(Namespace ns
		this(ns
	}

	public CacheKey(Namespace ns
		this.ns = ns;
		this.key = key;
	}

	public Namespace getNamespace() {
		return ns;
	}

	public byte[] getBytes() {
		return key;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			int h = 5381;
			for (int ptr = 0; ptr < key.length; ptr++)
				h = ((h << 5) + h) + (key[ptr] & 0xff);
			if (h == 0)
				h = 1;
			hashCode = h;
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other instanceof CacheKey) {
			CacheKey m = (CacheKey) other;
			return ns.equals(m.ns) && Arrays.equals(key
		}
		return false;
	}

	@Override
	public String toString() {
		return ns + ":" + RawParseUtils.decode(key);
	}
}
