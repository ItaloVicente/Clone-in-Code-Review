
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.eclipse.jgit.revwalk.ObjectListIterator;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.dht.spi.Context;
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

	private Collection<ObjectListInfo> objectLists;

	private Inflater inflater;

	private PackChunk lastChunk;

	private Prefetcher prefetcher;

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
		walkAdviceEnd();

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

	@Override
	public void walkAdviceBeginCommits(RevWalk walk
			throws IOException {
		walkAdviceEnd();

		prefetcher = new Prefetcher(db
		prefetcher.push(this
	}

	@Override
	public void walkAdviceBeginTrees(ObjectWalk ow
			throws IOException {
		walkAdviceEnd();

		prefetcher = new Prefetcher(db
		if (min != null && min.getTree() != null)
			prefetcher.push(this
	}

	@Override
	public void walkAdviceEnd() {
		if (prefetcher != null) {
			prefetcher.cancel();
			prefetcher = null;
		}
	}

	@Override
	public Set<ObjectId> getAvailableObjectLists() {
		if (objectLists == null) {
			try {
				objectLists = db.repository().getObjectLists(repo);
			} catch (DhtException e) {
				return Collections.emptySet();
			} catch (TimeoutException e) {
				return Collections.emptySet();
			}
		}

		Set<ObjectId> r = new HashSet<ObjectId>();
		for (ObjectListInfo info : objectLists)
			r.add(info.getStartingCommit());
		return r;
	}

	@Override
	public ObjectListIterator openObjectList(AnyObjectId listName
			ObjectWalk walker) throws IOException {
		for (ObjectListInfo info : objectLists) {
			if (info.getStartingCommit().equals(listName))
				return new DhtObjectListIterator(db
		}
		throw new DhtException(MessageFormat.format(
				DhtText.get().missingObjectList
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
			boolean loadIfRequired) throws DhtException
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
					if (prefetcher != null && prefetcher.isType(typeHint))
						prefetcher.push(chunk.getPrefetch());
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
		ChunkKey key = repository.getRefDatabase().find(objId);
		if (key != null)
			return key;

		if (lastChunk != null && 0 <= lastChunk.findOffset(repo
			return lastChunk.getChunkKey();

		ChunkAndOffset r = ChunkCache.get().find(repo
		if (r != null)
			return r.chunk.getChunkKey();

		for (ObjectInfo link : find(ObjectIndexKey.create(repo
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

	Prefetcher beginPrefetch() {
		walkAdviceEnd();
		prefetcher = new Prefetcher(db
		return prefetcher;
	}

	@SuppressWarnings("unchecked")
	public void writeObjects(PackOutputStream out
			throws IOException {
		List itr = objects;
		new ObjectWriter(this).writeObjects(out
		walkAdviceEnd();
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
