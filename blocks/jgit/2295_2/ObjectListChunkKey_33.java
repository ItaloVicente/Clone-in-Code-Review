
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.storage.dht.KeyUtils.format32;
import static org.eclipse.jgit.storage.dht.KeyUtils.parse32;
import static org.eclipse.jgit.util.RawParseUtils.decode;

import java.text.MessageFormat;

import org.eclipse.jgit.lib.ObjectId;

public final class ObjectListChunkKey implements RowKey {
	private static final int KEYLEN = 58;

	public static ObjectListChunkKey fromBytes(byte[] key) {
		if (key.length != KEYLEN)
			throw new IllegalArgumentException(MessageFormat.format(DhtText
					.get().invalidChunkKey

		int repo = parse32(key
		ObjectId id = ObjectId.fromString(key
		int chunk = parse32(key
		return new ObjectListChunkKey(repo
	}

	private final int repo;

	private final ObjectId id;

	private final int chunk;

	ObjectListChunkKey(int repo
		this.repo = repo;
		this.id = id;
		this.chunk = chunk;
	}

	ObjectListChunkKey(RepositoryKey repo
		this.repo = repo.asInt();
		this.id = id;
		this.chunk = chunk;
	}

	int getObjectType() {
		return chunk >>> 29;
	}

	public byte[] toBytes() {
		byte[] r = new byte[KEYLEN];
		format32(r
		r[8] = '.';
		id.copyTo(r
		r[49] = '.';
		format32(r
		return r;
	}

	@Override
	public int hashCode() {
		return chunk;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other instanceof ObjectListChunkKey) {
			ObjectListChunkKey k = (ObjectListChunkKey) other;
			return repo == k.repo && id.equals(k.id) && chunk == k.chunk;
		}
		return false;
	}

	@Override
	public String toString() {
		return "object-list:" + decode(toBytes());
	}
}
