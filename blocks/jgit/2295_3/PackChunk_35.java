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
import org.eclipse.jgit.storage.dht.spi.Database;

final class OpenQueue<T extends ObjectId> extends QueueObjectLookup<T>
		implements AsyncObjectLoaderQueue<T> {
	private final DhtReader reader;

	private Map<ChunkKey

	private Iterator<Collection<ObjectWithInfo<T>>> chunkItr;

	private Iterator<ObjectWithInfo<T>> objectItr;

	private Prefetcher prefetcher;

	private ObjectWithInfo<T> current;

	OpenQueue(RepositoryKey repo
			Iterable<T> objectIds
		super(repo
		this.reader = reader;

		byChunk = new LinkedHashMap<ChunkKey
		objectItr = Collections.<ObjectWithInfo<T>> emptyList().iterator();
	}

	public boolean next() throws MissingObjectException
		if (chunkItr == null)
			init();

		if (!objectItr.hasNext()) {
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
		ObjectInfo info = current.info;
		ChunkKey chunkKey = info.getChunkKey();
		PackChunk chunk = prefetcher.get(chunkKey);
		if (chunk == null)
			chunk = reader.getChunk(chunkKey);

		ObjectLoader ldr = open(chunk
		reader.recentChunk(chunk);
		return ldr;
	}

	private ObjectLoader open(PackChunk chunk
			throws IOException {
		return PackChunk.read(chunk
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		release();
		return true;
	}

	@Override
	public void release() {
		if (prefetcher != null) {
			prefetcher.cancel();
			prefetcher = null;
		}
	}

	private void init() throws IOException {
		ObjectWithInfo<T> c;

		while ((c = nextObjectWithInfo()) != null) {
			ChunkKey chunkKey = c.info.getChunkKey();
			Collection<ObjectWithInfo<T>> list = byChunk.get(chunkKey);
			if (list == null) {
				list = new ArrayList<ObjectWithInfo<T>>();
				byChunk.put(chunkKey

				if (prefetcher == null)
					prefetcher = new Prefetcher(db
				prefetcher.push(chunkKey);
			}
			list.add(c);
		}

		chunkItr = byChunk.values().iterator();
	}
}
