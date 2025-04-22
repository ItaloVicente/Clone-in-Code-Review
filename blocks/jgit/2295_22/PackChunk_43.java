package org.eclipse.jgit.storage.dht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AsyncObjectLoaderQueue;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;

final class OpenQueue<T extends ObjectId> extends QueueObjectLookup<T>
		implements AsyncObjectLoaderQueue<T> {
	private Map<ChunkKey

	private Iterator<Collection<ObjectWithInfo<T>>> chunkItr;

	private Iterator<ObjectWithInfo<T>> objectItr;

	private Prefetcher prefetcher;

	private ObjectWithInfo<T> current;

	private PackChunk currChunk;

	OpenQueue(DhtReader reader
		super(reader
		setCacheLoadedInfo(true);
		setNeedChunkOnly(true);
		init(objectIds);

		byChunk = new LinkedHashMap<ChunkKey
		objectItr = Collections.<ObjectWithInfo<T>> emptyList().iterator();
	}

	public boolean next() throws MissingObjectException
		if (chunkItr == null)
			init();

		if (!objectItr.hasNext()) {
			currChunk = null;
			if (!chunkItr.hasNext()) {
				release();
				return false;
			}
			objectItr = chunkItr.next().iterator();
		}

		current = objectItr.next();
		return true;
	}

	public T getCurrent() {
		return current.object;
	}

	public ObjectId getObjectId() {
		return getCurrent();
	}

	public ObjectLoader open() throws IOException {
		ChunkKey chunkKey = current.chunkKey;

		PackChunk chunk;
		if (currChunk != null && chunkKey.equals(currChunk.getChunkKey()))
			chunk = currChunk;
		else {
			chunk = prefetcher.get(chunkKey);
			if (chunk == null)
				throw new DhtMissingChunkException(chunkKey);
			currChunk = chunk;
			reader.recentChunk(chunk);
		}

		if (current.info != null) {
			int ptr = current.info.getOffset();
			int type = current.info.getType();
			return PackChunk.read(chunk
		} else {
			int ptr = chunk.findOffset(repo
			if (ptr < 0)
				throw DhtReader.missing(current.object
			return PackChunk.read(chunk
		}
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		release();
		return true;
	}

	@Override
	public void release() {
		prefetcher = null;
		currChunk = null;
	}

	private void init() throws IOException {
		ObjectWithInfo<T> c;

		while ((c = nextObjectWithInfo()) != null) {
			ChunkKey chunkKey = c.chunkKey;
			Collection<ObjectWithInfo<T>> list = byChunk.get(chunkKey);
			if (list == null) {
				list = new ArrayList<ObjectWithInfo<T>>();
				byChunk.put(chunkKey

				if (prefetcher == null)
					prefetcher = new Prefetcher(reader
				prefetcher.push(chunkKey);
			}
			list.add(c);
		}

		chunkItr = byChunk.values().iterator();
	}
}
