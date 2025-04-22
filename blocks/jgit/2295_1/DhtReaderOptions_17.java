
package org.eclipse.jgit.storage.dht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.zip.Inflater;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.InflaterCache;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.pack.ObjectReuseAsIs;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.PackOutputStream;
import org.eclipse.jgit.storage.pack.PackWriter;

class DhtReader extends ObjectReader implements ObjectReuseAsIs {
	private final DhtRepository repository;

	private final RepositoryKey repo;

	private final Database db;

	private final DhtReaderOptions options;

	private final DeltaBaseCache deltaBaseCache;

	private Inflater inflater;

	private PackChunk lastChunk;

	DhtReader(DhtRepository repository
		this.repository = repository;
		this.repo = repo;
		this.db = db;
		this.options = DhtReaderOptions.DEFAULT;
		this.deltaBaseCache = new DeltaBaseCache(options);
	}

	DhtReaderOptions getOptions() {
		return options;
	}

	DeltaBaseCache getDeltaBaseCache() {
		return deltaBaseCache;
	}

	Inflater inflater() {
		if (inflater == null)
			inflater = InflaterCache.get();
		else
			inflater.reset();
		return inflater;
	}

	@Override
	public void release() {
		InflaterCache.release(inflater);
		inflater = null;

		lastChunk = null;

		super.release();
	}

	@Override
	public ObjectReader newReader() {
		return new DhtReader(repository
	}

	@Override
	public boolean has(AnyObjectId objId
		if (lastChunk != null && lastChunk.contains(repo
			return true;

		if (objId instanceof RefData.IdWithChunk
				|| repository.getRefDatabase().find(objId) != null)
			return true;

		if (ChunkCache.get().find(repo
			return true;

		return !find(ObjectIndexKey.create(repo
	}

	@Override
	public ObjectLoader open(AnyObjectId objId
			throws MissingObjectException
			IOException {
		if (lastChunk != null) {
			int pos = lastChunk.findOffset(repo
			if (0 <= pos)
				return lastChunk.read(pos
			lastChunk = null;
		}

		ChunkAndOffset p = getChunk(objId
		ObjectLoader ldr = p.chunk.read(p.offset
		lastChunk = p.chunk;
		return ldr;
	}

	ChunkAndOffset getChunk(AnyObjectId objId
			throws DhtException
		if (lastChunk != null) {
			int pos = lastChunk.findOffset(repo
			if (0 <= pos)
				return new ChunkAndOffset(lastChunk
			lastChunk = null;
		}

		ChunkKey key;
		if (objId instanceof RefData.IdWithChunk)
			key = ((RefData.IdWithChunk) objId).getChunkKey();
		else
			key = repository.getRefDatabase().find(objId);
		if (key != null) {
			try {
				PackChunk chunk = getChunk(key);
				int pos = chunk.findOffset(repo
				if (0 <= pos)
					return new ChunkAndOffset(chunk
			} catch (DhtMissingChunkException brokenHint) {

			}
		}

		ChunkAndOffset r = ChunkCache.get().find(repo
		if (r != null)
			return r;

		for (ChunkLink link : find(ObjectIndexKey.create(repo
			PackChunk chunk = load(link.getChunkKey());
			if (chunk == null)
				continue;
			if (chunk.hasIndex())
				chunk = ChunkCache.get().put(chunk);
			return new ChunkAndOffset(chunk
		}

		throw missing(objId
	}

	ChunkKey findChunk(AnyObjectId objId) throws DhtException {
		ChunkKey key = repository.getRefDatabase().find(objId);
		if (key != null)
			return key;

		if (lastChunk != null && 0 <= lastChunk.findOffset(repo
			return lastChunk.getChunkKey();

		ChunkAndOffset r = ChunkCache.get().find(repo
		if (r != null)
			return r.chunk.getChunkKey();

		for (ChunkLink link : find(ObjectIndexKey.create(repo
			return link.getChunkKey();
		return null;
	}

	static MissingObjectException missing(AnyObjectId objId
		if (typeHint == OBJ_ANY)
			return new MissingObjectException(objId.copy()
					DhtText.get().objectTypeUnknown);
		else
			return new MissingObjectException(objId.copy()
	}

	PackChunk getChunk(ChunkKey key) throws DhtException {
		if (lastChunk != null && key.equals(lastChunk.getChunkKey()))
			return lastChunk;

		PackChunk chunk = ChunkCache.get().get(key);
		if (chunk != null)
			return chunk;

		chunk = load(key);
		if (chunk != null) {
			if (chunk.hasIndex())
				return ChunkCache.get().put(chunk);
			return chunk;
		}

		throw new DhtMissingChunkException(key);
	}

	@Override
	public Collection<ObjectId> resolve(AbbreviatedObjectId id)
			throws IOException {
		throw new DhtException.TODO("resolve abbreviations");
	}

	public DhtObjectToPack newObjectToPack(RevObject obj) {
		return new DhtObjectToPack(obj);
	}

	@SuppressWarnings("unchecked")
	public void selectObjectRepresentation(PackWriter packer
			ProgressMonitor monitor
			throws IOException
		Iterable itr = objects;
		new RepresentationSelector(packer
	}

	public void writeObjects(PackOutputStream out
			throws IOException {
		for (ObjectToPack otp : list)
			out.writeObject(otp);
	}

	public void copyObjectAsIs(PackOutputStream out
			throws IOException
		try {
			DhtObjectToPack obj = (DhtObjectToPack) otp;
			getChunk(obj.chunk).copyAsIs(out
		} catch (DhtMissingChunkException missingChunk) {
			throw new StoredObjectRepresentationNotAvailableException(otp);
		}
	}

	private List<ChunkLink> find(ObjectIndexKey idxKey) throws DhtException {
		Sync<Map<ObjectIndexKey
		db.objectIndex().get(Collections.singleton(idxKey)
		try {
			Collection<ChunkLink> m;

			m = sync.get(options.getTimeout()).get(idxKey);
			if (m == null || m.isEmpty())
				return Collections.emptyList();

			List<ChunkLink> t = new ArrayList<ChunkLink>(m);
			ChunkLink.sort(t);
			return t;
		} catch (InterruptedException e) {
			throw new DhtTimeoutException(e);
		} catch (TimeoutException e) {
			throw new DhtTimeoutException(e);
		}
	}

	private PackChunk load(ChunkKey chunkKey) throws DhtException {
		Sync<Collection<PackChunk>> sync = Sync.create();
		db.chunks().get(Collections.singleton(chunkKey)
		try {
			return firstOrNull(sync.get(options.getTimeout()));
		} catch (InterruptedException e) {
			throw new DhtTimeoutException(e);
		} catch (TimeoutException e) {
			throw new DhtTimeoutException(e);
		}
	}

	private static <T> T firstOrNull(Collection<T> c) {
		if (c.isEmpty())
			return null;
		if (c instanceof List)
			return ((List<T>) c).get(0);
		return c.iterator().next();
	}

	static class ChunkAndOffset {
		final PackChunk chunk;

		final int offset;

		ChunkAndOffset(PackChunk chunk
			this.chunk = chunk;
			this.offset = offset;
		}
	}
}
