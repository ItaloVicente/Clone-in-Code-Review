
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
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
import org.eclipse.jgit.storage.dht.spi.ObjectIndexTable;
import org.eclipse.jgit.storage.pack.CachedPack;
import org.eclipse.jgit.storage.pack.ObjectReuseAsIs;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.PackOutputStream;
import org.eclipse.jgit.storage.pack.PackWriter;

public class DhtReader extends ObjectReader implements ObjectReuseAsIs {
	private final DhtRepository repository;

	private final RepositoryKey repo;

	private final Database db;

	private final DhtReaderOptions readerOptions;

	private final DhtInserterOptions inserterOptions;

	private final Statistics stats;

	private final RecentInfoCache recentInfo;

	private final RecentChunks recentChunks;

	private final DeltaBaseCache deltaBaseCache;

	private Collection<CachedPack> cachedPacks;

	private Inflater inflater;

	private Prefetcher prefetcher;

	DhtReader(DhtObjDatabase objdb) {
		this.repository = objdb.getRepository();
		this.repo = objdb.getRepository().getRepositoryKey();
		this.db = objdb.getDatabase();
		this.readerOptions = objdb.getReaderOptions();
		this.inserterOptions = objdb.getInserterOptions();

		this.stats = new Statistics();
		this.recentInfo = new RecentInfoCache(getOptions());
		this.recentChunks = new RecentChunks(this);
		this.deltaBaseCache = new DeltaBaseCache(this);
	}

	public Statistics getStatistics() {
		return stats;
	}

	Database getDatabase() {
		return db;
	}

	RepositoryKey getRepositoryKey() {
		return repo;
	}

	DhtReaderOptions getOptions() {
		return readerOptions;
	}

	DhtInserterOptions getInserterOptions() {
		return inserterOptions;
	}

	RecentInfoCache getRecentInfoCache() {
		return recentInfo;
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
		return new DhtReader(repository.getObjectDatabase());
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

		return !find(objId).isEmpty();
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
		return new OpenQueue<T>(this
	}

	@Override
	public long getObjectSize(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		for (ObjectInfo info : find(objectId))
			return info.getSize();
		throw missing(objectId
	}

	@Override
	public <T extends ObjectId> AsyncObjectSizeQueue<T> getObjectSize(
			Iterable<T> objectIds
		return new SizeQueue<T>(this
	}

	@Override
	public void walkAdviceBeginCommits(RevWalk rw
			throws IOException {
		endPrefetch();

		Prefetcher p = new Prefetcher(this
		p.push(this
		prefetcher = p;
	}

	@Override
	public void walkAdviceBeginTrees(ObjectWalk ow
			throws IOException {
		endPrefetch();

		if (min != null) {
			Prefetcher p = new Prefetcher(this
			p.push(this
			prefetcher = p;
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

		if (prefetcher != null) {
			r = prefetcher.find(repo
			if (r != null)
				return r;
		}

		for (ObjectInfo link : find(objId)) {
			PackChunk chunk;

			if (prefetcher != null) {
				chunk = prefetcher.get(link.getChunkKey());
				if (chunk == null) {
					chunk = load(link.getChunkKey());
					if (chunk == null)
						continue;
					if (prefetcher.isType(typeHint))
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

		for (ObjectInfo link : find(objId))
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
		prefetcher = null;
	}

	@SuppressWarnings("unchecked")
	public void writeObjects(PackOutputStream out
			throws IOException {
		prefetcher = new Prefetcher(this
		prefetcher.setCacheLoadedChunks(false);

		List itr = objects;
		new ObjectWriter(prefetcher).writeObjects(out
	}

	public void copyObjectAsIs(PackOutputStream out
			boolean validate) throws IOException
			StoredObjectRepresentationNotAvailableException {
		DhtObjectToPack obj = (DhtObjectToPack) otp;
		try {
			PackChunk chunk = recentChunks.get(obj.chunk);
			if (chunk == null) {
				chunk = prefetcher.get(obj.chunk);
				if (chunk == null) {
					stats.access(obj.chunk).cntCopyObjectAsIs_PrefetchMiss++;
					chunk = getChunk(obj.chunk);
				}
				if (!chunk.isFragment())
					recentChunk(chunk);
			}
			chunk.copyObjectAsIs(out
		} catch (DhtMissingChunkException missingChunk) {
			stats.access(missingChunk.getChunkKey()).cntCopyObjectAsIs_InvalidChunk++;
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
				packs.add(new DhtCachedPack(this
			cachedPacks = packs;
		}
		return cachedPacks;
	}

	public void copyPackAsIs(PackOutputStream out
			boolean validate) throws IOException {
		((DhtCachedPack) pack).copyAsIs(out
	}

	private List<ObjectInfo> find(AnyObjectId obj) throws DhtException {
		List<ObjectInfo> info = recentInfo.get(obj);
		if (info != null)
			return info;

		stats.cntObjectIndex_Load++;
		ObjectIndexKey idxKey = ObjectIndexKey.create(repo
		Context opt = Context.READ_REPAIR;
		Sync<Map<ObjectIndexKey
		db.objectIndex().get(opt
		try {
			Collection<ObjectInfo> m;

			m = sync.get(getOptions().getTimeout()).get(idxKey);
			if (m == null || m.isEmpty())
				return Collections.emptyList();

			info = new ArrayList<ObjectInfo>(m);
			ObjectInfo.sort(info);
			recentInfo.put(obj
			return info;
		} catch (InterruptedException e) {
			throw new DhtTimeoutException(e);
		} catch (TimeoutException e) {
			throw new DhtTimeoutException(e);
		}
	}

	private PackChunk load(ChunkKey chunkKey) throws DhtException {
		if (0 == stats.access(chunkKey).cntReader_Load++)
			stats.access(chunkKey).locReader_Load = new Throwable("first");
		Context opt = Context.READ_REPAIR;
		Sync<Collection<PackChunk.Members>> sync = Sync.create();
		db.chunk().get(opt
		try {
			Collection<PackChunk.Members> c = sync.get(getOptions()
					.getTimeout());
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

	public static class Statistics {
		private final Map<ChunkKey

		ChunkAccess access(ChunkKey chunkKey) {
			ChunkAccess ca = chunkAccess.get(chunkKey);
			if (ca == null) {
				ca = new ChunkAccess(chunkKey);
				chunkAccess.put(chunkKey
			}
			return ca;
		}

		public int cntObjectIndex_Load;

		int recentChunks_Hits;

		int recentChunks_Miss;

		int deltaBaseCache_Hits;

		int deltaBaseCache_Miss;

		public double getRecentChunksHitRatio() {
			int total = recentChunks_Hits + recentChunks_Miss;
			return ((double) recentChunks_Hits) / total;
		}

		public double getDeltaBaseCacheHitRatio() {
			int total = deltaBaseCache_Hits + deltaBaseCache_Miss;
			return ((double) deltaBaseCache_Hits) / total;
		}

		public Collection<ChunkAccess> getChunkAccess() {
			return chunkAccess.values();
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append("DhtReader.Statistics:\n");
			b.append(" ");
			if (recentChunks_Hits != 0 || recentChunks_Miss != 0)
				ratio(b
			if (deltaBaseCache_Hits != 0 || deltaBaseCache_Miss != 0)
				ratio(b
			appendFields(this
			b.append("\n");
			for (ChunkAccess ca : getChunkAccess()) {
				b.append("  ");
				b.append(ca.toString());
				b.append("\n");
			}
			return b.toString();
		}

		@SuppressWarnings("boxing")
		static void ratio(StringBuilder b
			b.append(String.format(" %s=%.2f%%"
		}

		static void appendFields(Object obj
			try {
				for (Field field : obj.getClass().getDeclaredFields()) {
					String n = field.getName();

					if (field.getType() == Integer.TYPE
							&& (field.getModifiers() & Modifier.PUBLIC) != 0) {
						int v = field.getInt(obj);
						if (0 < v)
							b.append(' ').append(n).append('=').append(v);
					}
				}
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		public static final class ChunkAccess {
			public final ChunkKey chunkKey;

			public int cntReader_Load;

			Throwable locReader_Load;

			public int cntPrefetcher_Load;

			public int cntPrefetcher_ChunkCacheHit;

			public int cntPrefetcher_OutOfOrder;

			public int cntPrefetcher_WaitedForLoad;

			public int cntPrefetcher_Revisited;

			public int cntCopyObjectAsIs_PrefetchMiss;

			public int cntCopyObjectAsIs_InvalidChunk;

			ChunkAccess(ChunkKey key) {
				chunkKey = key;
			}

			@Override
			public String toString() {
				StringBuilder b = new StringBuilder();
				b.append(chunkKey).append('[');
				appendFields(this
				b.append(" ]");
				if (locReader_Load != null) {
					StringWriter sw = new StringWriter();
					locReader_Load.printStackTrace(new PrintWriter(sw));
					b.append(sw);
				}
				return b.toString();
			}
		}
	}
}
