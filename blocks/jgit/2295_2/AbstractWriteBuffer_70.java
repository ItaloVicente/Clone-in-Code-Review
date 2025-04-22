
package org.eclipse.jgit.storage.dht.spi.cache;

import java.util.Arrays;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.storage.dht.RowKey;
import org.eclipse.jgit.storage.dht.spi.ChunkTable;
import org.eclipse.jgit.storage.dht.spi.ObjectIndexTable;
import org.eclipse.jgit.storage.dht.spi.ObjectListTable;
import org.eclipse.jgit.storage.dht.spi.RepositoryIndexTable;
import org.eclipse.jgit.util.RawParseUtils;

public class Namespace {
	public static final Namespace CHUNK = create("chunk");

	public static final Namespace OBJECT_INDEX = create("objectIndex");

	public static final Namespace REPOSITORY_INDEX = create("repositoryIndex");

	public static final Namespace OBJECT_LIST = create("objectList");

	public static Namespace create(String name) {
		return new Namespace(Constants.encode(name));
	}

	public static Namespace create(byte[] name) {
		return new Namespace(name);
	}

	private final byte[] name;

	private volatile int hashCode;

	private Namespace(byte[] name) {
		this.name = name;
	}

	public byte[] getBytes() {
		return name;
	}

	public CacheKey key(byte[] key) {
		return new CacheKey(this
	}

	public CacheKey key(RowKey key) {
		return new CacheKey(this
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			int h = 5381;
			for (int ptr = 0; ptr < name.length; ptr++)
				h = ((h << 5) + h) + (name[ptr] & 0xff);
			if (h == 0)
				h = 1;
			hashCode = h;
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (other instanceof Namespace)
			return Arrays.equals(name
		return false;
	}

	@Override
	public String toString() {
		return RawParseUtils.decode(name);
	}
}
