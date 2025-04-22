
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.storage.dht.KeyUtils.format32;
import static org.eclipse.jgit.storage.dht.KeyUtils.parse32;
import static org.eclipse.jgit.util.RawParseUtils.decode;

import java.text.MessageFormat;

import org.eclipse.jgit.lib.ObjectId;

public final class ChunkKey implements RowKey {
	static final int KEYLEN = 52;

	public static ChunkKey create(RepositoryKey repo
		return new ChunkKey(repo.asInt()
	}

	public static ChunkKey fromBytes(byte[] key) {
		if (key.length != KEYLEN)
			throw new IllegalArgumentException(MessageFormat.format(
					DhtText.get().invalidChunkKey

		int repo = parse32(key
		ObjectId chunk = ObjectId.fromString(key
		return new ChunkKey(repo
	}

	public static ChunkKey fromString(String key) {
		int d1 = key.indexOf('.');
		int d2 = key.indexOf('.'
		int repo = Integer.parseInt(key.substring(d1 + 1
		ObjectId chunk = ObjectId.fromString(key.substring(d2 + 1));
		return new ChunkKey(repo
	}

	private final int repo;

	private final ObjectId chunk;

	ChunkKey(int repo
		this.repo = repo;
		this.chunk = chunk;
	}

	RepositoryKey getRepositoryKey() {
		return RepositoryKey.fromInt(repo);
	}

	int getRepositoryId() {
		return repo;
	}

	ObjectId getChunkHash() {
		return chunk;
	}

	public byte[] asBytes() {
		byte[] r = new byte[KEYLEN];
		chunk.copyTo(r
		format32(r
		r[11] = '.';
		r[2] = '.';
		r[1] = r[12 + 1];
		r[0] = r[12 + 0];
		return r;
	}

	public String asString() {
		return decode(asBytes());
	}

	@Override
	public int hashCode() {
		return chunk.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other instanceof ChunkKey) {
			ChunkKey thisChunk = this;
			ChunkKey otherChunk = (ChunkKey) other;
			return thisChunk.repo == otherChunk.repo
					&& thisChunk.chunk.equals(otherChunk.chunk);
		}
		return false;
	}

	@Override
	public String toString() {
		return "chunk:" + asString();
	}
}
