
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.storage.dht.DhtReader.ChunkAndOffset;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.Database;

class Prefetcher implements StreamingCallback<Collection<PackChunk.Members>> {
	private static enum Status {
		ON_QUEUE
	}

	private final Database db;

	private final DhtReader.Statistics stats;

	private final int objectType;

	private final HashMap<ChunkKey

	private final HashMap<ChunkKey

	private final LinkedList<ChunkKey> queue;

	private final boolean followEdgeHints;

	private final int averageChunkSize;

	private final int highWaterMark;

	private final int lowWaterMark;

	private boolean cacheLoadedChunks;

	private boolean first = true;

	private int bytesReady;

	private int bytesLoading;

	private DhtException error;

	Prefetcher(DhtReader reader
		this.db = reader.getDatabase();
		this.stats = reader.getStatistics();
		this.objectType = objectType;
		this.ready = new HashMap<ChunkKey
		this.status = new HashMap<ChunkKey
		this.queue = new LinkedList<ChunkKey>();
		this.followEdgeHints = reader.getOptions().isPrefetchFollowEdgeHints();
		this.averageChunkSize = reader.getInserterOptions().getChunkSize();
		this.highWaterMark = reader.getOptions().getPrefetchLimit();

		int lwm = (highWaterMark / averageChunkSize) - 4;
		if (lwm <= 0)
			lwm = (highWaterMark / averageChunkSize) / 2;
		lowWaterMark = lwm * averageChunkSize;
		cacheLoadedChunks = true;
	}

	boolean isType(int type) {
		return objectType == type;
	}

	synchronized void setCacheLoadedChunks(boolean cacheLoadedChunks) {
		this.cacheLoadedChunks = cacheLoadedChunks;
	}

	void push(DhtReader ctx
			MissingObjectException {

		int time = -1;
		PackChunk chunk = null;

		for (RevCommit cmit : roots) {
			if (time < cmit.getCommitTime()) {
				ChunkAndOffset p = ctx.getChunkGently(cmit
				if (p != null && p.chunk.getMeta() != null) {
					time = cmit.getCommitTime();
					chunk = p.chunk;
				}
			}
		}

		if (chunk != null) {
			synchronized (this) {
				status.put(chunk.getChunkKey()
				push(chunk.getMeta());
			}
		}
	}

	void push(DhtReader ctx
			MissingObjectException {
		ChunkAndOffset p = ctx.getChunk(start
		synchronized (this) {
			status.put(p.chunk.getChunkKey()
			push(p.chunk.getMeta());
		}
	}

	void push(ChunkKey key) {
		push(Collections.singleton(key));
	}

	void push(ChunkMeta meta) {
		if (meta == null)
			return;

		ChunkMeta.PrefetchHint hint;
		switch (objectType) {
		case OBJ_COMMIT:
			hint = meta.getCommitPrefetch();
			break;
		case OBJ_TREE:
			hint = meta.getTreePrefetch();
			break;
		default:
			return;
		}

		if (hint != null) {
			synchronized (this) {
				if (followEdgeHints && !hint.getEdge().isEmpty())
					push(hint.getEdge());
				else
					push(hint.getSequential());
			}
		}
	}

	void push(Iterable<ChunkKey> list) {
		synchronized (this) {
			for (ChunkKey key : list) {
				if (status.containsKey(key))
					continue;
				status.put(key
				queue.add(key);
			}
			maybeStartGet();
		}
	}

	synchronized ChunkAndOffset find(RepositoryKey repo
		for (PackChunk c : ready.values()) {
			int p = c.findOffset(repo
			if (0 <= p)
				return new ChunkAndOffset(useReadyChunk(c.getChunkKey())
		}
		return null;
	}

	synchronized PackChunk get(ChunkKey key) throws DhtException {
		GET: for (;;) {
			if (error != null)
				throw error;

			Status chunkStatus = status.get(key);
			if (chunkStatus == null)
				return null;

			switch (chunkStatus) {
			case ON_QUEUE:
				if (queue.isEmpty()) {
					status.put(key
					return null;

				} else if (bytesReady + bytesLoading < highWaterMark) {
					if (!queue.getFirst().equals(key)) {
						stats.access(key).cntPrefetcher_OutOfOrder++;
						queue.remove(key);
						queue.addFirst(key);
					}
					forceStartGet();
					continue GET;

				} else {
					stats.access(key).cntPrefetcher_OutOfOrder++;
					status.put(key
					return null;
				}

				status.put(key
			case WAITING:
				stats.access(key).cntPrefetcher_WaitedForLoad++;
				try {
					wait();
				} catch (InterruptedException e) {
					throw new DhtTimeoutException(e);
				}
				continue GET;

			case READY:
				return useReadyChunk(key);

			case DONE:
				stats.access(key).cntPrefetcher_Revisited++;
				return null;

			default:
				throw new IllegalStateException(key + " " + chunkStatus);
			}
		}
	}

	private PackChunk useReadyChunk(ChunkKey key) {
		PackChunk chunk = ready.remove(key);

		if (cacheLoadedChunks)
			chunk = ChunkCache.get().put(chunk);

		status.put(chunk.getChunkKey()
		bytesReady -= chunk.getTotalSize();
		push(chunk.getMeta());
		maybeStartGet();
		return chunk;
	}

	private void maybeStartGet() {
		if (!queue.isEmpty() && bytesReady + bytesLoading <= lowWaterMark)
			forceStartGet();
	}

	private void forceStartGet() {
		LinkedHashSet<ChunkKey> toLoad = new LinkedHashSet<ChunkKey>();
		ChunkCache cache = ChunkCache.get();

		while (bytesReady + bytesLoading < highWaterMark && !queue.isEmpty()) {
			ChunkKey key = queue.removeFirst();
			PackChunk chunk = cache.get(key);

			if (chunk != null) {
				stats.access(key).cntPrefetcher_ChunkCacheHit++;
				chunkIsReady(chunk);
			} else {
				stats.access(key).cntPrefetcher_Load++;
				toLoad.add(key);
				status.put(key
				bytesLoading += averageChunkSize;

				if (first)
					break;
			}
		}

		if (!toLoad.isEmpty() && error == null)
			db.chunk().get(Context.LOCAL

		if (first) {
			first = false;
			maybeStartGet();
		}
	}

	public synchronized void onPartialResult(Collection<PackChunk.Members> res) {
		try {
			bytesLoading -= averageChunkSize * res.size();
			for (PackChunk.Members builder : res)
				chunkIsReady(builder.build());
		} catch (DhtException loadError) {
			onError(loadError);
		}
	}

	private void chunkIsReady(PackChunk chunk) {
		ChunkKey key = chunk.getChunkKey();
		ready.put(key
		bytesReady += chunk.getTotalSize();

		if (status.put(key
			notifyAll();
	}

	public synchronized void onSuccess(Collection<PackChunk.Members> result) {
		if (result != null && !result.isEmpty())
			onPartialResult(result);
	}

	public synchronized void onFailure(DhtException asyncError) {
		onError(asyncError);
	}

	private void onError(DhtException asyncError) {
		if (error == null) {
			error = asyncError;
			notifyAll();
		}
	}
}
