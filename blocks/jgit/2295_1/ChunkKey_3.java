
package org.eclipse.jgit.storage.dht;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.NB;

class ChunkIndex {
	private static final int V1 = 0x0001;

	static ChunkIndex fromRaw(ChunkKey key
		int v = NB.decodeUInt16(index
		switch (v) {
		case V1:
			return new ChunkIndex(index);
		default:
			throw new DhtException(MessageFormat.format(
					DhtText.get().unsupportedChunkIndex
		}
	}

	private final byte[] index;

	private final int count;

	private final int offsetTable;

	ChunkIndex(byte[] index) {
		this.index = index;
		this.count = (index.length - 2) / (Constants.OBJECT_ID_LENGTH + 4);
		this.offsetTable = 2 + count * Constants.OBJECT_ID_LENGTH;
	}

	int findOffset(AnyObjectId objId) {
		int high = count;
		int low = 0;
		do {
			final int mid = (low + high) >>> 1;
			final int cmp = objId.compareTo(index
			if (cmp < 0)
				high = mid;
			else if (cmp == 0)
				return NB.decodeInt32(index
			else
				low = mid + 1;
		} while (low < high);
		return -1;
	}

	private static int idOffset(int nth) {
		return 2 + (nth * Constants.OBJECT_ID_LENGTH);
	}

	static byte[] create(List<? extends PackedObjectInfo> list) {
		int cnt = list.size();

		if (1 < cnt)
			Collections.sort(list);

		byte[] index = new byte[2 + cnt * Constants.OBJECT_ID_LENGTH + cnt * 4];
		NB.encodeInt16(index

		int ptr = 2;
		for (PackedObjectInfo oe : list) {
			oe.copyRawTo(index
			ptr += Constants.OBJECT_ID_LENGTH;
		}

		for (PackedObjectInfo oe : list) {
			NB.encodeInt32(index
			ptr += 4;
		}

		return index;
	}
}
