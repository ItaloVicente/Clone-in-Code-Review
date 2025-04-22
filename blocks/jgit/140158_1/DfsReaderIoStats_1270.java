
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.UNREACHABLE_GARBAGE;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackList;
import org.eclipse.jgit.internal.storage.file.BitmapIndexImpl;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndex;
import org.eclipse.jgit.internal.storage.file.PackIndex;
import org.eclipse.jgit.internal.storage.file.PackReverseIndex;
import org.eclipse.jgit.internal.storage.pack.CachedPack;
import org.eclipse.jgit.internal.storage.pack.ObjectReuseAsIs;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackOutputStream;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.AsyncObjectLoaderQueue;
import org.eclipse.jgit.lib.AsyncObjectSizeQueue;
import org.eclipse.jgit.lib.BitmapIndex;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.InflaterCache;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.util.BlockList;

public class DfsReader extends ObjectReader implements ObjectReuseAsIs {
	private static final int MAX_RESOLVE_MATCHES = 256;

	final byte[] tempId = new byte[OBJECT_ID_LENGTH];

	final DfsObjDatabase db;

	final DfsReaderIoStats.Accumulator stats = new DfsReaderIoStats.Accumulator();

	private Inflater inf;
	private DfsBlock block;
	private DeltaBaseCache baseCache;
	private DfsPackFile last;
	private boolean avoidUnreachable;

	protected DfsReader(DfsObjDatabase db) {
		this.db = db;
		this.streamFileThreshold = db.getReaderOptions().getStreamFileThreshold();
	}

	DfsReaderOptions getOptions() {
		return db.getReaderOptions();
	}

	DeltaBaseCache getDeltaBaseCache() {
		if (baseCache == null)
			baseCache = new DeltaBaseCache(this);
		return baseCache;
	}

	@Override
	public ObjectReader newReader() {
		return db.newReader();
	}

	@Override
	public void setAvoidUnreachableObjects(boolean avoid) {
		avoidUnreachable = avoid;
	}

	@Override
	public BitmapIndex getBitmapIndex() throws IOException {
		for (DfsPackFile pack : db.getPacks()) {
			PackBitmapIndex bitmapIndex = pack.getBitmapIndex(this);
			if (bitmapIndex != null)
				return new BitmapIndexImpl(bitmapIndex);
		}
		return null;
	}

	@Override
	public Collection<CachedPack> getCachedPacksAndUpdate(
		BitmapBuilder needBitmap) throws IOException {
		for (DfsPackFile pack : db.getPacks()) {
			PackBitmapIndex bitmapIndex = pack.getBitmapIndex(this);
			if (needBitmap.removeAllOrNone(bitmapIndex))
				return Collections.<CachedPack> singletonList(
						new DfsCachedPack(pack));
		}
		return Collections.emptyList();
	}

	@Override
	public Collection<ObjectId> resolve(AbbreviatedObjectId id)
			throws IOException {
		if (id.isComplete())
			return Collections.singleton(id.toObjectId());
		HashSet<ObjectId> matches = new HashSet<>(4);
		PackList packList = db.getPackList();
		resolveImpl(packList
		if (matches.size() < MAX_RESOLVE_MATCHES && packList.dirty()) {
			stats.scanPacks++;
			resolveImpl(db.scanPacks(packList)
		}
		return matches;
	}

	private void resolveImpl(PackList packList
			HashSet<ObjectId> matches) throws IOException {
		for (DfsPackFile pack : packList.packs) {
			if (skipGarbagePack(pack)) {
				continue;
			}
			pack.resolve(this
			if (matches.size() >= MAX_RESOLVE_MATCHES) {
				break;
			}
		}
	}

	@Override
	public boolean has(AnyObjectId objectId) throws IOException {
		if (last != null
				&& !skipGarbagePack(last)
				&& last.hasObject(this
			return true;
		PackList packList = db.getPackList();
		if (hasImpl(packList
			return true;
		} else if (packList.dirty()) {
			stats.scanPacks++;
			return hasImpl(db.scanPacks(packList)
		}
		return false;
	}

	private boolean hasImpl(PackList packList
			throws IOException {
		for (DfsPackFile pack : packList.packs) {
			if (pack == last || skipGarbagePack(pack))
				continue;
			if (pack.hasObject(this
				last = pack;
				return true;
			}
		}
		return false;
	}

	@Override
	public ObjectLoader open(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		ObjectLoader ldr;
		if (last != null && !skipGarbagePack(last)) {
			ldr = last.get(this
			if (ldr != null) {
				return checkType(ldr
			}
		}

		PackList packList = db.getPackList();
		ldr = openImpl(packList
		if (ldr != null) {
			return checkType(ldr
		}
		if (packList.dirty()) {
			stats.scanPacks++;
			ldr = openImpl(db.scanPacks(packList)
			if (ldr != null) {
				return checkType(ldr
			}
		}

		if (typeHint == OBJ_ANY)
			throw new MissingObjectException(objectId.copy()
					JGitText.get().unknownObjectType2);
		throw new MissingObjectException(objectId.copy()
	}

	private static ObjectLoader checkType(ObjectLoader ldr
			int typeHint) throws IncorrectObjectTypeException {
		if (typeHint != OBJ_ANY && ldr.getType() != typeHint) {
			throw new IncorrectObjectTypeException(id.copy()
		}
		return ldr;
	}

	private ObjectLoader openImpl(PackList packList
			throws IOException {
		for (DfsPackFile pack : packList.packs) {
			if (pack == last || skipGarbagePack(pack)) {
				continue;
			}
			ObjectLoader ldr = pack.get(this
			if (ldr != null) {
				last = pack;
				return ldr;
			}
		}
		return null;
	}

	@Override
	public Set<ObjectId> getShallowCommits() {
		return Collections.emptySet();
	}

	private static final Comparator<FoundObject<?>> FOUND_OBJECT_SORT = new Comparator<FoundObject<?>>() {
		@Override
		public int compare(FoundObject<?> a
			int cmp = a.packIndex - b.packIndex;
			if (cmp == 0)
				cmp = Long.signum(a.offset - b.offset);
			return cmp;
		}
	};

	private static class FoundObject<T extends ObjectId> {
		final T id;
		final DfsPackFile pack;
		final long offset;
		final int packIndex;

		FoundObject(T objectId
			this.id = objectId;
			this.pack = pack;
			this.offset = offset;
			this.packIndex = packIdx;
		}

		FoundObject(T objectId) {
			this.id = objectId;
			this.pack = null;
			this.offset = 0;
			this.packIndex = 0;
		}
	}

	private <T extends ObjectId> Iterable<FoundObject<T>> findAll(
			Iterable<T> objectIds) throws IOException {
		Collection<T> pending = new LinkedList<>();
		for (T id : objectIds) {
			pending.add(id);
		}

		PackList packList = db.getPackList();
		List<FoundObject<T>> r = new ArrayList<>();
		findAllImpl(packList
		if (!pending.isEmpty() && packList.dirty()) {
			stats.scanPacks++;
			findAllImpl(db.scanPacks(packList)
		}
		for (T t : pending) {
			r.add(new FoundObject<>(t));
		}
		Collections.sort(r
		return r;
	}

	private <T extends ObjectId> void findAllImpl(PackList packList
			Collection<T> pending
		DfsPackFile[] packs = packList.packs;
		if (packs.length == 0) {
			return;
		}
		int lastIdx = 0;
		DfsPackFile lastPack = packs[lastIdx];

		OBJECT_SCAN: for (Iterator<T> it = pending.iterator(); it.hasNext();) {
			T t = it.next();
			if (!skipGarbagePack(lastPack)) {
				try {
					long p = lastPack.findOffset(this
					if (0 < p) {
						r.add(new FoundObject<>(t
						it.remove();
						continue;
					}
				} catch (IOException e) {
				}
			}

			for (int i = 0; i < packs.length; i++) {
				if (i == lastIdx)
					continue;
				DfsPackFile pack = packs[i];
				if (skipGarbagePack(pack))
					continue;
				try {
					long p = pack.findOffset(this
					if (0 < p) {
						r.add(new FoundObject<>(t
						it.remove();
						lastIdx = i;
						lastPack = pack;
						continue OBJECT_SCAN;
					}
				} catch (IOException e) {
				}
			}
		}

		last = lastPack;
	}

	private boolean skipGarbagePack(DfsPackFile pack) {
		return avoidUnreachable && pack.isGarbage();
	}

	@Override
	public <T extends ObjectId> AsyncObjectLoaderQueue<T> open(
			Iterable<T> objectIds
		Iterable<FoundObject<T>> order;
		IOException error = null;
		try {
			order = findAll(objectIds);
		} catch (IOException e) {
			order = Collections.emptyList();
			error = e;
		}

		final Iterator<FoundObject<T>> idItr = order.iterator();
		final IOException findAllError = error;
		return new AsyncObjectLoaderQueue<T>() {
			private FoundObject<T> cur;

			@Override
			public boolean next() throws MissingObjectException
				if (idItr.hasNext()) {
					cur = idItr.next();
					return true;
				} else if (findAllError != null) {
					throw findAllError;
				} else {
					return false;
				}
			}

			@Override
			public T getCurrent() {
				return cur.id;
			}

			@Override
			public ObjectId getObjectId() {
				return cur.id;
			}

			@Override
			public ObjectLoader open() throws IOException {
				if (cur.pack == null)
					throw new MissingObjectException(cur.id
							JGitText.get().unknownObjectType2);
				return cur.pack.load(DfsReader.this
			}

			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return true;
			}

			@Override
			public void release() {
			}
		};
	}

	@Override
	public <T extends ObjectId> AsyncObjectSizeQueue<T> getObjectSize(
			Iterable<T> objectIds
		Iterable<FoundObject<T>> order;
		IOException error = null;
		try {
			order = findAll(objectIds);
		} catch (IOException e) {
			order = Collections.emptyList();
			error = e;
		}

		final Iterator<FoundObject<T>> idItr = order.iterator();
		final IOException findAllError = error;
		return new AsyncObjectSizeQueue<T>() {
			private FoundObject<T> cur;
			private long sz;

			@Override
			public boolean next() throws MissingObjectException
				if (idItr.hasNext()) {
					cur = idItr.next();
					if (cur.pack == null)
						throw new MissingObjectException(cur.id
								JGitText.get().unknownObjectType2);
					sz = cur.pack.getObjectSize(DfsReader.this
					return true;
				} else if (findAllError != null) {
					throw findAllError;
				} else {
					return false;
				}
			}

			@Override
			public T getCurrent() {
				return cur.id;
			}

			@Override
			public ObjectId getObjectId() {
				return cur.id;
			}

			@Override
			public long getSize() {
				return sz;
			}

			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return true;
			}

			@Override
			public void release() {
			}
		};
	}

	@Override
	public long getObjectSize(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		if (last != null && !skipGarbagePack(last)) {
			long sz = last.getObjectSize(this
			if (0 <= sz) {
				return sz;
			}
		}

		PackList packList = db.getPackList();
		long sz = getObjectSizeImpl(packList
		if (0 <= sz) {
			return sz;
		}
		if (packList.dirty()) {
			sz = getObjectSizeImpl(packList
			if (0 <= sz) {
				return sz;
			}
		}

		if (typeHint == OBJ_ANY) {
			throw new MissingObjectException(objectId.copy()
					JGitText.get().unknownObjectType2);
		}
		throw new MissingObjectException(objectId.copy()
	}

	private long getObjectSizeImpl(PackList packList
			throws IOException {
		for (DfsPackFile pack : packList.packs) {
			if (pack == last || skipGarbagePack(pack)) {
				continue;
			}
			long sz = pack.getObjectSize(this
			if (0 <= sz) {
				last = pack;
				return sz;
			}
		}
		return -1;
	}

	@Override
	public DfsObjectToPack newObjectToPack(AnyObjectId objectId
		return new DfsObjectToPack(objectId
	}

	private static final Comparator<DfsObjectToPack> OFFSET_SORT = new Comparator<DfsObjectToPack>() {
		@Override
		public int compare(DfsObjectToPack a
			return Long.signum(a.getOffset() - b.getOffset());
		}
	};

	@Override
	public void selectObjectRepresentation(PackWriter packer
			ProgressMonitor monitor
			throws IOException
		List<DfsPackFile> packs = sortPacksForSelectRepresentation();
		trySelectRepresentation(packer

		List<DfsPackFile> garbage = garbagePacksForSelectRepresentation();
		if (!garbage.isEmpty() && checkGarbagePacks(objects)) {
			trySelectRepresentation(packer
		}
	}

	private void trySelectRepresentation(PackWriter packer
			ProgressMonitor monitor
			List<DfsPackFile> packs
		for (DfsPackFile pack : packs) {
			List<DfsObjectToPack> tmp = findAllFromPack(pack
			if (tmp.isEmpty())
				continue;
			Collections.sort(tmp
			PackReverseIndex rev = pack.getReverseIdx(this);
			DfsObjectRepresentation rep = new DfsObjectRepresentation(pack);
			for (DfsObjectToPack otp : tmp) {
				pack.representation(rep
				otp.setOffset(0);
				packer.select(otp
				if (!otp.isFound()) {
					otp.setFound();
					monitor.update(1);
				}
			}
		}
	}

	private static final Comparator<DfsPackFile> PACK_SORT_FOR_REUSE =
		Comparator.comparing(
				DfsPackFile::getPackDescription

	private List<DfsPackFile> sortPacksForSelectRepresentation()
			throws IOException {
		DfsPackFile[] packs = db.getPacks();
		List<DfsPackFile> sorted = new ArrayList<>(packs.length);
		for (DfsPackFile p : packs) {
			if (p.getPackDescription().getPackSource() != UNREACHABLE_GARBAGE) {
				sorted.add(p);
			}
		}
		Collections.sort(sorted
		return sorted;
	}

	private List<DfsPackFile> garbagePacksForSelectRepresentation()
			throws IOException {
		DfsPackFile[] packs = db.getPacks();
		List<DfsPackFile> garbage = new ArrayList<>(packs.length);
		for (DfsPackFile p : packs) {
			if (p.getPackDescription().getPackSource() == UNREACHABLE_GARBAGE) {
				garbage.add(p);
			}
		}
		return garbage;
	}

	private static boolean checkGarbagePacks(Iterable<ObjectToPack> objects) {
		for (ObjectToPack otp : objects) {
			if (!((DfsObjectToPack) otp).isFound()) {
				return true;
			}
		}
		return false;
	}

	private List<DfsObjectToPack> findAllFromPack(DfsPackFile pack
			Iterable<ObjectToPack> objects
					throws IOException {
		List<DfsObjectToPack> tmp = new BlockList<>();
		PackIndex idx = pack.getPackIndex(this);
		for (ObjectToPack obj : objects) {
			DfsObjectToPack otp = (DfsObjectToPack) obj;
			if (skipFound && otp.isFound()) {
				continue;
			}
			long p = idx.findOffset(otp);
			if (0 < p && !pack.isCorrupt(p)) {
				otp.setOffset(p);
				tmp.add(otp);
			}
		}
		return tmp;
	}

	@Override
	public void copyObjectAsIs(PackOutputStream out
			boolean validate) throws IOException
			StoredObjectRepresentationNotAvailableException {
		DfsObjectToPack src = (DfsObjectToPack) otp;
		src.pack.copyAsIs(out
	}

	@Override
	public void writeObjects(PackOutputStream out
			throws IOException {
		for (ObjectToPack otp : list)
			out.writeObject(otp);
	}

	@Override
	public void copyPackAsIs(PackOutputStream out
			throws IOException {
		((DfsCachedPack) pack).copyAsIs(out
	}

	int copy(BlockBasedFile file
			int cnt) throws IOException {
		if (cnt == 0)
			return 0;

		long length = file.length;
		if (0 <= length && length <= position)
			return 0;

		int need = cnt;
		do {
			pin(file
			int r = block.copy(position
			position += r;
			dstoff += r;
			need -= r;
			if (length < 0)
				length = file.length;
		} while (0 < need && position < length);
		return cnt - need;
	}

	int inflate(DfsPackFile pack
			boolean headerOnly) throws IOException
		long start = System.nanoTime();
		prepareInflater();
		pin(pack
		position += block.setInput(position
		for (int dstoff = 0;;) {
			int n = inf.inflate(dstbuf
			dstoff += n;
			if (inf.finished() || (headerOnly && dstoff == dstbuf.length)) {
				stats.inflatedBytes += dstoff;
				stats.inflationMicros += BlockBasedFile.elapsedMicros(start);
				return dstoff;
			} else if (inf.needsInput()) {
				pin(pack
				position += block.setInput(position
			} else if (n == 0)
				throw new DataFormatException();
		}
	}

	DfsBlock quickCopy(DfsPackFile p
			throws IOException {
		pin(p
		if (block.contains(p.key
			return block;
		return null;
	}

	Inflater inflater() {
		prepareInflater();
		return inf;
	}

	private void prepareInflater() {
		if (inf == null)
			inf = InflaterCache.get();
		else
			inf.reset();
	}

	void pin(BlockBasedFile file
		if (block == null || !block.contains(file.key
			block = null;
			block = file.getOrLoadBlock(position
		}
	}

	void unpin() {
		block = null;
	}

	public DfsReaderIoStats getIoStats() {
		return new DfsReaderIoStats(stats);
	}

	@Override
	public void close() {
		last = null;
		block = null;
		baseCache = null;
		try {
			InflaterCache.release(inf);
		} finally {
			inf = null;
		}
	}
}
