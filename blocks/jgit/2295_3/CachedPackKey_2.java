
package org.eclipse.jgit.storage.dht;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.jgit.lib.ObjectId;

public class CachedPackInfo {
	public static CachedPackInfo fromBytes(RepositoryKey repo
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
				info.tips.add(d.stringObjectId());
				continue;
			case 4:
				info.totalObjects = d.int64();
				continue;
			case 5:
				info.totalBytes = d.int64();
				continue;
			case 6:
				info.chunks.add(ChunkKey.fromShortBytes(repo
				continue;
			default:
				d.skip();
				continue;
			}
		}
		return info;
	}

	private static byte[] toBytes(CachedPackInfo info) {
		TinyProtobuf.Encoder e = TinyProtobuf.encode(1024);
		e.string(1
		e.string(2
		for (ObjectId tip : info.tips)
			e.string(3
		e.int64IfNotZero(4
		e.int64IfNotZero(5
		for (ChunkKey key : info.chunks)
			e.bytes(6
		return e.asByteArray();
	}

	ObjectId name;

	ObjectId version;

	SortedSet<ObjectId> tips = new TreeSet<ObjectId>();

	long totalObjects;

	long totalBytes;

	List<ChunkKey> chunks = new ArrayList<ChunkKey>();

	public CachedPackKey getRowKey() {
		return new CachedPackKey(name
	}

	public long getTotalObjects() {
		return totalObjects;
	}

	public long getTotalBytes() {
		return totalBytes;
	}

	public byte[] toBytes() {
		return toBytes(this);
	}

	@Override
	public String toString() {
		return getRowKey().toString();
	}
}
