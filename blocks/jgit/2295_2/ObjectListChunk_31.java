
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
			return Long.signum(b.getTime() - a.getTime());
		}
	};

	public static void sort(List<ObjectInfo> toSort) {
		Collections.sort(toSort
	}

	public static ObjectInfo fromBytes(ChunkKey chunkKey
		TinyProtobuf.Decoder d = TinyProtobuf.decode(data);
		int offset = -1;
		int packedSize = -1;
		ObjectId deltaBase = null;
		boolean fragment = false;

		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				offset = d.int32();
				continue;
			case 2:
				packedSize = d.int32();
				continue;
			case 3:
				deltaBase = d.bytesObjectId();
				continue;
			case 4:
				fragment = d.bool();
				continue;
			default:
				d.skip();
				continue;
			}
		}

		if (offset < 0 || packedSize < 0)
			throw new IllegalArgumentException(MessageFormat.format(DhtText
					.get().invalidChunkLink

		return new ObjectInfo(chunkKey
				fragment);
	}

	private final ChunkKey chunk;

	private final long time;

	private final int offset;

	private final int packedSize;

	private final ObjectId deltaBase;

	private final boolean fragment;

	ObjectInfo(ChunkKey chunk
			boolean fragment) {
		this.chunk = chunk;
		this.time = time < 0 ? chunk.getApproximateCreationTime() : time;
		this.offset = offset;
		this.packedSize = size;
		this.deltaBase = base;
		this.fragment = fragment;
	}

	public ChunkKey getChunkKey() {
		return chunk;
	}

	public long getTime() {
		return time;
	}

	int getOffset() {
		return offset;
	}

	int getSize() {
		return packedSize;
	}

	ObjectId getDeltaBase() {
		return deltaBase;
	}

	boolean isFragment() {
		return fragment;
	}

	public byte[] toBytes() {
		int max = 2 * 6 + Constants.OBJECT_ID_LENGTH;
		TinyProtobuf.Encoder e = TinyProtobuf.encode(max);
		e.int32(1
		e.int32(2
		e.bytes(3
		e.boolIfTrue(4
		return e.asByteArray();
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("ObjectInfo:");
		b.append(chunk);
		b.append(" [");
		b.append("time=").append(new Date(time));
		b.append(" offset=").append(offset);
		b.append(" packedSize=").append(packedSize);
		if (deltaBase != null)
			b.append(" deltaBase=").append(deltaBase.name());
		if (fragment)
			b.append(" fragment");
		b.append(']');
		return b.toString();
	}
}
