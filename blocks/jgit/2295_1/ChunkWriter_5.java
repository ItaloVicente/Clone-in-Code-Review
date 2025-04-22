
package org.eclipse.jgit.storage.dht;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;

public class ChunkLink {
	public static final Comparator<ChunkLink> BY_TIME = new Comparator<ChunkLink>() {
		public int compare(ChunkLink a
			return Long.signum(b.getTime() - a.getTime());
		}
	};

	public static void sort(List<ChunkLink> toSort) {
		Collections.sort(toSort
	}

	public static ChunkLink fromBytes(ChunkKey chunkKey
		TinyProtobuf.Decoder d = TinyProtobuf.decode(data);
		int offset = -1;
		int size = -1;
		ObjectId baseId = null;

		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				offset = d.int32();
				continue;
			case 2:
				size = d.int32();
				continue;
			case 3:
				baseId = d.stringObjectId();
				continue;
			default:
				d.skip();
				continue;
			}
		}

		if (offset < 0 || size < 0)
			throw new IllegalArgumentException(MessageFormat.format(
					DhtText.get().invalidChunkLink

		return new ChunkLink(chunkKey
	}

	private final ChunkKey chunk;

	private final long time;

	private final int pos;

	private final int size;

	private final ObjectId base;

	ChunkLink(ChunkKey chunk
		this.chunk = chunk;
		this.time = time < 0 ? chunk.getApproximateCreationTime() : time;
		this.pos = pos;
		this.size = size;
		this.base = base;
	}

	public ChunkKey getChunkKey() {
		return chunk;
	}

	public long getTime() {
		return time;
	}

	int getOffset() {
		return pos;
	}

	int getSize() {
		return size;
	}

	ObjectId getDeltaBase() {
		return base;
	}

	public byte[] asByteArray() {
		int max = 3 * 6 + Constants.OBJECT_ID_STRING_LENGTH;
		TinyProtobuf.Encoder e = TinyProtobuf.encode(max);
		e.int32(1
		e.int32(2
		if (base != null)
			e.string(3
		return e.asByteArray();
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("ChunkLink:");
		b.append(chunk);
		b.append(" [");
		b.append("time=").append(new Date(time));
		b.append(" offset=").append(pos);
		b.append(" size=").append(size);
		if (base != null)
			b.append(" base=").append(base);
		b.append(']');
		return b.toString();
	}
}
