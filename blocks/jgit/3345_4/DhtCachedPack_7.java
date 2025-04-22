
package org.eclipse.jgit.storage.dht;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.generated.storage.dht.proto.GitStore.ChunkMeta;
import org.eclipse.jgit.generated.storage.dht.proto.GitStore.ChunkMeta.BaseChunk;

class ChunkMetaUtil {
	static BaseChunk getBaseChunk(ChunkKey chunkKey
			long position) throws DhtException {

		List<BaseChunk> baseChunks = meta.getBaseChunkList();
		int high = baseChunks.size();
		int low = 0;
		while (low < high) {
			final int mid = (low + high) >>> 1;
			final BaseChunk base = baseChunks.get(mid);

			if (position > base.getRelativeStart()) {
				low = mid + 1;

			} else if (mid == 0 || position == base.getRelativeStart()) {
				return base;

			} else if (baseChunks.get(mid - 1).getRelativeStart() < position) {
				return base;

			} else {
				high = mid;
			}
		}

		throw new DhtException(MessageFormat.format(
				DhtText.get().missingLongOffsetBase
				Long.valueOf(position)));
	}

	static ChunkKey getNextFragment(ChunkMeta meta
		int cnt = meta.getFragmentCount();
		for (int i = 0; i < cnt - 1; i++) {
			ChunkKey key = ChunkKey.fromString(meta.getFragment(i));
			if (chunkKey.equals(key))
				return ChunkKey.fromString(meta.getFragment(i + 1));
		}
		return null;
	}

	private ChunkMetaUtil() {
	}
}
