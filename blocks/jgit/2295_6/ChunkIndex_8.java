
package org.eclipse.jgit.storage.dht;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.dht.ChunkMeta.BaseChunk;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.NB;

class ChunkFormatter {
	static final int TRAILER_SIZE = 4;

	private final RepositoryKey repo;

	private final DhtInserterOptions options;

	private final byte[] varIntBuf;

	private final ChunkInfo info;

	private final int maxObjects;

	private Map<ChunkKey

	private List<StoredObject> objectList;

	private byte[] chunkData;

	private int ptr;

	private int mark;

	private int currentObjectType;

	private BaseChunkInfo currentObjectBase;

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

	ChunkMeta getChunkMeta() {
		return builder.getMeta();
	}

	PackChunk getPackChunk() throws DhtException {
		return builder.build();
	}

	ChunkKey end(MessageDigest md) {
		if (md == null)
			md = Constants.newMessageDigest();

		chunkData = cloneArray(chunkData
		NB.encodeInt32(chunkData
		ptr += 4;

		md.update(chunkData
		info.chunkKey = ChunkKey.create(repo
		info.chunkSize = chunkData.length;

		builder = new PackChunk.Members();
		builder.setChunkKey(info.chunkKey);
		builder.setChunkData(chunkData);

		ChunkMeta meta = new ChunkMeta(info.chunkKey);
		if (baseChunks != null) {
			meta.baseChunks = new ArrayList<BaseChunk>(baseChunks.size());
			for (BaseChunkInfo b : baseChunks.values()) {
				if (0 < b.useCount)
					meta.baseChunks.add(new BaseChunk(b.relativeStart
			}
			Collections.sort(meta.baseChunks
				public int compare(BaseChunk a
					return Long.signum(a.relativeStart - b.relativeStart);
				}
			});
		}
		if (!meta.isEmpty()) {
			builder.setMeta(meta);
			info.metaSize = meta.toBytes().length;
		}

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

		final int packedSize = ptr - endOfHeader;
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

	boolean ofsDelta(long inflatedSize
		final int ofsPtr = encodeVarInt(negativeOffset);
		final int ofsLen = varIntBuf.length - ofsPtr;
		if (free() < 10 + ofsLen || maxObjects <= info.objectsTotal)
			return false;

		header(Constants.OBJ_OFS_DELTA
		info.objectsOfsDelta++;
		currentObjectType = Constants.OBJ_OFS_DELTA;
		currentObjectBase = null;

		if (append(varIntBuf
			return true;

		rollback();
		return false;
	}

	boolean refDelta(long inflatedSize
		if (free() < 30 || maxObjects <= info.objectsTotal)
			return false;

		header(Constants.OBJ_REF_DELTA
		info.objectsRefDelta++;
		currentObjectType = Constants.OBJ_REF_DELTA;

		baseId.copyRawTo(chunkData
		ptr += 20;
		return true;
	}

	void useBaseChunk(long relativeStart
		if (baseChunks == null)
			baseChunks = new HashMap<ChunkKey

		BaseChunkInfo base = baseChunks.get(baseChunkKey);
		if (base == null) {
			base = new BaseChunkInfo(relativeStart
			baseChunks.put(baseChunkKey
		}
		base.useCount++;
		currentObjectBase = base;
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
			if (currentObjectBase != null && --currentObjectBase.useCount == 0)
				baseChunks.remove(currentObjectBase.key);
			currentObjectBase = null;
			break;

		case Constants.OBJ_REF_DELTA:
			info.objectsRefDelta += delta;
			break;
		}
	}

	private void header(int type
		mark = ptr;
		info.objectsTotal++;

		long nextLength = inflatedSize >>> 4;
		chunkData[ptr++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (type << 4) | (inflatedSize & 0x0F));
		inflatedSize = nextLength;
		while (inflatedSize > 0) {
			nextLength >>>= 7;
			chunkData[ptr++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (inflatedSize & 0x7F));
			inflatedSize = nextLength;
		}
	}

	private int encodeVarInt(long value) {
		int n = varIntBuf.length - 1;
		varIntBuf[n] = (byte) (value & 0x7F);
		while ((value >>= 7) > 0)
			varIntBuf[--n] = (byte) (0x80 | (--value & 0x7F));
		return n;
	}

	private static byte[] cloneArray(byte[] src
		byte[] dst = new byte[len];
		System.arraycopy(src
		return dst;
	}

	private static class BaseChunkInfo {
		final long relativeStart;

		final ChunkKey key;

		int useCount;

		BaseChunkInfo(long relativeStart
			this.relativeStart = relativeStart;
			this.key = key;
		}
	}

	private static class StoredObject extends PackedObjectInfo {
		private final int type;

		private final int packed;

		private final int inflated;

		StoredObject(AnyObjectId id
			super(id);
			setOffset(offset);
			this.type = type;
			this.packed = packed;
			this.inflated = size;
		}

		ObjectInfo link(ChunkKey key) {
			final int ptr = (int) getOffset();
			return new ObjectInfo(key
		}
	}
}
