
package org.eclipse.jgit.storage.pack;

import static org.eclipse.jgit.storage.pack.StoredObjectRepresentation.PACK_DELTA;
import static org.eclipse.jgit.storage.pack.StoredObjectRepresentation.PACK_WHOLE;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSubclassMap;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.ThreadSafeProgressMonitor;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.storage.file.PackIndexWriter;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.TemporaryBuffer;

public class PackWriter {
	public static final String COUNTING_OBJECTS_PROGRESS = JGitText.get().countingObjects;

	public static final String COMPRESSING_OBJECTS_PROGRESS = JGitText.get().compressingObjects;

	public static final String WRITING_OBJECTS_PROGRESS = JGitText.get().writingObjects;

	public static final boolean DEFAULT_REUSE_DELTAS = true;

	public static final boolean DEFAULT_REUSE_OBJECTS = true;

	public static final boolean DEFAULT_DELTA_BASE_AS_OFFSET = false;

	public static final int DEFAULT_MAX_DELTA_DEPTH = 50;

	public static final int DEFAULT_DELTA_SEARCH_WINDOW_SIZE = 10;

	static final long DEFAULT_BIG_FILE_THRESHOLD = 50 * 1024 * 1024;

	static final long DEFAULT_DELTA_CACHE_SIZE = 50 * 1024 * 1024;

	static final int DEFAULT_DELTA_CACHE_LIMIT = 100;

	private static final int PACK_VERSION_GENERATED = 2;

	@SuppressWarnings("unchecked")
	private final List<ObjectToPack> objectsLists[] = new List[Constants.OBJ_TAG + 1];
	{
		objectsLists[0] = Collections.<ObjectToPack> emptyList();
		objectsLists[Constants.OBJ_COMMIT] = new ArrayList<ObjectToPack>();
		objectsLists[Constants.OBJ_TREE] = new ArrayList<ObjectToPack>();
		objectsLists[Constants.OBJ_BLOB] = new ArrayList<ObjectToPack>();
		objectsLists[Constants.OBJ_TAG] = new ArrayList<ObjectToPack>();
	}

	private final ObjectIdSubclassMap<ObjectToPack> objectsMap = new ObjectIdSubclassMap<ObjectToPack>();

	private final ObjectIdSubclassMap<ObjectToPack> edgeObjects = new ObjectIdSubclassMap<ObjectToPack>();

	private int compressionLevel;

	private Deflater myDeflater;

	private final ObjectReader reader;

	private final ObjectReuseAsIs reuseSupport;

	private List<ObjectToPack> sortedByName;

	private byte packcsum[];

	private boolean reuseDeltas = DEFAULT_REUSE_DELTAS;

	private boolean reuseObjects = DEFAULT_REUSE_OBJECTS;

	private boolean deltaBaseAsOffset = DEFAULT_DELTA_BASE_AS_OFFSET;

	private boolean deltaCompress = true;

	private int maxDeltaDepth = DEFAULT_MAX_DELTA_DEPTH;

	private int deltaSearchWindowSize = DEFAULT_DELTA_SEARCH_WINDOW_SIZE;

	private long deltaSearchMemoryLimit;

	private long deltaCacheSize = DEFAULT_DELTA_CACHE_SIZE;

	private int deltaCacheLimit = DEFAULT_DELTA_CACHE_LIMIT;

	private int indexVersion;

	private long bigFileThreshold = DEFAULT_BIG_FILE_THRESHOLD;

	private int threads = 1;

	private boolean thin;

	private boolean ignoreMissingUninteresting = true;

	public PackWriter(final Repository repo) {
		this(repo
	}

	public PackWriter(final ObjectReader reader) {
		this(null
	}

	public PackWriter(final Repository repo
		this.reader = reader;
		if (reader instanceof ObjectReuseAsIs)
			reuseSupport = ((ObjectReuseAsIs) reader);
		else
			reuseSupport = null;

		final PackConfig pc = configOf(repo).get(PackConfig.KEY);
		deltaSearchWindowSize = pc.deltaWindow;
		deltaSearchMemoryLimit = pc.deltaWindowMemory;
		deltaCacheSize = pc.deltaCacheSize;
		deltaCacheLimit = pc.deltaCacheLimit;
		maxDeltaDepth = pc.deltaDepth;
		compressionLevel = pc.compression;
		indexVersion = pc.indexVersion;
		bigFileThreshold = pc.bigFileThreshold;
		threads = pc.threads;
	}

	private static Config configOf(final Repository repo) {
		if (repo == null)
			return new Config();
		return repo.getConfig();
	}

	public boolean isReuseDeltas() {
		return reuseDeltas;
	}

	public void setReuseDeltas(boolean reuseDeltas) {
		this.reuseDeltas = reuseDeltas;
	}

	public boolean isReuseObjects() {
		return reuseObjects;
	}

	public void setReuseObjects(boolean reuseObjects) {
		this.reuseObjects = reuseObjects;
	}

	public boolean isDeltaBaseAsOffset() {
		return deltaBaseAsOffset;
	}

	public void setDeltaBaseAsOffset(boolean deltaBaseAsOffset) {
		this.deltaBaseAsOffset = deltaBaseAsOffset;
	}

	public boolean isDeltaCompress() {
		return deltaCompress;
	}

	public void setDeltaCompress(boolean deltaCompress) {
		this.deltaCompress = deltaCompress;
	}

	public int getMaxDeltaDepth() {
		return maxDeltaDepth;
	}

	public void setMaxDeltaDepth(int maxDeltaDepth) {
		this.maxDeltaDepth = maxDeltaDepth;
	}

	public int getDeltaSearchWindowSize() {
		return deltaSearchWindowSize;
	}

	public void setDeltaSearchWindowSize(int objectCount) {
		if (objectCount <= 2)
			setDeltaCompress(false);
		else
			deltaSearchWindowSize = objectCount;
	}

	public long getDeltaSearchMemoryLimit() {
		return deltaSearchMemoryLimit;
	}

	public void setDeltaSearchMemoryLimit(long memoryLimit) {
		deltaSearchMemoryLimit = memoryLimit;
	}

	public long getDeltaCacheSize() {
		return deltaCacheSize;
	}

	public void setDeltaCacheSize(long size) {
		deltaCacheSize = size;
	}

	public int getDeltaCacheLimit() {
		return deltaCacheLimit;
	}

	public void setDeltaCacheLimit(int size) {
		deltaCacheLimit = size;
	}

	public long getBigFileThreshold() {
		return bigFileThreshold;
	}

	public void setBigFileThreshold(long bigFileThreshold) {
		this.bigFileThreshold = bigFileThreshold;
	}

	public int getCompressionLevel() {
		return compressionLevel;
	}

	public void setCompressionLevel(int level) {
		compressionLevel = level;
	}

	public int getThreads() {
		return threads;
	}

	public void setThread(int threads) {
		this.threads = threads;
	}

	public boolean isThin() {
		return thin;
	}

	public void setThin(final boolean packthin) {
		thin = packthin;
	}

	public boolean isIgnoreMissingUninteresting() {
		return ignoreMissingUninteresting;
	}

	public void setIgnoreMissingUninteresting(final boolean ignore) {
		ignoreMissingUninteresting = ignore;
	}

	public void setIndexVersion(final int version) {
		indexVersion = version;
	}

	public int getObjectsNumber() {
		return objectsMap.size();
	}

	public void preparePack(final Iterator<RevObject> objectsSource)
			throws IOException {
		while (objectsSource.hasNext()) {
			addObject(objectsSource.next());
		}
	}

	public void preparePack(ProgressMonitor countingMonitor
			final Collection<? extends ObjectId> interestingObjects
			final Collection<? extends ObjectId> uninterestingObjects)
			throws IOException {
		if (countingMonitor == null)
			countingMonitor = NullProgressMonitor.INSTANCE;
		ObjectWalk walker = setUpWalker(interestingObjects
				uninterestingObjects);
		findObjectsToPack(countingMonitor
	}

	public boolean willInclude(final AnyObjectId id) {
		return objectsMap.get(id) != null;
	}

	public ObjectId computeName() {
		final byte[] buf = new byte[Constants.OBJECT_ID_LENGTH];
		final MessageDigest md = Constants.newMessageDigest();
		for (ObjectToPack otp : sortByName()) {
			otp.copyRawTo(buf
			md.update(buf
		}
		return ObjectId.fromRaw(md.digest());
	}

	public void writeIndex(final OutputStream indexStream) throws IOException {
		final List<ObjectToPack> list = sortByName();
		final PackIndexWriter iw;
		if (indexVersion <= 0)
			iw = PackIndexWriter.createOldestPossible(indexStream
		else
			iw = PackIndexWriter.createVersion(indexStream
		iw.write(list
	}

	private List<ObjectToPack> sortByName() {
		if (sortedByName == null) {
			sortedByName = new ArrayList<ObjectToPack>(objectsMap.size());
			for (List<ObjectToPack> list : objectsLists) {
				for (ObjectToPack otp : list)
					sortedByName.add(otp);
			}
			Collections.sort(sortedByName);
		}
		return sortedByName;
	}

	public void writePack(ProgressMonitor compressMonitor
			ProgressMonitor writeMonitor
			throws IOException {
		if (compressMonitor == null)
			compressMonitor = NullProgressMonitor.INSTANCE;
		if (writeMonitor == null)
			writeMonitor = NullProgressMonitor.INSTANCE;

		if ((reuseDeltas || reuseObjects) && reuseSupport != null)
			searchForReuse();
		if (deltaCompress)
			searchForDeltas(compressMonitor);

		final PackOutputStream out = new PackOutputStream(writeMonitor
				packStream

		writeMonitor.beginTask(WRITING_OBJECTS_PROGRESS
		out.writeFileHeader(PACK_VERSION_GENERATED
		writeObjects(writeMonitor
		writeChecksum(out);

		reader.release();
		writeMonitor.endTask();
	}

	public void release() {
		reader.release();
		if (myDeflater != null) {
			myDeflater.end();
			myDeflater = null;
		}
	}

	private void searchForReuse() throws IOException {
		for (List<ObjectToPack> list : objectsLists) {
			for (ObjectToPack otp : list)
				reuseSupport.selectObjectRepresentation(this
		}
	}

	private void searchForDeltas(ProgressMonitor monitor)
			throws MissingObjectException
			IOException {
		ObjectToPack[] list = new ObjectToPack[
				  objectsLists[Constants.OBJ_TREE].size()
				+ objectsLists[Constants.OBJ_BLOB].size()
				+ edgeObjects.size()];
		int cnt = 0;
		cnt = findObjectsNeedingDelta(list
		cnt = findObjectsNeedingDelta(list
		if (cnt == 0)
			return;

		for (ObjectToPack eo : edgeObjects) {
			try {
				if (loadSize(eo))
					list[cnt++] = eo;
			} catch (IOException notAvailable) {
				if (!ignoreMissingUninteresting)
					throw notAvailable;
			}
		}

		monitor.beginTask(COMPRESSING_OBJECTS_PROGRESS

		Arrays.sort(list
			public int compare(ObjectToPack a
				int cmp = a.getType() - b.getType();
				if (cmp == 0)
					cmp = (a.getPathHash() >>> 1) - (b.getPathHash() >>> 1);
				if (cmp == 0)
					cmp = (a.getPathHash() & 1) - (b.getPathHash() & 1);
				if (cmp == 0)
					cmp = b.getWeight() - a.getWeight();
				return cmp;
			}
		});
		searchForDeltas(monitor
		monitor.endTask();
	}

	private int findObjectsNeedingDelta(ObjectToPack[] list
			throws MissingObjectException
			IOException {
		for (ObjectToPack otp : objectsLists[type]) {
				continue;
				continue;
			if (loadSize(otp))
				list[cnt++] = otp;
		}
		return cnt;
	}

	private boolean loadSize(ObjectToPack e) throws MissingObjectException
			IncorrectObjectTypeException
		long sz = reader.getObjectSize(e

		if (bigFileThreshold <= sz || Integer.MAX_VALUE <= sz)
			return false;

		if (sz <= DeltaIndex.BLKSZ)
			return false;

		e.setWeight((int) sz);
		return true;
	}

	private void searchForDeltas(final ProgressMonitor monitor
			final ObjectToPack[] list
			throws MissingObjectException
			LargeObjectException
		if (threads == 0)
			threads = Runtime.getRuntime().availableProcessors();

		if (threads <= 1 || cnt <= 2 * getDeltaSearchWindowSize()) {
			DeltaCache dc = new DeltaCache(this);
			DeltaWindow dw = new DeltaWindow(this
			dw.search(monitor
			return;
		}

		final List<Throwable> errors = Collections
				.synchronizedList(new ArrayList<Throwable>());
		final DeltaCache dc = new ThreadSafeDeltaCache(this);
		final ProgressMonitor pm = new ThreadSafeProgressMonitor(monitor);
		final ExecutorService pool = Executors.newFixedThreadPool(threads);

		int estSize = cnt / (threads * 2);
		if (estSize < 2 * getDeltaSearchWindowSize())
			estSize = 2 * getDeltaSearchWindowSize();

		for (int i = 0; i < cnt;) {
			final int start = i;
			final int batchSize;

			if (cnt - i < estSize) {
				batchSize = cnt - i;
			} else {
				int end = start + estSize;
				while (end < cnt) {
					ObjectToPack a = list[end - 1];
					ObjectToPack b = list[end];
					if (a.getPathHash() == b.getPathHash())
						end++;
					else
						break;
				}
				batchSize = end - start;
			}
			i += batchSize;

			pool.submit(new Runnable() {
				public void run() {
					try {
						final ObjectReader or = reader.newReader();
						try {
							DeltaWindow dw;
							dw = new DeltaWindow(PackWriter.this
							dw.search(pm
						} finally {
							or.release();
						}
					} catch (Throwable err) {
						errors.add(err);
					}
				}
			});
		}

		pool.shutdown();
		for (;;) {
			try {
				if (pool.awaitTermination(60
					break;
			} catch (InterruptedException e) {
				throw new IOException(
						JGitText.get().packingCancelledDuringObjectsWriting);
			}
		}

		if (!errors.isEmpty()) {
			Throwable err = errors.get(0);
			if (err instanceof Error)
				throw (Error) err;
			if (err instanceof RuntimeException)
				throw (RuntimeException) err;
			if (err instanceof IOException)
				throw (IOException) err;

			IOException fail = new IOException(err.getMessage());
			fail.initCause(err);
			throw fail;
		}
	}

	private void writeObjects(ProgressMonitor writeMonitor
			throws IOException {
		for (List<ObjectToPack> list : objectsLists) {
			for (ObjectToPack otp : list) {
				if (writeMonitor.isCancelled())
					throw new IOException(
							JGitText.get().packingCancelledDuringObjectsWriting);
				if (!otp.isWritten())
					writeObject(out
			}
		}
	}

	private void writeObject(PackOutputStream out
			throws IOException {
		if (otp.isWritten())

		otp.markWantWrite();
		if (otp.isDeltaRepresentation())
			writeBaseFirst(out

		out.resetCRC32();
		otp.setOffset(out.length());

		while (otp.isReuseAsIs()) {
			try {
				reuseSupport.copyObjectAsIs(out
				out.endObject();
				otp.setCRC(out.getCRC32());
				return;
			} catch (StoredObjectRepresentationNotAvailableException gone) {
				if (otp.getOffset() == out.length()) {
					redoSearchForReuse(otp);
					continue;
				} else {
					CorruptObjectException coe;
					coe = new CorruptObjectException(otp
					coe.initCause(gone);
					throw coe;
				}
			}
		}

		if (otp.isDeltaRepresentation())
			writeDeltaObjectDeflate(out
		else
			writeWholeObjectDeflate(out
		out.endObject();
		otp.setCRC(out.getCRC32());
	}

	private void writeBaseFirst(PackOutputStream out
			throws IOException {
		ObjectToPack baseInPack = otp.getDeltaBase();
		if (baseInPack != null) {
			if (!baseInPack.isWritten()) {
				if (baseInPack.wantWrite()) {
					reuseDeltas = false;
					redoSearchForReuse(otp);
					reuseDeltas = true;
				} else {
					writeObject(out
				}
			}
		} else if (!thin) {
			otp.clearDeltaBase();
			otp.clearReuseAsIs();
		}
	}

	private void redoSearchForReuse(final ObjectToPack otp) throws IOException
			MissingObjectException {
		otp.clearDeltaBase();
		otp.clearReuseAsIs();
		reuseSupport.selectObjectRepresentation(this
	}

	private void writeWholeObjectDeflate(PackOutputStream out
			final ObjectToPack otp) throws IOException {
		final Deflater deflater = deflater();
		final ObjectLoader ldr = reader.open(otp

		out.writeHeader(otp

		deflater.reset();
		DeflaterOutputStream dst = new DeflaterOutputStream(out
		ldr.copyTo(dst);
		dst.finish();
	}

	private void writeDeltaObjectDeflate(PackOutputStream out
			final ObjectToPack otp) throws IOException {
		DeltaCache.Ref ref = otp.popCachedDelta();
		if (ref != null) {
			byte[] zbuf = ref.get();
			if (zbuf != null) {
				out.writeHeader(otp
				out.write(zbuf);
				return;
			}
		}

		TemporaryBuffer.Heap delta = delta(otp);
		out.writeHeader(otp

		Deflater deflater = deflater();
		deflater.reset();
		DeflaterOutputStream dst = new DeflaterOutputStream(out
		delta.writeTo(dst
		dst.finish();
	}

	private TemporaryBuffer.Heap delta(final ObjectToPack otp)
			throws IOException {
		DeltaIndex index = new DeltaIndex(buffer(reader
		byte[] res = buffer(reader

		TemporaryBuffer.Heap delta = new TemporaryBuffer.Heap(res.length);
		index.encode(delta
		return delta;
	}

	byte[] buffer(ObjectReader or
		ObjectLoader ldr = or.open(objId);
		if (!ldr.isLarge())
			return ldr.getCachedBytes();


		long sz = ldr.getSize();
		if (getBigFileThreshold() <= sz || Integer.MAX_VALUE < sz)
			throw new LargeObjectException(objId.copy());

		byte[] buf;
		try {
			buf = new byte[(int) sz];
		} catch (OutOfMemoryError noMemory) {
			LargeObjectException e;

			e = new LargeObjectException(objId.copy());
			e.initCause(noMemory);
			throw e;
		}
		InputStream in = ldr.openStream();
		try {
			IO.readFully(in
		} finally {
			in.close();
		}
		return buf;
	}

	private Deflater deflater() {
		if (myDeflater == null)
			myDeflater = new Deflater(compressionLevel);
		return myDeflater;
	}

	private void writeChecksum(PackOutputStream out) throws IOException {
		packcsum = out.getDigest();
		out.write(packcsum);
	}

	private ObjectWalk setUpWalker(
			final Collection<? extends ObjectId> interestingObjects
			final Collection<? extends ObjectId> uninterestingObjects)
			throws MissingObjectException
			IncorrectObjectTypeException {
		final ObjectWalk walker = new ObjectWalk(reader);
		walker.setRetainBody(false);
		walker.sort(RevSort.COMMIT_TIME_DESC);
		if (thin)
			walker.sort(RevSort.BOUNDARY

		for (ObjectId id : interestingObjects) {
			RevObject o = walker.parseAny(id);
			walker.markStart(o);
		}
		if (uninterestingObjects != null) {
			for (ObjectId id : uninterestingObjects) {
				final RevObject o;
				try {
					o = walker.parseAny(id);
				} catch (MissingObjectException x) {
					if (ignoreMissingUninteresting)
						continue;
					throw x;
				}
				walker.markUninteresting(o);
			}
		}
		return walker;
	}

	private void findObjectsToPack(final ProgressMonitor countingMonitor
			final ObjectWalk walker) throws MissingObjectException
			IncorrectObjectTypeException
		countingMonitor.beginTask(COUNTING_OBJECTS_PROGRESS
				ProgressMonitor.UNKNOWN);
		RevObject o;

		while ((o = walker.next()) != null) {
			addObject(o
			countingMonitor.update(1);
		}
		while ((o = walker.nextObject()) != null) {
			addObject(o
			countingMonitor.update(1);
		}
		countingMonitor.endTask();
	}

	public void addObject(final RevObject object)
			throws IncorrectObjectTypeException {
		addObject(object
	}

	private void addObject(final RevObject object
			throws IncorrectObjectTypeException {
		if (object.has(RevFlag.UNINTERESTING)) {
			switch (object.getType()) {
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
				ObjectToPack otp = new ObjectToPack(object);
				otp.setPathHash(pathHashCode);
				otp.setDoNotDelta(true);
				edgeObjects.add(otp);
				thin = true;
				break;
			}
			return;
		}

		final ObjectToPack otp;
		if (reuseSupport != null)
			otp = reuseSupport.newObjectToPack(object);
		else
			otp = new ObjectToPack(object);
		otp.setPathHash(pathHashCode);

		try {
			objectsLists[object.getType()].add(otp);
		} catch (ArrayIndexOutOfBoundsException x) {
			throw new IncorrectObjectTypeException(object
					JGitText.get().incorrectObjectType_COMMITnorTREEnorBLOBnorTAG);
		} catch (UnsupportedOperationException x) {
			throw new IncorrectObjectTypeException(object
					JGitText.get().incorrectObjectType_COMMITnorTREEnorBLOBnorTAG);
		}
		objectsMap.add(otp);
	}

	public void select(ObjectToPack otp
		int nFmt = next.getFormat();
		int nWeight;
		if (otp.isReuseAsIs()) {
			if (PACK_WHOLE < nFmt)
			else if (PACK_DELTA < nFmt && otp.isDeltaRepresentation())

			nWeight = next.getWeight();
			if (otp.getWeight() <= nWeight)
		} else
			nWeight = next.getWeight();

		if (nFmt == PACK_DELTA && reuseDeltas) {
			ObjectId baseId = next.getDeltaBase();
			ObjectToPack ptr = objectsMap.get(baseId);
			if (ptr != null) {
				otp.setDeltaBase(ptr);
				otp.setReuseAsIs();
				otp.setWeight(nWeight);
			} else if (thin && edgeObjects.contains(baseId)) {
				otp.setDeltaBase(baseId);
				otp.setReuseAsIs();
				otp.setWeight(nWeight);
			} else {
				otp.clearDeltaBase();
				otp.clearReuseAsIs();
			}
		} else if (nFmt == PACK_WHOLE && reuseObjects) {
			otp.clearDeltaBase();
			otp.setReuseAsIs();
			otp.setWeight(nWeight);
		} else {
			otp.clearDeltaBase();
			otp.clearReuseAsIs();
		}

		otp.select(next);
	}
}
