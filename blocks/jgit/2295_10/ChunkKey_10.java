
package org.eclipse.jgit.storage.dht;

import org.eclipse.jgit.lib.Constants;

public class ChunkInfo {
	public static enum Source implements TinyProtobuf.Enum {
		RECEIVE(1)
		INSERT(2)
		REPACK(3);

		private final int value;

		Source(int val) {
			this.value = val;
		}

		public int value() {
			return value;
		}
	}

	public static final int OBJ_MIXED = 0;

	public static ChunkInfo fromBytes(ChunkKey chunkKey
		ChunkInfo info = new ChunkInfo();
		info.chunkKey = chunkKey;

		TinyProtobuf.Decoder d = TinyProtobuf.decode(raw);
		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				info.source = d.intEnum(Source.values());
				continue;
			case 2:
				info.objectType = d.int32();
				continue;
			case 3:
				info.fragment = d.bool();
				continue;
			case 4:
				info.cachedPack = CachedPackKey.fromBytes(d.bytes());
				continue;

			case 5: {
				TinyProtobuf.Decoder m = d.message();
				for (;;) {
					switch (m.next()) {
					case 0:
						continue PARSE;
					case 1:
						info.objectsTotal = m.int32();
						continue;
					case 2:
						info.objectsWhole = m.int32();
						continue;
					case 3:
						info.objectsOfsDelta = m.int32();
						continue;
					case 4:
						info.objectsRefDelta = m.int32();
						continue;
					default:
						m.skip();
						continue;
					}
				}
			}
			case 6:
				info.chunkSize = d.int32();
				continue;
			case 7:
				info.indexSize = d.int32();
				continue;
			case 8:
				info.metaSize = d.int32();
				continue;
			default:
				d.skip();
				continue;
			}
		}
		return info;
	}

	private static byte[] toBytes(ChunkInfo info) {
		TinyProtobuf.Encoder objects = TinyProtobuf.encode(48);
		objects.int32IfNotZero(1
		objects.int32IfNotZero(2
		objects.int32IfNotZero(3
		objects.int32IfNotZero(4

		TinyProtobuf.Encoder e = TinyProtobuf.encode(128);
		e.intEnum(1
		e.int32IfNotNegative(2
		e.boolIfTrue(3
		e.string(4
		e.message(5
		e.int32IfNotZero(6
		e.int32IfNotZero(7
		e.int32IfNotZero(8
		return e.asByteArray();
	}

	ChunkKey chunkKey;

	Source source;

	int objectType = -1;

	boolean fragment;

	CachedPackKey cachedPack;

	int objectsTotal;

	int objectsWhole;

	int objectsOfsDelta;

	int objectsRefDelta;

	int chunkSize;

	int indexSize;

	int metaSize;

	public RepositoryKey getRepositoryKey() {
		return chunkKey.getRepositoryKey();
	}

	public ChunkKey getChunkKey() {
		return chunkKey;
	}

	public Source getSource() {
		return source;
	}

	public int getObjectType() {
		return objectType;
	}

	public boolean isFragment() {
		return fragment;
	}

	public CachedPackKey getCachedPack() {
		return cachedPack;
	}

	public int getChunkSizeInBytes() {
		return chunkSize;
	}

	public int getIndexSizeInBytes() {
		return indexSize;
	}

	public int getMetaSizeInBytes() {
		return metaSize;
	}

	public int getObjectsTotal() {
		return objectsTotal;
	}

	public int getObjectsWhole() {
		return objectsWhole;
	}

	public int getObjectsOffsetDelta() {
		return objectsOfsDelta;
	}

	public int getObjectsReferenceDelta() {
		return objectsRefDelta;
	}

	public byte[] toBytes() {
		return toBytes(this);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("ChunkInfo:");
		b.append(chunkKey);
		b.append(" [");
		if (getSource() != null)
			b.append(" ").append(getSource());
		if (isFragment())
			b.append(" fragment");
		if (getObjectType() != 0)
			b.append(" ").append(Constants.typeString(getObjectType()));
		if (0 < getObjectsTotal())
			b.append(" objects=").append(getObjectsTotal());
		if (0 < getChunkSizeInBytes())
			b.append(" chunk=").append(getChunkSizeInBytes()).append("B");
		if (0 < getIndexSizeInBytes())
			b.append(" index=").append(getIndexSizeInBytes()).append("B");
		b.append(" ]");
		return b.toString();
	}
}
