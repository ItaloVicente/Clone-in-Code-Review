
package org.eclipse.jgit.storage.dht;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.pack.CachedPack;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.PackOutputStream;
import org.eclipse.jgit.storage.pack.StoredObjectRepresentation;

public class DhtCachedPack extends CachedPack {
	private final CachedPackInfo info;

	private Set<ChunkKey> chunkKeySet;

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
	public boolean hasObject(ObjectToPack obj
		DhtObjectRepresentation objrep = (DhtObjectRepresentation) rep;
		if (chunkKeySet == null)
			chunkKeySet = new HashSet<ChunkKey>(info.chunks);
		return chunkKeySet.contains(objrep.getChunkKey());
	}

	void copyAsIs(PackOutputStream out
			throws IOException {
		Prefetcher p = new Prefetcher(ctx
		p.setCacheLoadedChunks(false);
		p.push(info.chunks);
		copyPack(out
	}

	private void copyPack(PackOutputStream out
			Prefetcher prefetcher
			DhtMissingChunkException
		Map<ChunkKey
		for (ChunkKey key : info.chunks) {
			PackChunk chunk = prefetcher.get(key);

			if (chunk == null)
				throw new DhtMissingChunkException(key);

			long position = out.length();
			if (chunk.getMeta() != null && chunk.getMeta().baseChunks != null) {
				for (ChunkMeta.BaseChunk base : chunk.getMeta().baseChunks) {
					Long act = startsAt.get(base.getChunkKey());
					long exp = position - base.getRelativeStart();

					if (act == null) {
						throw new DhtException(MessageFormat.format(DhtText
								.get().wrongChunkPositionInCachedPack
								.getRowKey()
								"[not written]"
					}

					if (act.longValue() != exp) {
						throw new DhtException(MessageFormat.format(DhtText
								.get().wrongChunkPositionInCachedPack
								.getRowKey()
								act
					}
				}
			}

			startsAt.put(key
			chunk.copyEntireChunkAsIs(out
		}
	}
}
