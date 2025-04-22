
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

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
import org.eclipse.jgit.lib.AsyncObjectLoaderQueue;
import org.eclipse.jgit.lib.AsyncObjectSizeQueue;
import org.eclipse.jgit.lib.InflaterCache;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.dht.RefData.IdWithChunk;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.pack.CachedPack;
import org.eclipse.jgit.storage.pack.ObjectReuseAsIs;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.PackOutputStream;
import org.eclipse.jgit.storage.pack.PackWriter;

class DhtReader extends ObjectReader implements ObjectReuseAsIs {
	private final DhtRepository repository;

	private final RepositoryKey repo;

	private final Database db;

	private final DhtReaderOptions options;

	private final RecentChunks recentChunks;

	private final DeltaBaseCache deltaBaseCache;

	private Collection<CachedPack> cachedPacks;

	private Inflater inflater;

	private Prefetcher prefetcher;

	DhtReader(DhtRepository repository
		this.repository = repository;
		this.repo = repo;
		this.db = db;
		this.options = DhtReaderOptions.DEFAULT;

		this.recentChunks = new RecentChunks(this);
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
		recentChunks.clear();
		endPrefetch();

		InflaterCache.release(inflater);
		inflater = null;

		super.release();
	}

	@Override
	public ObjectReader newReader() {
		return new DhtReader(repository
	}

	@Override
	public boolean has(AnyObjectId objId
		if (objId instanceof RefData.IdWithChunk)
			return true;

		if (recentChunks.has(repo
			return true;

		if (repository.getRefDatabase().findChunk(objId) != null)
			return true;

		if (ChunkCache.get().find(repo
			return true;

		return !find(ObjectIndexKey.create(repo
	}

	@Override
	public ObjectLoader open(AnyObjectId objId
			throws MissingObjectException
			IOException {
		ObjectLoader ldr = recentChunks.open(repo
		if (ldr != null)
			return ldr;

		ChunkAndOffset p = getChunk(objId
		ldr = PackChunk.read(p.chunk
		recentChunk(p.chunk);
		return ldr;
	}

	@Override
	public <T extends ObjectId> AsyncObjectLoaderQueue<T> open(
			Iterable<T> objectIds
		return new OpenQueue<T>(repo
	}

	@Override
	public long getObjectSize(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		for (ObjectInfo info : find(ObjectIndexKey.create(repo
			return info.getSize();
		throw missing(objectId
	}

	@Override
	public <T extends ObjectId> AsyncObjectSizeQueue<T> getObjectSize(
			Iterable<T> objectIds
		return new SizeQueue<T>(repo
	}

	@Override
	public void walkAdviceBeginCommits(RevWalk walk
			throws IOException {
		endPrefetch();

		prefetcher = new Prefetcher(db
		prefetcher.push(this
	}

	@Override
	public void walkAdviceBeginTrees(ObjectWalk ow
			throws IOException {
		endPrefetch();

		if (min != null && min.getTree() != null) {
			prefetcher = new Prefetcher(db
			prefetcher.push(this
		}
	}

	@Override
	public void walkAdviceEnd() {
		endPrefetch();
	}

	void recentChunk(PackChunk chunk) {
		recentChunks.put(chunk);
	}

	ChunkAndOffset getChunk(AnyObjectId objId
			throws DhtException
		return getChunk(objId
	}

	ChunkAndOffset getChunkGently(AnyObjectId objId
			throws DhtException
		return getChunk(objId
	}

	private ChunkAndOffset getChunk(AnyObjectId objId
			boolean loadIfRequired
			MissingObjectException {
		if (checkRecent) {
			ChunkAndOffset r = recentChunks.find(repo
			if (r != null)
				return r;
		}

		ChunkKey key;
		if (objId instanceof RefData.IdWithChunk)
			key = ((RefData.IdWithChunk) objId).getChunkKey();
		else
			key = repository.getRefDatabase().findChunk(objId);
		if (key != null) {
			PackChunk chunk = ChunkCache.get().get(key);
			if (chunk != null) {
				int pos = chunk.findOffset(repo
				if (0 <= pos)
					return new ChunkAndOffset(chunk
			}

			if (loadIfRequired) {
				chunk = load(key);
				if (chunk != null && chunk.hasIndex()) {
					int pos = chunk.findOffset(repo
					if (0 <= pos) {
						chunk = ChunkCache.get().put(chunk);
						return new ChunkAndOffset(chunk
					}
				}
			}

		}

		ChunkAndOffset r = ChunkCache.get().find(repo
		if (r != null)
			return r;

		if (!loadIfRequired)
			return null;

		if (prefetcher != null && prefetcher.isType(typeHint)) {
			r = prefetcher.find(repo
			if (r != null)
				return r;
		}

		for (ObjectInfo link : find(ObjectIndexKey.create(repo
			PackChunk chunk;

			if (prefetcher != null && prefetcher.isType(typeHint)) {
				chunk = prefetcher.get(link.getChunkKey());
				if (chunk == null) {
					chunk = load(link.getChunkKey());
					if (chunk == null)
						continue;
					prefetcher.push(chunk.getMeta());
				}
			} else {
				chunk = load(link.getChunkKey());
				if (chunk == null)
					continue;
			}

			if (chunk.hasIndex())
				chunk = ChunkCache.get().put(chunk);
			return new ChunkAndOffset(chunk
		}

		throw missing(objId
	}

	ChunkKey findChunk(AnyObjectId objId) throws DhtException {
		if (objId instanceof IdWithChunk)
			return ((IdWithChunk) objId).getChunkKey();

		ChunkKey key = repository.getRefDatabase().findChunk(objId);
		if (key != null)
			return key;

		ChunkAndOffset r = recentChunks.find(repo
		if (r != null)
			return r.chunk.getChunkKey();

		r = ChunkCache.get().find(repo
		if (r != null)
			return r.chunk.getChunkKey();

		for (ObjectInfo link : find(ObjectIndexKey.create(repo
			return link.getChunkKey();

		return null;
	}

	static MissingObjectException missing(AnyObjectId objId
		ObjectId id = objId.copy();
		if (typeHint != OBJ_ANY)
			return new MissingObjectException(id
		return new MissingObjectException(id
	}

	PackChunk getChunk(ChunkKey key) throws DhtException {
		PackChunk chunk = recentChunks.get(key);
		if (chunk != null)
			return chunk;

		chunk = ChunkCache.get().get(key);
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
		if (id.length() < 4)
			return Collections.emptySet();

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

	private void endPrefetch() {
		if (prefetcher != null) {
			prefetcher.cancel();
			prefetcher = null;
		}
	}

	@SuppressWarnings("unchecked")
	public void writeObjects(PackOutputStream out
			throws IOException {
		Prefetcher p = new Prefetcher(db
		try {
			List itr = objects;
			new ObjectWriter(p).writeObjects(out
		} finally {
			p.cancel();
		}
	}

	public void copyObjectAsIs(PackOutputStream out
			throws IOException
		try {
			DhtObjectToPack obj = (DhtObjectToPack) otp;
			PackChunk.copyAsIs(getChunk(obj.chunk)
		} catch (DhtMissingChunkException missingChunk) {
			throw new StoredObjectRepresentationNotAvailableException(otp);
		}
	}

	public Collection<CachedPack> getCachedPacks() throws IOException {
		if (cachedPacks == null) {
			Collection<CachedPackInfo> info;
			Collection<CachedPack> packs;

			try {
				info = db.repository().getCachedPacks(repo);
			} catch (TimeoutException e) {
				throw new DhtTimeoutException(e);
			}

			packs = new ArrayList<CachedPack>(info.size());
			for (CachedPackInfo i : info)
				packs.add(new DhtCachedPack(i));
			cachedPacks = packs;
		}
		return cachedPacks;
	}

	public void copyPackAsIs(PackOutputStream out
			throws IOException {
		Prefetcher p = new Prefetcher(db
		try {
			p.setCacheLoadedChunks(false);
			((DhtCachedPack) pack).copyAsIs(out
		} finally {
			p.cancel();
		}
	}

	private List<ObjectInfo> find(ObjectIndexKey idxKey) throws DhtException {
		Context opt = Context.READ_REPAIR;
		Sync<Map<ObjectIndexKey
		db.objectIndex().get(opt
		try {
			Collection<ObjectInfo> m;

			m = sync.get(options.getTimeout()).get(idxKey);
			if (m == null || m.isEmpty())
				return Collections.emptyList();

			List<ObjectInfo> t = new ArrayList<ObjectInfo>(m);
			ObjectInfo.sort(t);
			return t;
		} catch (InterruptedException e) {
			throw new DhtTimeoutException(e);
		} catch (TimeoutException e) {
			throw new DhtTimeoutException(e);
		}
	}

	private PackChunk load(ChunkKey chunkKey) throws DhtException {
		Context opt = Context.READ_REPAIR;
		Sync<Collection<PackChunk.Members>> sync = Sync.create();
		db.chunk().get(opt
		try {
			Collection<PackChunk.Members> c = sync.get(options.getTimeout());
			if (c.isEmpty())
				return null;
			if (c instanceof List)
				return ((List<PackChunk.Members>) c).get(0).build();
			return c.iterator().next().build();
		} catch (InterruptedException e) {
			throw new DhtTimeoutException(e);
		} catch (TimeoutException e) {
			throw new DhtTimeoutException(e);
		}
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
