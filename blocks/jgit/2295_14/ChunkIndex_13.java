
package org.eclipse.jgit.storage.dht;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.lib.AnyObjectId;
import static org.eclipse.jgit.lib.Constants.*;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.NB;

public abstract class ChunkIndex {
	private static final int V1 = 0x01;

	static ChunkIndex fromBytes(ChunkKey key
		int v = index[0] & 0xff;
		switch (v) {
		case V1: {
			final int offsetFormat = index[1] & 7;
			switch (offsetFormat) {
			case 1:
				return new Offset1(index
			case 2:
				return new Offset2(index
			case 3:
				return new Offset3(index
			case 4:
				return new Offset4(index
			default:
				throw new DhtException(MessageFormat.format(
						DhtText.get().unsupportedChunkIndex
						Integer.toHexString(NB.decodeUInt16(index
			}
		}
		default:
			throw new DhtException(MessageFormat.format(
					DhtText.get().unsupportedChunkIndex
					Integer.toHexString(v)
		}
	}

	static byte[] create(List<? extends PackedObjectInfo> list) {
		int cnt = list.size();
		sortObjectList(list);

		int fanoutFormat = 0;
		int[] buckets = null;
		if (64 < cnt) {
			buckets = new int[256];
			for (PackedObjectInfo oe : list)
				buckets[oe.getFirstByte()]++;
			fanoutFormat = selectFanoutFormat(buckets);
		}

		int offsetFormat = selectOffsetFormat(list);
		];
		index[0] = V1;
		index[1] = (byte) ((fanoutFormat << 3) | offsetFormat);

		int ptr = 2;

		switch (fanoutFormat) {
		case 0:
			break;
		case 1:
			for (int i = 0; i < 256; i++
				index[ptr] = (byte) buckets[i];
			break;
		case 2:
			for (int i = 0; i < 256; i++
				NB.encodeInt16(index
			break;
		case 3:
			for (int i = 0; i < 256; i++
				encodeInt24(index
			break;
		case 4:
			for (int i = 0; i < 256; i++
				NB.encodeInt32(index
			break;
		}

		for (PackedObjectInfo oe : list) {
			oe.copyRawTo(index
			ptr += OBJECT_ID_LENGTH;
		}

		switch (offsetFormat) {
		case 1:
			for (PackedObjectInfo oe : list)
				index[ptr++] = (byte) oe.getOffset();
			break;

		case 2:
			for (PackedObjectInfo oe : list) {
				NB.encodeInt16(index
				ptr += 2;
			}
			break;

		case 3:
			for (PackedObjectInfo oe : list) {
				encodeInt24(index
				ptr += 3;
			}
			break;

		case 4:
			for (PackedObjectInfo oe : list) {
				NB.encodeInt32(index
				ptr += 4;
			}
			break;
		}

		return index;
	}

	private static int selectFanoutFormat(int[] buckets) {
		int fmt = 1;
		int max = 1 << (8 * fmt);

		for (int cnt : buckets) {
			while (max <= cnt && fmt < 4) {
				if (++fmt == 4)
					return fmt;
				max = 1 << (8 * fmt);
			}
		}
		return fmt;
	}

	private static int selectOffsetFormat(List<? extends PackedObjectInfo> list) {
		int fmt = 1;
		int max = 1 << (8 * fmt);

		for (PackedObjectInfo oe : list) {
			while (max <= oe.getOffset() && fmt < 4) {
				if (++fmt == 4)
					return fmt;
				max = 1 << (8 * fmt);
			}
		}
		return fmt;
	}

	@SuppressWarnings("unchecked")
	private static void sortObjectList(List<? extends PackedObjectInfo> list) {
		Collections.sort(list);
	}

	private final byte[] index;

	private final int[] fanout;

	private final int idTable;

	private final int offsetTable;

	private final int count;

	ChunkIndex(byte[] index
		final int ctl = index[1];
		final int fanoutFormat = (ctl >>> 3) & 7;
		final int offsetFormat = ctl & 7;

		switch (fanoutFormat) {
		case 0:
			break;

		case 1: {
			int last = 0;
			fanout = new int[256];
			for (int i = 0; i < 256; i++) {
				last += index[2 + i] & 0xff;
				fanout[i] = last;
			}
			break;
		}
		case 2: {
			int last = 0;
			fanout = new int[256];
			for (int i = 0; i < 256; i++) {
				last += NB.decodeUInt16(index
				fanout[i] = last;
			}
			break;
		}
		case 3: {
			int last = 0;
			fanout = new int[256];
			for (int i = 0; i < 256; i++) {
				last += decodeUInt24(index
				fanout[i] = last;
			}
			break;
		}
		case 4: {
			int last = 0;
			fanout = new int[256];
			for (int i = 0; i < 256; i++) {
				last += NB.decodeInt32(index
				fanout[i] = last;
			}
			break;
		}
		default:
			throw new DhtException(MessageFormat.format(
					DhtText.get().unsupportedChunkIndex
					Integer.toHexString(NB.decodeUInt16(index
		}

		this.index = index;
		this.idTable = 2 + 256 * fanoutFormat;

		int recsz = OBJECT_ID_LENGTH + offsetFormat;
		this.count = (index.length - idTable) / recsz;
		this.offsetTable = idTable + count * OBJECT_ID_LENGTH;
	}

	public final int getObjectCount() {
		return count;
	}

	public final ObjectId getObjectId(int nth) {
		return ObjectId.fromRaw(index
	}

	public final int getOffset(int nth) {
		return getOffset(index
	}

	int getIndexSize() {
		int sz = index.length;
		if (fanout != null)
			sz += 12 + 256 * 4;
		return sz;
	}

	final int findOffset(AnyObjectId objId) {
		int hi

		if (fanout != null) {
			int fb = objId.getFirstByte();
			lo = fb == 0 ? 0 : fanout[fb - 1];
			hi = fanout[fb];
		} else {
			lo = 0;
			hi = count;
		}

		while (lo < hi) {
			final int mid = (lo + hi) >>> 1;
			final int cmp = objId.compareTo(index
			if (cmp < 0)
				hi = mid;
			else if (cmp == 0)
				return getOffset(mid);
			else
				lo = mid + 1;
		}
		return -1;
	}

	abstract int getOffset(byte[] indexArray

	private int idPosition(int nth) {
		return idTable + (nth * OBJECT_ID_LENGTH);
	}

	private static class Offset1 extends ChunkIndex {
		Offset1(byte[] index
			super(index
		}

		int getOffset(byte[] index
			return index[offsetTable + nth] & 0xff;
		}
	}

	private static class Offset2 extends ChunkIndex {
		Offset2(byte[] index
			super(index
		}

		int getOffset(byte[] index
			return NB.decodeUInt16(index
		}
	}

	private static class Offset3 extends ChunkIndex {
		Offset3(byte[] index
			super(index
		}

		int getOffset(byte[] index
			return decodeUInt24(index
		}
	}

	private static class Offset4 extends ChunkIndex {
		Offset4(byte[] index
			super(index
		}

		int getOffset(byte[] index
			return NB.decodeInt32(index
		}
	}

	private static void encodeInt24(byte[] intbuf
		intbuf[offset + 2] = (byte) v;
		v >>>= 8;

		intbuf[offset + 1] = (byte) v;
		v >>>= 8;

		intbuf[offset] = (byte) v;
	}

	private static int decodeUInt24(byte[] intbuf
		int r = (intbuf[offset] & 0xff) << 8;

		r |= intbuf[offset + 1] & 0xff;
		r <<= 8;

		r |= intbuf[offset + 2] & 0xff;
		return r;
	}
}
