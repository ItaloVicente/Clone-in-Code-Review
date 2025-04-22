
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;

import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.NB;

public class ObjectListChunk {
	public static ObjectListChunk fromBytes(ObjectListChunkKey key
		return new ObjectListChunk(key
	}

	static ObjectListChunk create(ObjectListChunkKey key
			int[] hashes
		byte[] raw;
		switch (key.getObjectType()) {
		case OBJ_COMMIT:
			raw = new byte[cnt * OBJECT_ID_LENGTH];
			for (int nth = 0; nth < cnt; nth++)
				list[nth].copyRawTo(raw
			break;
		default:
			raw = new byte[cnt * (OBJECT_ID_LENGTH + 4)];
			for (int nth = 0; nth < cnt; nth++) {
				int ptr = nth * (OBJECT_ID_LENGTH + 4);
				list[nth].copyRawTo(raw
				NB.encodeInt32(raw
			}
			break;
		}
		return new ObjectListChunk(key
	}

	static int getRecordLength(int objectType) {
		switch (objectType) {
		case OBJ_COMMIT:
			return OBJECT_ID_LENGTH;
		default:
			return OBJECT_ID_LENGTH + 4;
		}
	}

	private final ObjectListChunkKey key;

	private final byte[] raw;

	private final int recLen;

	private final int size;

	ObjectListChunk(ObjectListChunkKey key
		this.key = key;
		this.raw = raw;
		this.recLen = getRecordLength(key.getObjectType());
		this.size = raw.length / recLen;
	}

	int getByteSize() {
		return raw.length;
	}

	public ObjectListChunkKey getRowKey() {
		return key;
	}

	public int size() {
		return size;
	}

	public ObjectId getObjectId(int nth) {
		return ObjectId.fromRaw(raw
	}

	public void getObjectId(MutableObjectId out
		out.fromRaw(raw
	}

	public int getPathHashCode(int nth) {
		return NB.decodeInt32(raw
	}

	public byte[] toBytes() {
		return raw;
	}

	@Override
	public String toString() {
		return key.toString();
	}
}
