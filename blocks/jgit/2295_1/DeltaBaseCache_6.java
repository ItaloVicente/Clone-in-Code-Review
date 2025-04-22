
package org.eclipse.jgit.storage.dht;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.dht.spi.ObjectIndexTable;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.transport.PackedObjectInfo;

class ChunkWriter {
	private final RepositoryKey repo;

	private final Database db;

	private final WriteBuffer buffer;

	private final byte[] hdrBuf;

	private final byte[] chunk;

	private int ptr;

	private List<StoredObject> objects;

	private int mark;

	private int objectCount;

	ChunkWriter(RepositoryKey repo
		this.repo = repo;
		this.db = db;
		this.buffer = buffer;

		hdrBuf = new byte[32];
		chunk = new byte[size];
		objects = new ArrayList<StoredObject>(16);
	}

	void flush(MessageDigest md) throws DhtException {
		if (!isEmpty())
			return;

		ChunkKey key = newKey(md);
		db.chunks().putData(key
		db.chunks().putIndex(key

		ObjectIndexTable idx = db.objectIndex();
		for (StoredObject o : objects)
			idx.add(ObjectIndexKey.create(repo
		clear();
	}

	ChunkKey putData(MessageDigest md
			throws DhtException {
		if (isEmpty())
			return null;

		ChunkKey key = newKey(md);
		byte[] data = copy(chunk
		if (cache != null) {
			PackChunk.Builder b = new PackChunk.Builder();
			b.setChunkKey(key);
			b.setChunkData(data);
			cache.put(key
		}
		db.chunks().putData(key
		clear();
		return key;
	}

	void clear() {
		ptr = 0;
		objectCount = 0;
		objects.clear();
	}

	boolean whole(Deflater def
			ObjectId objId) {
		if (free() < 10)
			return false;

		header(type
		int endOfHeader = ptr;
		def.setInput(data
		def.finish();
		do {
			int left = free();
			if (left == 0) {
				rollback();
				return false;
			}

			int n = def.deflate(chunk
			if (n == 0) {
				rollback();
				return false;
			}

			ptr += n;
		} while (!def.finished());

		objects.add(new StoredObject(objId
		objectCount++;
		return true;
	}

	void deflate(Deflater def) {
		while (!def.finished()) {
			int left = free();
			if (left == 0)
				return;
			int n = def.deflate(chunk
			if (n == 0)
				return;
			ptr += n;
		}
	}

	boolean whole(int type
		if (free() < 10)
			return false;

		header(type
		return true;
	}

	boolean ofsDelta(long len
		int n = encodeVarInt(offsetDiff);
		if (free() < 10 + (hdrBuf.length - n))
			return false;

		header(Constants.OBJ_OFS_DELTA
		if (append(hdrBuf
			return true;

		rollback();
		return false;
	}

	private int encodeVarInt(int value) {
		int n = hdrBuf.length - 1;
		hdrBuf[n] = (byte) (value & 0x7F);
		while ((value >>= 7) > 0)
			hdrBuf[--n] = (byte) (0x80 | (--value & 0x7F));
		return n;
	}

	boolean refDelta(long len
		if (free() < 30)
			return false;

		header(Constants.OBJ_REF_DELTA
		baseId.copyRawTo(chunk
		ptr += 20;
		return true;
	}

	boolean chunkDelta(long len
		int n = encodeVarInt(offset);
		if (free() < 10 + 1 + 22 + n)
			return false;

		header(Constants.OBJ_EXT
		chunk[ptr++] = PackChunk.OBJ_CHUNK_DELTA;

		key.toChunk(chunk
		ptr += 22;
		if (append(hdrBuf
			return true;

		rollback();
		return false;
	}

	boolean append(byte[] data
		if (free() < len)
			return false;

		System.arraycopy(data
		ptr += len;
		return true;
	}

	boolean append(ChunkWriter w
		if (free() < len)
			return false;

		System.arraycopy(w.chunk
		ptr += len;
		return true;
	}

	boolean isEmpty() {
		return ptr == 0;
	}

	int getObjectCount() {
		return objectCount;
	}

	int position() {
		return ptr;
	}

	void rollback() {
		ptr = mark;
		objectCount--;
	}

	int size() {
		return ptr;
	}

	int free() {
		return chunk.length - ptr;
	}

	private void header(int type
		mark = ptr;
		objectCount++;

		long nextLength = rawLength >>> 4;
		chunk[ptr++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (type << 4) | (rawLength & 0x0F));
		rawLength = nextLength;
		while (rawLength > 0) {
			nextLength >>>= 7;
			chunk[ptr++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (rawLength & 0x7F));
			rawLength = nextLength;
		}
	}

	private static byte[] copy(byte[] src
		byte[] dst = new byte[len];
		System.arraycopy(src
		return dst;
	}

	private ChunkKey newKey(MessageDigest md) {
		md.update(chunk
		return ChunkKey.create(repo
	}

	private static class StoredObject extends PackedObjectInfo {
		private final int size;

		StoredObject(AnyObjectId id
			super(id);
			setOffset(offset);
			this.size = size;
		}

		ChunkLink link(ChunkKey key) {
			return new ChunkLink(key
		}
	}
}
