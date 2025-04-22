
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.storage.dht.DhtReader.ChunkAndOffset;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.Database;

class Prefetcher implements StreamingCallback<Collection<PackChunk.Members>> {
	private static enum Status {
		ON_QUEUE
	}

	private final Database db;

	private final int objectType;

	private final HashMap<ChunkKey

	private final HashMap<ChunkKey

	private final LinkedList<ChunkKey> queue;

	private final int highWaterMark;

	private final int lowWaterMark;

	private boolean includeFragments;

	private boolean cacheLoadedChunks;

	private boolean first = true;

	private int cntReady;

	private int cntLoading;

	private DhtException error;

	Prefetcher(Database db
		this.db = db;
		this.objectType = objectType;
		this.ready = new HashMap<ChunkKey
		this.status = new HashMap<ChunkKey
		this.queue = new LinkedList<ChunkKey>();
		this.highWaterMark = options.getPrefetchDepth();

		int lwm = highWaterMark - 4;
		if (lwm <= 0)
			lwm = highWaterMark / 2;
		lowWaterMark = lwm;
		cacheLoadedChunks = true;
	}

	boolean isType(int type) {
		return objectType == type;
	}

	synchronized void setIncludeFragments(boolean includeFragments) {
		this.includeFragments = includeFragments;
	}

	synchronized void setCacheLoadedChunks(boolean cacheLoadedChunks) {
		this.cacheLoadedChunks = cacheLoadedChunks;
	}

	synchronized void cancel() {
		ready.clear();
		status.clear();
		queue.clear();

		cntReady = 0;
		cntLoading = 0;
		error = null;
	}

	void push(DhtReader ctx
			MissingObjectException {

		int time = -1;
		PackChunk chunk = null;

		for (RevCommit cmit : roots) {
			if (time < cmit.getCommitTime()) {
				ChunkAndOffset p = ctx.getChunkGently(cmit
				if (p == null || p.chunk.getMeta() == null)
					continue;
				time = cmit.getCommitTime();
				chunk = p.chunk;
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

		ChunkAndOffset p = ctx.getChunkGently(start
		if (p == null || p.chunk.getMeta() == null)
			return;

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
				push(hint.getEdge());
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

				} else if (cntReady + cntLoading < highWaterMark) {
					if (!queue.getFirst().equals(key)) {
						queue.remove(key);
						queue.addFirst(key);
					}
					forceStartGet();
					continue GET;

				} else {
					status.put(key
					return null;
				}

				status.put(key
			case WAITING:
				try {
					wait();
				} catch (InterruptedException e) {
					throw new DhtTimeoutException(e);
				}
				continue GET;

			case READY:
				return useReadyChunk(key);

			case DONE:
				status.put(key
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
		cntReady--;
		push(chunk.getMeta());
		maybeStartGet();
		return chunk;
	}

	private void maybeStartGet() {
		if (!queue.isEmpty() && cntReady + cntLoading <= lowWaterMark)
			forceStartGet();
	}

	private void forceStartGet() {
		ChunkCache cache = ChunkCache.get();
		HashSet<ChunkKey> toLoad = new HashSet<ChunkKey>();
		while (cntReady + cntLoading < highWaterMark && !queue.isEmpty()) {
			ChunkKey key = queue.removeFirst();
			PackChunk chunk = cache.get(key);

			if (chunk != null) {
				chunkIsReady(chunk);
			} else {
				toLoad.add(key);
				status.put(key
				cntLoading++;

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
			cntLoading -= res.size();
			for (PackChunk.Members builder : res)
				chunkIsReady(builder.build());
		} catch (DhtException loadError) {
			onError(loadError);
		}
	}

	private void chunkIsReady(PackChunk chunk) {
		ChunkKey key = chunk.getChunkKey();
		ready.put(key
		cntReady++;

		if (status.put(key
			notifyAll();

		ChunkMeta meta = chunk.getMeta();
		if (includeFragments && meta != null && meta.getFragmentCount() != 0) {
			int cnt = meta.getFragmentCount();
			for (int i = cnt - 1; 0 <= i; i--) {
				key = meta.getFragmentKey(i);
				if (status.containsKey(key))
					continue;
				status.put(key
				queue.addFirst(key);
			}
		}
	}

	public synchronized void onSuccess(Collection<PackChunk.Members> result) {
		if (result != null && !result.isEmpty())
			onPartialResult(result);
		maybeStartGet();
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
