
package org.eclipse.jgit.storage.dfs;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.AsyncObjectLoaderQueue;
import org.eclipse.jgit.lib.AsyncObjectSizeQueue;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.InflaterCache;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.CachedPack;
import org.eclipse.jgit.storage.pack.ObjectReuseAsIs;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.PackOutputStream;
import org.eclipse.jgit.storage.pack.PackWriter;
import org.eclipse.jgit.util.BlockList;

final class DfsReader extends ObjectReader implements ObjectReuseAsIs {
	final byte[] tempId = new byte[OBJECT_ID_LENGTH];

	final DfsObjDatabase db;

	private Inflater inf;

	private DfsBlock block;

	private DeltaBaseCache baseCache;

	private DfsPackFile last;

	private boolean wantReadAhead;

	private List<ReadAheadTask.BlockFuture> pendingReadAhead;

	DfsReader(DfsObjDatabase db) {
		this.db = db;
	}

	DfsReaderOptions getOptions() {
		return db.getReaderOptions();
	}

	DeltaBaseCache getDeltaBaseCache() {
		if (baseCache == null)
			baseCache = new DeltaBaseCache(this);
		return baseCache;
	}

	int getStreamFileThreshold() {
		return getOptions().getStreamFileThreshold();
	}

	@Override
	public ObjectReader newReader() {
		return new DfsReader(db);
	}

	@Override
	public Collection<ObjectId> resolve(AbbreviatedObjectId id)
			throws IOException {
		if (id.isComplete())
			return Collections.singleton(id.toObjectId());
		HashSet<ObjectId> matches = new HashSet<ObjectId>(4);
		for (DfsPackFile pack : db.getPacks()) {
			pack.resolve(this
			if (256 <= matches.size())
				break;
		}
		return matches;
	}

	@Override
	public boolean has(AnyObjectId objectId) throws IOException {
		if (last != null && last.hasObject(this
			return true;
		for (DfsPackFile pack : db.getPacks()) {
			if (last == pack)
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
		if (last != null) {
			ObjectLoader ldr = last.get(this
			if (ldr != null)
				return ldr;
		}

		for (DfsPackFile pack : db.getPacks()) {
			if (pack == last)
				continue;
			ObjectLoader ldr = pack.get(this
			if (ldr != null) {
				last = pack;
				return ldr;
			}
		}

		if (typeHint == OBJ_ANY)
			throw new MissingObjectException(objectId.copy()
		throw new MissingObjectException(objectId.copy()
	}

	private static final Comparator<FoundObject<?>> FOUND_OBJECT_SORT = new Comparator<FoundObject<?>>() {
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
		ArrayList<FoundObject<T>> r = new ArrayList<FoundObject<T>>();
		DfsPackFile[] packList = db.getPacks();
		if (packList.length == 0) {
			for (T t : objectIds)
				r.add(new FoundObject<T>(t));
			return r;
		}

		int lastIdx = 0;
		DfsPackFile lastPack = packList[lastIdx];

		OBJECT_SCAN: for (T t : objectIds) {
			try {
				long p = lastPack.findOffset(this
				if (0 < p) {
					r.add(new FoundObject<T>(t
					continue;
				}
			} catch (IOException e) {
			}

			for (int i = 0; i < packList.length; i++) {
				if (i == lastIdx)
					continue;
				DfsPackFile pack = packList[i];
				try {
					long p = pack.findOffset(this
					if (0 < p) {
						r.add(new FoundObject<T>(t
						lastIdx = i;
						lastPack = pack;
						continue OBJECT_SCAN;
					}
				} catch (IOException e) {
				}
			}

			r.add(new FoundObject<T>(t));
		}

		Collections.sort(r
		last = lastPack;
		return r;
	}

	@Override
	public <T extends ObjectId> AsyncObjectLoaderQueue<T> open(
			Iterable<T> objectIds
		wantReadAhead = true;

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

			public boolean next() throws MissingObjectException
				if (idItr.hasNext()) {
					cur = idItr.next();
					return true;
				} else if (findAllError != null) {
					throw findAllError;
				} else {
					cancelReadAhead();
					return false;
				}
			}

			public T getCurrent() {
				return cur.id;
			}

			public ObjectId getObjectId() {
				return cur.id;
			}

			public ObjectLoader open() throws IOException {
				if (cur.pack == null)
					throw new MissingObjectException(cur.id
				return cur.pack.load(DfsReader.this
			}

			public boolean cancel(boolean mayInterruptIfRunning) {
				cancelReadAhead();
				return true;
			}

			public void release() {
				cancelReadAhead();
			}
		};
	}

	@Override
	public <T extends ObjectId> AsyncObjectSizeQueue<T> getObjectSize(
			Iterable<T> objectIds
		wantReadAhead = true;

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

			public boolean next() throws MissingObjectException
				if (idItr.hasNext()) {
					cur = idItr.next();
					if (cur.pack == null)
						throw new MissingObjectException(cur.id
					sz = cur.pack.getObjectSize(DfsReader.this
					return true;
				} else if (findAllError != null) {
					throw findAllError;
				} else {
					cancelReadAhead();
					return false;
				}
			}

			public T getCurrent() {
				return cur.id;
			}

			public ObjectId getObjectId() {
				return cur.id;
			}

			public long getSize() {
				return sz;
			}

			public boolean cancel(boolean mayInterruptIfRunning) {
				cancelReadAhead();
				return true;
			}

			public void release() {
				cancelReadAhead();
			}
		};
	}

	@Override
	public void walkAdviceBeginCommits(RevWalk walk
		wantReadAhead = true;
	}

	@Override
	public void walkAdviceBeginTrees(ObjectWalk ow
		wantReadAhead = true;
	}

	@Override
	public void walkAdviceEnd() {
		cancelReadAhead();
	}

	@Override
	public long getObjectSize(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		if (last != null) {
			long sz = last.getObjectSize(this
			if (0 <= sz)
				return sz;
		}

		for (DfsPackFile pack : db.getPacks()) {
			if (pack == last)
				continue;
			long sz = pack.getObjectSize(this
			if (0 <= sz) {
				last = pack;
				return sz;
			}
		}

		if (typeHint == OBJ_ANY)
			throw new MissingObjectException(objectId.copy()
		throw new MissingObjectException(objectId.copy()
	}

	public DfsObjectToPack newObjectToPack(RevObject obj) {
		return new DfsObjectToPack(obj);
	}

	private static final Comparator<DfsObjectRepresentation> REPRESENTATION_SORT = new Comparator<DfsObjectRepresentation>() {
		public int compare(DfsObjectRepresentation a
			int cmp = a.packIndex - b.packIndex;
			if (cmp == 0)
				cmp = Long.signum(a.offset - b.offset);
			return cmp;
		}
	};

	public void selectObjectRepresentation(PackWriter packer
			ProgressMonitor monitor
			throws IOException
		DfsPackFile[] packList = db.getPacks();
		if (packList.length == 0) {
			Iterator<ObjectToPack> itr = objects.iterator();
			if (itr.hasNext())
				throw new MissingObjectException(itr.next()
			return;
		}

		int packIndex = 0;
		DfsPackFile packLast = packList[packIndex];

		int updated = 0;
		int posted = 0;
		List<DfsObjectRepresentation> all = new BlockList<DfsObjectRepresentation>();
		for (ObjectToPack otp : objects) {
			long p = packLast.findOffset(this
			if (p < 0) {
				int skip = packIndex;
				for (packIndex = 0; packIndex < packList.length; packIndex++) {
					if (skip == packIndex)
						continue;
					packLast = packList[packIndex];
					p = packLast.findOffset(this
					if (0 < p)
						break;
				}
				if (packIndex == packList.length)
					throw new MissingObjectException(otp
			}

			DfsObjectRepresentation r = new DfsObjectRepresentation(otp);
			r.pack = packLast;
			r.packIndex = packIndex;
			r.offset = p;
			all.add(r);

			if ((++updated & 1) == 1) {
				posted++;
			}
		}
		Collections.sort(all

		try {
			wantReadAhead = true;
			for (DfsObjectRepresentation r : all) {
				r.pack.representation(this
				packer.select(r.object
				if ((++updated & 1) == 1) {
					monitor.update(1);
					posted++;
				}
			}
		} finally {
			cancelReadAhead();
		}
		if (posted < all.size())
			monitor.update(all.size() - posted);
	}

	public void copyObjectAsIs(PackOutputStream out
			boolean validate) throws IOException
			StoredObjectRepresentationNotAvailableException {
		DfsObjectToPack src = (DfsObjectToPack) otp;
		src.pack.copyAsIs(out
	}

	private static final Comparator<ObjectToPack> WRITE_SORT = new Comparator<ObjectToPack>() {
		public int compare(ObjectToPack o1
			DfsObjectToPack a = (DfsObjectToPack) o1;
			DfsObjectToPack b = (DfsObjectToPack) o2;
			int cmp = a.packIndex - b.packIndex;
			if (cmp == 0)
				cmp = Long.signum(a.offset - b.offset);
			return cmp;
		}
	};

	public void writeObjects(PackOutputStream out
			throws IOException {
		if (list.isEmpty())
			return;

		switch (list.get(0).getType()) {
		case OBJ_TREE:
		case OBJ_BLOB:
			Collections.sort(list
		}

		try {
			wantReadAhead = true;
			for (ObjectToPack otp : list)
				out.writeObject(otp);
		} finally {
			cancelReadAhead();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<CachedPack> getCachedPacks() throws IOException {
		DfsPackFile[] packList = db.getPacks();
		List<CachedPack> cached = new ArrayList<CachedPack>(packList.length);
		for (DfsPackFile pack : packList) {
			DfsPackDescription desc = pack.getPackDescription();
			if (desc.getTips() == null || desc.getTips().isEmpty())
				continue;
			cached.add(new DfsCachedPack(pack));
		}
		return cached;
	}

	public void copyPackAsIs(PackOutputStream out
			boolean validate) throws IOException {
		try {
			wantReadAhead = true;
			((DfsCachedPack) pack).copyAsIs(out
		} finally {
			cancelReadAhead();
		}
	}

	int copy(DfsPackFile pack
			throws IOException {
		if (cnt == 0)
			return 0;

		long length = pack.length;
		if (0 <= length && length <= position)
			return 0;

		int need = cnt;
		do {
			pin(pack
			int r = block.copy(position
			position += r;
			dstoff += r;
			need -= r;
			if (length < 0)
				length = pack.length;
		} while (0 < need && position < length);
		return cnt - need;
	}

	void copyPackAsIs(DfsPackFile pack
			PackOutputStream out) throws IOException {
		MessageDigest md = null;
		if (validate) {
			md = Constants.newMessageDigest();
			byte[] buf = out.getCopyBuffer();
			pin(pack
			if (block.copy(0
				pack.setInvalid();
				throw new IOException(JGitText.get().packfileIsTruncated);
			}
			md.update(buf
		}

		long position = 12;
		long remaining = length - (12 + 20);
		while (0 < remaining) {
			pin(pack

			int ptr = (int) (position - block.start);
			int n = (int) Math.min(block.size() - ptr
			block.write(out
			position += n;
			remaining -= n;
		}

		if (md != null) {
			byte[] buf = new byte[20];
			byte[] actHash = md.digest();

			pin(pack
			if (block.copy(position
				pack.setInvalid();
				throw new IOException(JGitText.get().packfileIsTruncated);
			}
			if (!Arrays.equals(actHash
				pack.setInvalid();
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileCorruptionDetected
						pack.getPackDescription().getPackName()));
			}
		}
	}

	int inflate(DfsPackFile pack
			boolean headerOnly) throws IOException
		prepareInflater();
		pin(pack
		int dstoff = 0;
		for (;;) {
			dstoff = block.inflate(inf

			if (headerOnly & dstoff == dstbuf.length)
				return dstoff;
			if (inf.needsInput()) {
				position += block.remaining(position);
				pin(pack
			} else if (inf.finished())
				return dstoff;
			else
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

	void pin(DfsPackFile pack
		DfsBlock b = block;
		if (b == null || !b.contains(pack.key
			block = null;

			if (pendingReadAhead != null)
				waitForBlock(pack.key
			block = pack.getOrLoadBlock(position
		}
	}

	boolean wantReadAhead() {
		return wantReadAhead;
	}

	void startedReadAhead(List<ReadAheadTask.BlockFuture> blocks) {
		if (pendingReadAhead == null)
			pendingReadAhead = new LinkedList<ReadAheadTask.BlockFuture>();
		pendingReadAhead.addAll(blocks);
	}

	private void cancelReadAhead() {
		if (pendingReadAhead != null) {
			for (ReadAheadTask.BlockFuture f : pendingReadAhead)
				f.cancel(true);
			pendingReadAhead = null;
		}
		wantReadAhead = false;
	}

	private void waitForBlock(DfsPackKey key
			throws InterruptedIOException {
		Iterator<ReadAheadTask.BlockFuture> itr = pendingReadAhead.iterator();
		while (itr.hasNext()) {
			ReadAheadTask.BlockFuture f = itr.next();
			if (f.contains(key
				try {
					f.get();
				} catch (InterruptedException e) {
					throw new InterruptedIOException();
				} catch (ExecutionException e) {
				}
				itr.remove();
				if (pendingReadAhead.isEmpty())
					pendingReadAhead = null;
				break;
			}
		}
	}

	@Override
	public void release() {
		cancelReadAhead();
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
