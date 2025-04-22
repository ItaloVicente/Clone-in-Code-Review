
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.storage.dht.KeyUtils.format16;
import static org.eclipse.jgit.storage.dht.KeyUtils.format32;
import static org.eclipse.jgit.storage.dht.KeyUtils.parse16;
import static org.eclipse.jgit.storage.dht.KeyUtils.parse32;
import static org.eclipse.jgit.util.RawParseUtils.decode;

import java.text.MessageFormat;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.SystemReader;

public class ChunkKey implements RowKey {

	private static final long MS_PER_WEEK = 7L * 24 * 3600 * 1000;

	static final int KEYLEN = 59;

	public static ChunkKey create(RepositoryKey repo
		short bucket = bucket(chunk);
		short period = current();
		return new ChunkKey(bucket
	}

	private static short bucket(ObjectId chunk) {
		return (short) ((chunk.getByte(0) << 8) | (chunk.getByte(1)));
	}

	private static short current() {
		long t = SystemReader.getInstance().getCurrentTime() - PERIOD_EPOCH;
		if (t < 0)
			return 0;
		return (short) (t / MS_PER_WEEK);
	}

	public static ChunkKey fromBytes(byte[] key) {
		if (key.length != KEYLEN)
			throw new IllegalArgumentException(MessageFormat.format(
					DhtText.get().invalidChunkKey

		short bucket = parse16(key
		short period = parse16(key
		int repo = parse32(key
		ObjectId chunk = ObjectId.fromString(key
		return new ChunkKey(bucket
	}

	static ChunkKey fromChunk(ChunkKey container
		int repo = container.repo;
		short period = (short) NB.decodeUInt16(raw
		ObjectId chunk = ObjectId.fromRaw(raw
		short bucket = bucket(chunk);
		return new ChunkKey(bucket
	}

	private final short bucket;

	private final short period;

	private final int repo;

	private final ObjectId chunk;

	ChunkKey(short bucket
		this.bucket = bucket;
		this.period = period;
		this.repo = repo;
		this.chunk = chunk;
	}

	boolean isFor(RepositoryKey other) {
		return this.repo == other.asInt();
	}

	ObjectId getChunkHash() {
		return chunk;
	}

	void toChunk(byte[] dst
		NB.encodeInt16(dst
		chunk.copyRawTo(dst
	}

	public byte[] asByteArray() {
		byte[] r = new byte[KEYLEN];
		format16(r
		r[4] = '.';
		format16(r
		r[9] = '.';
		format32(r
		r[18] = '.';
		chunk.copyTo(r
		return r;
	}

	long getApproximateCreationTime() {
		return PERIOD_EPOCH + (period * MS_PER_WEEK);
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
			return thisChunk.bucket == otherChunk.bucket
					&& thisChunk.period == otherChunk.period
					&& thisChunk.repo == otherChunk.repo
					&& thisChunk.chunk.equals(otherChunk.chunk);
		}
		return false;
	}

	@Override
	public String toString() {
		return "chunk:" + decode(asByteArray());
	}
}
