
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.jgit.lib.ObjectId;

public class CachedPackInfo {
	public static CachedPackInfo fromBytes(byte[] raw) {
		CachedPackInfo info = new CachedPackInfo();

		TinyProtobuf.Decoder d = TinyProtobuf.decode(raw);
		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				info.name = d.stringObjectId();
				continue;
			case 2:
				info.version = d.stringObjectId();
				continue;
			case 3:
				info.objectsTotal = d.int64();
				continue;
			case 4:
				info.objectsDelta = d.int64();
				continue;
			case 5:
				info.bytesTotal = d.int64();
				continue;
			case 6: {
				TinyProtobuf.Decoder m = d.message();
				for (;;) {
					switch (m.next()) {
					case 0:
						continue PARSE;
					case 1:
						info.tips.add(m.stringObjectId());
						continue;
					default:
						m.skip();
						continue;
					}
				}
			}
			case 7: {
				TinyProtobuf.Decoder m = d.message();
				for (;;) {
					switch (m.next()) {
					case 0:
						continue PARSE;
					case 1:
						info.chunks.add(ChunkKey.fromBytes(m.bytes()));
						continue;
					default:
						m.skip();
						continue;
					}
				}
			}
			default:
				d.skip();
				continue;
			}
		}
		return info;
	}

	private static byte[] asBytes(CachedPackInfo info) {
		int tipSize = (2 + OBJECT_ID_STRING_LENGTH) * info.tips.size();
		TinyProtobuf.Encoder tipList = TinyProtobuf.encode(tipSize);
		for (ObjectId tip : info.tips)
			tipList.string(1

		int chunkSize = (2 + ChunkKey.KEYLEN) * info.chunks.size();
		TinyProtobuf.Encoder chunkList = TinyProtobuf.encode(chunkSize);
		for (ChunkKey key : info.chunks)
			chunkList.bytes(1

		TinyProtobuf.Encoder e = TinyProtobuf.encode(1024);
		e.string(1
		e.string(2
		e.int64(3
		e.int64IfNotZero(4
		e.int64IfNotZero(5
		e.message(6
		e.message(7
		return e.asByteArray();
	}

	ObjectId name;

	ObjectId version;

	SortedSet<ObjectId> tips = new TreeSet<ObjectId>();

	long objectsTotal;

	long objectsDelta;

	long bytesTotal;

	List<ChunkKey> chunks = new ArrayList<ChunkKey>();

	public CachedPackKey getRowKey() {
		return new CachedPackKey(name
	}

	public long getObjectsTotal() {
		return objectsTotal;
	}

	public long getObjectsDelta() {
		return objectsDelta;
	}

	public long getTotalBytes() {
		return bytesTotal;
	}

	public byte[] asBytes() {
		return asBytes(this);
	}

	@Override
	public String toString() {
		return getRowKey().toString();
	}
}
