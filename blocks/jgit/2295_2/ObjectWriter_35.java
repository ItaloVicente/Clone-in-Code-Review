
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.*;
import org.eclipse.jgit.lib.ObjectId;

public class ObjectListInfo {
	public static class Segment {
		final int objectType;

		int chunkStart;

		int chunkCount;

		int objectCount;

		Segment(int objectType) {
			this.objectType = objectType;
		}

		TinyProtobuf.Encoder toBytes() {
			TinyProtobuf.Encoder e = TinyProtobuf.encode(16);
			e.stringHex32(1
			e.int32(2
			e.int32IfNotZero(3
			return e;
		}

		static Segment fromBytes(int type
			Segment s = new Segment(type);
			for (;;) {
				switch (d.next()) {
				case 0:
					return s;
				case 1:
					s.chunkStart = d.stringHex32();
					continue;
				case 2:
					s.chunkCount = d.int32();
					continue;
				case 3:
					s.objectCount = d.int32();
					continue;
				default:
					d.skip();
				}
			}
		}
	}

	public static ObjectListInfo fromBytes(RepositoryKey repo
		ObjectListInfo info = new ObjectListInfo();
		info.repository = repo;

		TinyProtobuf.Decoder d = TinyProtobuf.decode(data);
		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				info.startingCommit = d.stringObjectId();
				continue;
			case 2:
				info.commits = Segment.fromBytes(OBJ_COMMIT
				continue;
			case 3:
				info.trees = Segment.fromBytes(OBJ_TREE
				continue;
			case 4:
				info.blobs = Segment.fromBytes(OBJ_BLOB
				continue;
			case 5:
				info.chunkCount = d.int32();
				continue;
			case 6:
				info.objectCount = d.int32();
				continue;
			case 7:
				info.listSizeInBytes = d.int32();
				continue;
			default:
				d.skip();
				continue;
			}
		}
		return info;
	}

	RepositoryKey repository;

	ObjectId startingCommit;

	Segment commits;

	Segment trees;

	Segment blobs;

	int chunkCount;

	int listSizeInBytes;

	int objectCount;

	public ObjectId getStartingCommit() {
		return startingCommit;
	}

	public RowKey getRowKey() {
		return ObjectIndexKey.create(repository
	}

	ObjectListChunkKey getChunkKey(int cid) {
		return new ObjectListChunkKey(repository
	}

	public byte[] toBytes() {
		TinyProtobuf.Encoder e = TinyProtobuf.encode(48);
		e.string(1
		if (commits != null)
			e.message(2
		if (trees != null)
			e.message(3
		if (blobs != null)
			e.message(4
		e.int32(5
		e.int32IfNotZero(6
		e.int32IfNotZero(7
		return e.asByteArray();
	}

	@Override
	public String toString() {
		return "ObjectListInfo:" + startingCommit.name();
	}
}
