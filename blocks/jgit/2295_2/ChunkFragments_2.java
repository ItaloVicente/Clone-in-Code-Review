
package org.eclipse.jgit.storage.dht;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.NB;

class ChunkFormatter {
	static final int TRAILER_SIZE = 8;

	private final RepositoryKey repo;

	private final DhtInserterOptions options;

	private final byte[] varIntBuf;

	private final ChunkInfo info;

	private final int maxObjects;

	private List<StoredObject> objectList;

	private byte[] chunkData;

	private int ptr;

	private int mark;

	private int currentObjectType;

	private PackChunk.Members builder;

	ChunkFormatter(RepositoryKey repo
		this.repo = repo;
		this.options = options;
		this.varIntBuf = new byte[32];
		this.info = new ChunkInfo();
		this.chunkData = new byte[options.getChunkSize()];
		this.maxObjects = options.getMaxObjectCount();
	}

	void setSource(ChunkInfo.Source src) {
		info.source = src;
	}

	void setObjectType(int type) {
		info.objectType = type;
	}

	void setFragment() {
		info.fragment = true;
	}

	ChunkKey getChunkKey() {
		return getChunkInfo().getChunkKey();
	}

	ChunkInfo getChunkInfo() {
		return info;
	}

	PackChunk getPackChunk() throws DhtException {
		return builder.build();
	}

	ChunkKey end(MessageDigest md) {
		if (md == null)
			md = Constants.newMessageDigest();

		NB.encodeInt32(chunkData
		ptr += 4;

		CRC32 crc = new CRC32();
		crc.update(chunkData
		NB.encodeInt32(chunkData
		ptr += 4;

		md.update(chunkData
		chunkData = resize(chunkData

		info.chunkKey = ChunkKey.create(repo
		info.chunkSize = chunkData.length;

		builder = new PackChunk.Members();
		builder.setChunkKey(info.chunkKey);
		builder.setChunkData(chunkData);

		if (objectList != null && !objectList.isEmpty()) {
			byte[] index = ChunkIndex.create(objectList);
			builder.setChunkIndex(index);
			info.indexSize = index.length;
		}

		return getChunkKey();
	}

	void safePut(Database db
		WriteBuffer chunkBuf = db.newWriteBuffer();

		db.repository().put(repo
		chunkBuf.flush();

		db.chunk().put(builder
		chunkBuf.flush();

		linkObjects(db
	}

	void unsafePut(Database db
		db.repository().put(repo
		db.chunk().put(builder
		linkObjects(db
	}

	private void linkObjects(Database db
			throws DhtException {
		if (objectList != null && !objectList.isEmpty()) {
			for (StoredObject obj : objectList) {
				db.objectIndex().add(ObjectIndexKey.create(repo
						obj.link(getChunkKey())
			}
		}
	}

	boolean whole(Deflater def
			ObjectId objId) {
		if (free() < 10 || maxObjects <= info.objectsTotal)
			return false;

		header(type
		info.objectsWhole++;
		currentObjectType = type;

		int endOfHeader = ptr;
		def.setInput(data
		def.finish();
		do {
			int left = free();
			if (left == 0) {
				rollback();
				return false;
			}

			int n = def.deflate(chunkData
			if (n == 0) {
				rollback();
				return false;
			}

			ptr += n;
		} while (!def.finished());

		if (objectList == null)
			objectList = new ArrayList<StoredObject>();
		objectList.add(new StoredObject(objId

		if (info.objectType < 0)
			info.objectType = type;
		else if (info.objectType != type)
			info.objectType = ChunkInfo.OBJ_MIXED;

		return true;
	}

	boolean whole(int type
		if (free() < 10 || maxObjects <= info.objectsTotal)
			return false;

		header(type
		info.objectsWhole++;
		currentObjectType = type;
		return true;
	}

	boolean ofsDelta(long len
		int n = encodeVarInt(offsetDiff);
		if (free() < 10 + (varIntBuf.length - n)
				|| maxObjects <= info.objectsTotal)
			return false;

		header(Constants.OBJ_OFS_DELTA
		info.objectsOfsDelta++;
		currentObjectType = Constants.OBJ_OFS_DELTA;

		if (append(varIntBuf
			return true;

		rollback();
		return false;
	}

	boolean refDelta(long len
		if (free() < 30 || maxObjects <= info.objectsTotal)
			return false;

		header(Constants.OBJ_REF_DELTA
		info.objectsRefDelta++;
		currentObjectType = Constants.OBJ_REF_DELTA;

		baseId.copyRawTo(chunkData
		ptr += 20;
		return true;
	}

	boolean chunkDelta(long len
		int n = encodeVarInt(offset);
		if (free() < 10 + 1 + 22 + n || maxObjects <= info.objectsTotal)
			return false;

		header(Constants.OBJ_EXT
		chunkData[ptr++] = PackChunk.OBJ_CHUNK_DELTA;

		info.objectsChunkDelta++;
		currentObjectType = PackChunk.OBJ_CHUNK_DELTA;

		key.toChunk(chunkData
		ptr += 22;
		if (append(varIntBuf
			return true;

		rollback();
		return false;
	}

	void appendDeflateOutput(Deflater def) {
		while (!def.finished()) {
			int left = free();
			if (left == 0)
				return;
			int n = def.deflate(chunkData
			if (n == 0)
				return;
			ptr += n;
		}
	}

	boolean append(byte[] data
		if (free() < len)
			return false;

		System.arraycopy(data
		ptr += len;
		return true;
	}

	boolean isEmpty() {
		return ptr == 0;
	}

	int getObjectCount() {
		return info.objectsTotal;
	}

	int position() {
		return ptr;
	}

	int size() {
		return ptr;
	}

	int free() {
		return (chunkData.length - TRAILER_SIZE) - ptr;
	}

	byte[] getRawChunkDataArray() {
		return chunkData;
	}

	int getCurrentObjectType() {
		return currentObjectType;
	}

	void rollback() {
		ptr = mark;
		adjustObjectCount(-1
	}

	void adjustObjectCount(int delta
		info.objectsTotal += delta;

		switch (type) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			info.objectsWhole += delta;
			break;

		case Constants.OBJ_OFS_DELTA:
			info.objectsOfsDelta += delta;
			break;

		case Constants.OBJ_REF_DELTA:
			info.objectsRefDelta += delta;
			break;

		case PackChunk.OBJ_CHUNK_DELTA:
			info.objectsChunkDelta += delta;
			break;
		}
	}

	private void header(int type
		mark = ptr;
		info.objectsTotal++;

		long nextLength = rawLength >>> 4;
		chunkData[ptr++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (type << 4) | (rawLength & 0x0F));
		rawLength = nextLength;
		while (rawLength > 0) {
			nextLength >>>= 7;
			chunkData[ptr++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (rawLength & 0x7F));
			rawLength = nextLength;
		}
	}

	private int encodeVarInt(int value) {
		int n = varIntBuf.length - 1;
		varIntBuf[n] = (byte) (value & 0x7F);
		while ((value >>= 7) > 0)
			varIntBuf[--n] = (byte) (0x80 | (--value & 0x7F));
		return n;
	}

	private static byte[] resize(byte[] src
		if (src.length == len)
			return src;
		byte[] dst = new byte[len];
		System.arraycopy(src
		return dst;
	}

	private static class StoredObject extends PackedObjectInfo {
		private final int size;

		StoredObject(AnyObjectId id
			super(id);
			setOffset(offset);
			this.size = size;
		}

		ObjectInfo link(ChunkKey key) {
			return new ObjectInfo(key
		}
	}
}
