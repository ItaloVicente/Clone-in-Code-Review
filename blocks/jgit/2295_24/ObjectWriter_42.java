
package org.eclipse.jgit.storage.dht;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;

public class ObjectInfo {
	public static final Comparator<ObjectInfo> BY_TIME = new Comparator<ObjectInfo>() {
		public int compare(ObjectInfo a
			return Long.signum(a.getTime() - b.getTime());
		}
	};

	public static void sort(List<ObjectInfo> toSort) {
		Collections.sort(toSort
	}

	public static ObjectInfo fromBytes(ChunkKey chunkKey
		return fromBytes(chunkKey
	}

	public static ObjectInfo fromBytes(ChunkKey chunkKey
			TinyProtobuf.Decoder d
		int typeCode = -1;
		int offset = -1;
		long packedSize = -1;
		long inflatedSize = -1;
		ObjectId deltaBase = null;
		boolean fragmented = false;

		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				typeCode = d.int32();
				continue;
			case 2:
				offset = d.int32();
				continue;
			case 3:
				packedSize = d.int64();
				continue;
			case 4:
				inflatedSize = d.int64();
				continue;
			case 5:
				deltaBase = d.bytesObjectId();
				continue;
			case 6:
				fragmented = d.bool();
				continue;
			default:
				d.skip();
				continue;
			}
		}

		if (typeCode < 0 || offset < 0 || packedSize < 0 || inflatedSize < 0)
			throw new IllegalArgumentException(MessageFormat.format(
					DhtText.get().invalidObjectInfo

		return new ObjectInfo(chunkKey
				packedSize
	}

	private final ChunkKey chunk;

	private final long time;

	private final int typeCode;

	private final int offset;

	private final long packedSize;

	private final long inflatedSize;

	private final ObjectId deltaBase;

	private final boolean fragmented;

	ObjectInfo(ChunkKey chunk
			long packedSize
			boolean fragmented) {
		this.chunk = chunk;
		this.time = time < 0 ? 0 : time;
		this.typeCode = typeCode;
		this.offset = offset;
		this.packedSize = packedSize;
		this.inflatedSize = inflatedSize;
		this.deltaBase = base;
		this.fragmented = fragmented;
	}

	public ChunkKey getChunkKey() {
		return chunk;
	}

	public long getTime() {
		return time;
	}

	public int getType() {
		return typeCode;
	}

	public long getSize() {
		return inflatedSize;
	}

	public boolean isDelta() {
		return getDeltaBase() != null;
	}

	public boolean isFragmented() {
		return fragmented;
	}

	int getOffset() {
		return offset;
	}

	long getPackedSize() {
		return packedSize;
	}

	ObjectId getDeltaBase() {
		return deltaBase;
	}

	public byte[] asBytes() {
		TinyProtobuf.Encoder e = TinyProtobuf.encode(256);
		e.int32(1
		e.int32(2
		e.int64(3
		e.int64(4
		e.bytes(5
		if (fragmented)
			e.bool(6
		return e.asByteArray();
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("ObjectInfo:");
		b.append(chunk);
		b.append(" [");
		if (0 < time)
			b.append(" time=").append(new Date(time));
		b.append(" type=").append(Constants.typeString(typeCode));
		b.append(" offset=").append(offset);
		b.append(" packedSize=").append(packedSize);
		b.append(" inflatedSize=").append(inflatedSize);
		if (deltaBase != null)
			b.append(" deltaBase=").append(deltaBase.name());
		if (fragmented)
			b.append(" fragmented");
		b.append(" ]");
		return b.toString();
	}
}
