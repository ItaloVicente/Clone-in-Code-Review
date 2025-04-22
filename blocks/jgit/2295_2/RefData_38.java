
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
	}

	boolean isType(int type) {
		return objectType == type;
	}

	synchronized void setIncludeFragments(boolean includeFragments) {
		this.includeFragments = includeFragments;
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
				if (p == null || p.chunk.getPrefetch() == null)
					continue;
				time = cmit.getCommitTime();
				chunk = p.chunk;
			}
		}

		if (chunk != null) {
			synchronized (this) {
				status.put(chunk.getChunkKey()
				push(chunk.getPrefetch());
			}
		}
	}

	void push(DhtReader ctx
			MissingObjectException {

		ChunkAndOffset p = ctx.getChunkGently(start
		if (p == null || p.chunk.getPrefetch() == null)
			return;

		synchronized (this) {
			status.put(p.chunk.getChunkKey()
			push(p.chunk.getPrefetch());
		}
	}

	void push(ChunkKey key) {
		push(Collections.singleton(key));
	}

	void push(ChunkPrefetch prefetch) {
		if (prefetch == null)
			return;

		ChunkPrefetch.Hint hint;
		switch (objectType) {
		case OBJ_COMMIT:
			hint = prefetch.getCommits();
			break;
		case OBJ_TREE:
			hint = prefetch.getTrees();
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
				queue.remove(key);
			case DONE:
				status.put(key
				return null;

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

			default:
				throw new IllegalStateException(key + " " + chunkStatus);
			}
		}
	}

	private PackChunk useReadyChunk(ChunkKey key) {
		PackChunk chunk = ChunkCache.get().put(ready.remove(key));
		status.put(chunk.getChunkKey()
		cntReady--;
		push(chunk.getPrefetch());
		maybeStartGet();
		return chunk;
	}

	private void maybeStartGet() {
		if (queue.isEmpty())
			return;

		if (lowWaterMark < cntReady + cntLoading)
			return;

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

		ChunkFragments fragments = chunk.getFragments();
		if (includeFragments && fragments != null) {
			for (int i = fragments.size() - 1; 0 <= i; i--) {
				key = fragments.getChunkKey(i);
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
