
package org.eclipse.jgit.storage.dht;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.pack.CachedPack;
import org.eclipse.jgit.storage.pack.PackOutputStream;

public class DhtCachedPack extends CachedPack {
	private final CachedPackInfo info;

	DhtCachedPack(CachedPackInfo info) {
		this.info = info;
	}

	@Override
	public Set<ObjectId> getTips() {
		return Collections.unmodifiableSet(info.tips);
	}

	@Override
	public long getObjectCount() {
		return info.getObjectsTotal();
	}

	@Override
	public long getDeltaCount() throws IOException {
		return info.getObjectsDelta();
	}

	public CachedPackInfo getCachedPackInfo() {
		return info;
	}

	@Override
	public <T extends ObjectId> Set<ObjectId> hasObject(Iterable<T> toFind)
			throws IOException {

		return Collections.emptySet();
	}

	void copyAsIs(PackOutputStream out
			throws IOException {
		prefetcher.push(info.chunks);

		Map<ChunkKey
		for (ChunkKey key : info.chunks) {
			PackChunk chunk = prefetcher.get(key);
			if (chunk == null)
				chunk = ctx.getChunk(key);

			long position = out.length();
			if (chunk.getMeta() != null && chunk.getMeta().baseChunks != null) {
				for (ChunkMeta.BaseChunk base : chunk.getMeta().baseChunks) {
					Long act = startsAt.get(base.getChunkKey());
					long exp = position - base.getRelativeStart();

					if (act == null) {
						throw new DhtException(MessageFormat.format(
								DhtText.get().wrongChunkPositionInCachedPack
								info.getRowKey()
								"[not written]"
					}

					if (act.longValue() != exp) {
						throw new DhtException(MessageFormat.format(
								DhtText.get().wrongChunkPositionInCachedPack
								info.getRowKey()
								act
					}
				}
			}

			startsAt.put(key
			chunk.copyEntireChunkAsIs(out
		}
	}
}
