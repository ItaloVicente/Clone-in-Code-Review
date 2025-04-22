
package org.eclipse.jgit.internal.storage.pack;

import static java.util.Objects.requireNonNull;
import static org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation.PACK_DELTA;
import static org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation.PACK_WHOLE;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TAG;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndexBuilder;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndexWriterV1;
import org.eclipse.jgit.internal.storage.file.PackIndexWriter;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.AsyncObjectSizeQueue;
import org.eclipse.jgit.lib.BatchingProgressMonitor;
import org.eclipse.jgit.lib.BitmapIndex;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.BitmapObject;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.lib.ObjectIdSet;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.ThreadSafeProgressMonitor;
import org.eclipse.jgit.revwalk.AsyncRevObjectQueue;
import org.eclipse.jgit.revwalk.BitmapWalker;
import org.eclipse.jgit.revwalk.DepthWalk;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.storage.pack.PackStatistics;
import org.eclipse.jgit.transport.FilterSpec;
import org.eclipse.jgit.transport.ObjectCountCallback;
import org.eclipse.jgit.transport.WriteAbortedException;
import org.eclipse.jgit.util.BlockList;
import org.eclipse.jgit.util.TemporaryBuffer;

public class PackWriter implements AutoCloseable {
	private static final int PACK_VERSION_GENERATED = 2;

	public static final Set<ObjectId> NONE = Collections.emptySet();

	private static final Map<WeakReference<PackWriter>
			new ConcurrentHashMap<>();

	private static final Iterable<PackWriter> instancesIterable = new Iterable<PackWriter>() {
		@Override
		public Iterator<PackWriter> iterator() {
			return new Iterator<PackWriter>() {
				private final Iterator<WeakReference<PackWriter>> it =
						instances.keySet().iterator();
				private PackWriter next;

				@Override
				public boolean hasNext() {
					if (next != null)
						return true;
					while (it.hasNext()) {
						WeakReference<PackWriter> ref = it.next();
						next = ref.get();
						if (next != null)
							return true;
						it.remove();
					}
					return false;
				}

				@Override
				public PackWriter next() {
					if (hasNext()) {
						PackWriter result = next;
						next = null;
						return result;
					}
					throw new NoSuchElementException();
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
	};

	public static Iterable<PackWriter> getInstances() {
		return instancesIterable;
	}

	@SuppressWarnings("unchecked")
	BlockList<ObjectToPack> objectsLists[] = new BlockList[OBJ_TAG + 1];
	{
		objectsLists[OBJ_COMMIT] = new BlockList<>();
		objectsLists[OBJ_TREE] = new BlockList<>();
		objectsLists[OBJ_BLOB] = new BlockList<>();
		objectsLists[OBJ_TAG] = new BlockList<>();
	}

	private ObjectIdOwnerMap<ObjectToPack> objectsMap = new ObjectIdOwnerMap<>();

	private List<ObjectToPack> edgeObjects = new BlockList<>();

	private BitmapBuilder haveObjects;

	private List<CachedPack> cachedPacks = new ArrayList<>(2);

	private Set<ObjectId> tagTargets = NONE;

	private Set<? extends ObjectId> excludeFromBitmapSelection = NONE;

	private ObjectIdSet[] excludeInPacks;

	private ObjectIdSet excludeInPackLast;

	private Deflater myDeflater;

	private final ObjectReader reader;

	private final ObjectReuseAsIs reuseSupport;

	final PackConfig config;

	private final PackStatistics.Accumulator stats;

	private final MutableState state;

	private final WeakReference<PackWriter> selfRef;

	private PackStatistics.ObjectType.Accumulator typeStats;

	private List<ObjectToPack> sortedByName;

	private byte packcsum[];

	private boolean deltaBaseAsOffset;

	private boolean reuseDeltas;

	private boolean reuseDeltaCommits;

	private boolean reuseValidate;

	private boolean thin;

	private boolean useCachedPacks;

	private boolean useBitmaps;

	private boolean ignoreMissingUninteresting = true;

	private boolean pruneCurrentObjectList;

	private boolean shallowPack;

	private boolean canBuildBitmaps;

	private boolean indexDisabled;

	private int depth;

	private Collection<? extends ObjectId> unshallowObjects;

	private PackBitmapIndexBuilder writeBitmaps;

	private CRC32 crc32;

	private ObjectCountCallback callback;

	private FilterSpec filterSpec = FilterSpec.NO_FILTER;

	public PackWriter(Repository repo) {
		this(repo
	}

	public PackWriter(ObjectReader reader) {
		this(new PackConfig()
	}

	public PackWriter(Repository repo
		this(new PackConfig(repo)
	}

	public PackWriter(PackConfig config
		this(config
	}

	public PackWriter(PackConfig config
			@Nullable PackStatistics.Accumulator statsAccumulator) {
		this.config = config;
		this.reader = reader;
		if (reader instanceof ObjectReuseAsIs)
			reuseSupport = ((ObjectReuseAsIs) reader);
		else
			reuseSupport = null;

		deltaBaseAsOffset = config.isDeltaBaseAsOffset();
		reuseDeltas = config.isReuseDeltas();
		stats = statsAccumulator != null ? statsAccumulator
				: new PackStatistics.Accumulator();
		state = new MutableState();
		selfRef = new WeakReference<>(this);
		instances.put(selfRef
	}

	public PackWriter setObjectCountCallback(ObjectCountCallback callback) {
		this.callback = callback;
		return this;
	}

	public void setClientShallowCommits(Set<ObjectId> clientShallowCommits) {
		stats.clientShallowCommits = Collections
				.unmodifiableSet(new HashSet<>(clientShallowCommits));
	}

	public boolean isDeltaBaseAsOffset() {
		return deltaBaseAsOffset;
	}

	public void setDeltaBaseAsOffset(boolean deltaBaseAsOffset) {
		this.deltaBaseAsOffset = deltaBaseAsOffset;
	}

	public boolean isReuseDeltaCommits() {
		return reuseDeltaCommits;
	}

	public void setReuseDeltaCommits(boolean reuse) {
		reuseDeltaCommits = reuse;
	}

	public boolean isReuseValidatingObjects() {
		return reuseValidate;
	}

	public void setReuseValidatingObjects(boolean validate) {
		reuseValidate = validate;
	}

	public boolean isThin() {
		return thin;
	}

	public void setThin(boolean packthin) {
		thin = packthin;
	}

	public boolean isUseCachedPacks() {
		return useCachedPacks;
	}

	public void setUseCachedPacks(boolean useCached) {
		useCachedPacks = useCached;
	}

	public boolean isUseBitmaps() {
		return useBitmaps;
	}

	public void setUseBitmaps(boolean useBitmaps) {
		this.useBitmaps = useBitmaps;
	}

	public boolean isIndexDisabled() {
		return indexDisabled || !cachedPacks.isEmpty();
	}

	public void setIndexDisabled(boolean noIndex) {
		this.indexDisabled = noIndex;
	}

	public boolean isIgnoreMissingUninteresting() {
		return ignoreMissingUninteresting;
	}

	public void setIgnoreMissingUninteresting(boolean ignore) {
		ignoreMissingUninteresting = ignore;
	}

	public void setTagTargets(Set<ObjectId> objects) {
		tagTargets = objects;
	}

	public void setShallowPack(int depth
			Collection<? extends ObjectId> unshallow) {
		this.shallowPack = true;
		this.depth = depth;
		this.unshallowObjects = unshallow;
	}

	public void setFilterSpec(@NonNull FilterSpec filter) {
		filterSpec = requireNonNull(filter);
	}

	public long getObjectCount() throws IOException {
		if (stats.totalObjects == 0) {
			long objCnt = 0;

			objCnt += objectsLists[OBJ_COMMIT].size();
			objCnt += objectsLists[OBJ_TREE].size();
			objCnt += objectsLists[OBJ_BLOB].size();
			objCnt += objectsLists[OBJ_TAG].size();

			for (CachedPack pack : cachedPacks)
				objCnt += pack.getObjectCount();
			return objCnt;
		}
		return stats.totalObjects;
	}

	public ObjectIdOwnerMap<ObjectIdOwnerMap.Entry> getObjectSet()
			throws IOException {
		if (!cachedPacks.isEmpty())
			throw new IOException(
					JGitText.get().cachedPacksPreventsListingObjects);

		if (writeBitmaps != null) {
			return writeBitmaps.getObjectSet();
		}

		ObjectIdOwnerMap<ObjectIdOwnerMap.Entry> r = new ObjectIdOwnerMap<>();
		for (BlockList<ObjectToPack> objList : objectsLists) {
			if (objList != null) {
				for (ObjectToPack otp : objList)
					r.add(new ObjectIdOwnerMap.Entry(otp) {
					});
			}
		}
		return r;
	}

	public void excludeObjects(ObjectIdSet idx) {
		if (excludeInPacks == null) {
			excludeInPacks = new ObjectIdSet[] { idx };
			excludeInPackLast = idx;
		} else {
			int cnt = excludeInPacks.length;
			ObjectIdSet[] newList = new ObjectIdSet[cnt + 1];
			System.arraycopy(excludeInPacks
			newList[cnt] = idx;
			excludeInPacks = newList;
		}
	}

	public void preparePack(@NonNull Iterator<RevObject> objectsSource)
			throws IOException {
		while (objectsSource.hasNext()) {
			addObject(objectsSource.next());
		}
	}

	public void preparePack(ProgressMonitor countingMonitor
			@NonNull Set<? extends ObjectId> want
			@NonNull Set<? extends ObjectId> have) throws IOException {
		preparePack(countingMonitor
	}

	public void preparePack(ProgressMonitor countingMonitor
			@NonNull Set<? extends ObjectId> want
			@NonNull Set<? extends ObjectId> have
			@NonNull Set<? extends ObjectId> shallow) throws IOException {
		preparePack(countingMonitor
	}

	public void preparePack(ProgressMonitor countingMonitor
			@NonNull Set<? extends ObjectId> want
			@NonNull Set<? extends ObjectId> have
			@NonNull Set<? extends ObjectId> shallow
			@NonNull Set<? extends ObjectId> noBitmaps) throws IOException {
		try (ObjectWalk ow = getObjectWalk()) {
			ow.assumeShallow(shallow);
			preparePack(countingMonitor
		}
	}

	private ObjectWalk getObjectWalk() {
		return shallowPack ? new DepthWalk.ObjectWalk(reader
				: new ObjectWalk(reader);
	}

	public void preparePack(ProgressMonitor countingMonitor
			@NonNull ObjectWalk walk
			@NonNull Set<? extends ObjectId> interestingObjects
			@NonNull Set<? extends ObjectId> uninterestingObjects
			@NonNull Set<? extends ObjectId> noBitmaps)
			throws IOException {
		if (countingMonitor == null)
			countingMonitor = NullProgressMonitor.INSTANCE;
		if (shallowPack && !(walk instanceof DepthWalk.ObjectWalk))
			throw new IllegalArgumentException(
					JGitText.get().shallowPacksRequireDepthWalk);
		findObjectsToPack(countingMonitor
				uninterestingObjects
	}

	public boolean willInclude(AnyObjectId id) throws IOException {
		ObjectToPack obj = objectsMap.get(id);
		return obj != null && !obj.isEdge();
	}

	public ObjectToPack get(AnyObjectId id) {
		ObjectToPack obj = objectsMap.get(id);
		return obj != null && !obj.isEdge() ? obj : null;
	}

	public ObjectId computeName() {
		final byte[] buf = new byte[OBJECT_ID_LENGTH];
		final MessageDigest md = Constants.newMessageDigest();
		for (ObjectToPack otp : sortByName()) {
			otp.copyRawTo(buf
			md.update(buf
		}
		return ObjectId.fromRaw(md.digest());
	}

	public int getIndexVersion() {
		int indexVersion = config.getIndexVersion();
		if (indexVersion <= 0) {
			for (BlockList<ObjectToPack> objs : objectsLists)
				indexVersion = Math.max(indexVersion
						PackIndexWriter.oldestPossibleFormat(objs));
		}
		return indexVersion;
	}

	public void writeIndex(OutputStream indexStream) throws IOException {
		if (isIndexDisabled())
			throw new IOException(JGitText.get().cachedPacksPreventsIndexCreation);

		long writeStart = System.currentTimeMillis();
		final PackIndexWriter iw = PackIndexWriter.createVersion(
				indexStream
		iw.write(sortByName()
		stats.timeWriting += System.currentTimeMillis() - writeStart;
	}

	public void writeBitmapIndex(OutputStream bitmapIndexStream)
			throws IOException {
		if (writeBitmaps == null)
			throw new IOException(JGitText.get().bitmapsMustBePrepared);

		long writeStart = System.currentTimeMillis();
		final PackBitmapIndexWriterV1 iw = new PackBitmapIndexWriterV1(bitmapIndexStream);
		iw.write(writeBitmaps
		stats.timeWriting += System.currentTimeMillis() - writeStart;
	}

	private List<ObjectToPack> sortByName() {
		if (sortedByName == null) {
			int cnt = 0;
			cnt += objectsLists[OBJ_COMMIT].size();
			cnt += objectsLists[OBJ_TREE].size();
			cnt += objectsLists[OBJ_BLOB].size();
			cnt += objectsLists[OBJ_TAG].size();

			sortedByName = new BlockList<>(cnt);
			sortedByName.addAll(objectsLists[OBJ_COMMIT]);
			sortedByName.addAll(objectsLists[OBJ_TREE]);
			sortedByName.addAll(objectsLists[OBJ_BLOB]);
			sortedByName.addAll(objectsLists[OBJ_TAG]);
			Collections.sort(sortedByName);
		}
		return sortedByName;
	}

	private void beginPhase(PackingPhase phase
			long cnt) {
		state.phase = phase;
		String task;
		switch (phase) {
		case COUNTING:
			task = JGitText.get().countingObjects;
			break;
		case GETTING_SIZES:
			task = JGitText.get().searchForSizes;
			break;
		case FINDING_SOURCES:
			task = JGitText.get().searchForReuse;
			break;
		case COMPRESSING:
			task = JGitText.get().compressingObjects;
			break;
		case WRITING:
			task = JGitText.get().writingObjects;
			break;
		case BUILDING_BITMAPS:
			task = JGitText.get().buildingBitmaps;
			break;
		default:
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().illegalPackingPhase
		}
		monitor.beginTask(task
	}

	private void endPhase(ProgressMonitor monitor) {
		monitor.endTask();
	}

	public void writePack(ProgressMonitor compressMonitor
			ProgressMonitor writeMonitor
			throws IOException {
		if (compressMonitor == null)
			compressMonitor = NullProgressMonitor.INSTANCE;
		if (writeMonitor == null)
			writeMonitor = NullProgressMonitor.INSTANCE;

		excludeInPacks = null;
		excludeInPackLast = null;

		boolean needSearchForReuse = reuseSupport != null && (
				   reuseDeltas
				|| config.isReuseObjects()
				|| !cachedPacks.isEmpty());

		if (compressMonitor instanceof BatchingProgressMonitor) {
			long delay = 1000;
			if (needSearchForReuse && config.isDeltaCompress())
				delay = 500;
			((BatchingProgressMonitor) compressMonitor).setDelayStart(
					delay
					TimeUnit.MILLISECONDS);
		}

		if (needSearchForReuse)
			searchForReuse(compressMonitor);
		if (config.isDeltaCompress())
			searchForDeltas(compressMonitor);

		crc32 = new CRC32();
		final PackOutputStream out = new PackOutputStream(
			writeMonitor
			isIndexDisabled()
				? packStream
				: new CheckedOutputStream(packStream
			this);

		long objCnt = getObjectCount();
		stats.totalObjects = objCnt;
		if (callback != null)
			callback.setObjectCount(objCnt);
		beginPhase(PackingPhase.WRITING
		long writeStart = System.currentTimeMillis();
		try {
			out.writeFileHeader(PACK_VERSION_GENERATED
			out.flush();

			writeObjects(out);
			if (!edgeObjects.isEmpty() || !cachedPacks.isEmpty()) {
				for (PackStatistics.ObjectType.Accumulator typeStat : stats.objectTypes) {
					if (typeStat == null)
						continue;
					stats.thinPackBytes += typeStat.bytes;
				}
			}

			stats.reusedPacks = Collections.unmodifiableList(cachedPacks);
			for (CachedPack pack : cachedPacks) {
				long deltaCnt = pack.getDeltaCount();
				stats.reusedObjects += pack.getObjectCount();
				stats.reusedDeltas += deltaCnt;
				stats.totalDeltas += deltaCnt;
				reuseSupport.copyPackAsIs(out
			}
			writeChecksum(out);
			out.flush();
		} finally {
			stats.timeWriting = System.currentTimeMillis() - writeStart;
			stats.depth = depth;

			for (PackStatistics.ObjectType.Accumulator typeStat : stats.objectTypes) {
				if (typeStat == null)
					continue;
				typeStat.cntDeltas += typeStat.reusedDeltas;
				stats.reusedObjects += typeStat.reusedObjects;
				stats.reusedDeltas += typeStat.reusedDeltas;
				stats.totalDeltas += typeStat.cntDeltas;
			}
		}

		stats.totalBytes = out.length();
		reader.close();
		endPhase(writeMonitor);
	}

	public PackStatistics getStatistics() {
		return new PackStatistics(stats);
	}

	public State getState() {
		return state.snapshot();
	}

	@Override
	public void close() {
		reader.close();
		if (myDeflater != null) {
			myDeflater.end();
			myDeflater = null;
		}
		instances.remove(selfRef);
	}

	private void searchForReuse(ProgressMonitor monitor) throws IOException {
		long cnt = 0;
		cnt += objectsLists[OBJ_COMMIT].size();
		cnt += objectsLists[OBJ_TREE].size();
		cnt += objectsLists[OBJ_BLOB].size();
		cnt += objectsLists[OBJ_TAG].size();

		long start = System.currentTimeMillis();
		beginPhase(PackingPhase.FINDING_SOURCES
		if (cnt <= 4096) {
			BlockList<ObjectToPack> tmp = new BlockList<>((int) cnt);
			tmp.addAll(objectsLists[OBJ_TAG]);
			tmp.addAll(objectsLists[OBJ_COMMIT]);
			tmp.addAll(objectsLists[OBJ_TREE]);
			tmp.addAll(objectsLists[OBJ_BLOB]);
			searchForReuse(monitor
			if (pruneCurrentObjectList) {
				pruneEdgesFromObjectList(objectsLists[OBJ_COMMIT]);
				pruneEdgesFromObjectList(objectsLists[OBJ_TREE]);
				pruneEdgesFromObjectList(objectsLists[OBJ_BLOB]);
				pruneEdgesFromObjectList(objectsLists[OBJ_TAG]);
			}
		} else {
			searchForReuse(monitor
			searchForReuse(monitor
			searchForReuse(monitor
			searchForReuse(monitor
		}
		endPhase(monitor);
		stats.timeSearchingForReuse = System.currentTimeMillis() - start;

		if (config.isReuseDeltas() && config.getCutDeltaChains()) {
			cutDeltaChains(objectsLists[OBJ_TREE]);
			cutDeltaChains(objectsLists[OBJ_BLOB]);
		}
	}

	private void searchForReuse(ProgressMonitor monitor
			throws IOException
		pruneCurrentObjectList = false;
		reuseSupport.selectObjectRepresentation(this
		if (pruneCurrentObjectList)
			pruneEdgesFromObjectList(list);
	}

	private void cutDeltaChains(BlockList<ObjectToPack> list)
			throws IOException {
		int max = config.getMaxDeltaDepth();
		for (int idx = list.size() - 1; idx >= 0; idx--) {
			int d = 0;
			ObjectToPack b = list.get(idx).getDeltaBase();
			while (b != null) {
				if (d < b.getChainLength())
					break;
				b.setChainLength(++d);
				if (d >= max && b.isDeltaRepresentation()) {
					reselectNonDelta(b);
					break;
				}
				b = b.getDeltaBase();
			}
		}
		if (config.isDeltaCompress()) {
			for (ObjectToPack otp : list)
				otp.clearChainLength();
		}
	}

	private void searchForDeltas(ProgressMonitor monitor)
			throws MissingObjectException
			IOException {
		ObjectToPack[] list = new ObjectToPack[
				  objectsLists[OBJ_TREE].size()
				+ objectsLists[OBJ_BLOB].size()
				+ edgeObjects.size()];
		int cnt = 0;
		cnt = findObjectsNeedingDelta(list
		cnt = findObjectsNeedingDelta(list
		if (cnt == 0)
			return;
		int nonEdgeCnt = cnt;

		for (ObjectToPack eo : edgeObjects) {
			eo.setWeight(0);
			list[cnt++] = eo;
		}

		final long sizingStart = System.currentTimeMillis();
		beginPhase(PackingPhase.GETTING_SIZES
		AsyncObjectSizeQueue<ObjectToPack> sizeQueue = reader.getObjectSize(
				Arrays.<ObjectToPack> asList(list).subList(0
		try {
			final long limit = Math.min(
					config.getBigFileThreshold()
					Integer.MAX_VALUE);
			for (;;) {
				try {
					if (!sizeQueue.next())
						break;
				} catch (MissingObjectException notFound) {
					monitor.update(1);
					if (ignoreMissingUninteresting) {
						ObjectToPack otp = sizeQueue.getCurrent();
						if (otp != null && otp.isEdge()) {
							otp.setDoNotDelta();
							continue;
						}

						otp = objectsMap.get(notFound.getObjectId());
						if (otp != null && otp.isEdge()) {
							otp.setDoNotDelta();
							continue;
						}
					}
					throw notFound;
				}

				ObjectToPack otp = sizeQueue.getCurrent();
				if (otp == null)
					otp = objectsMap.get(sizeQueue.getObjectId());

				long sz = sizeQueue.getSize();
				if (DeltaIndex.BLKSZ < sz && sz < limit)
					otp.setWeight((int) sz);
				else
				monitor.update(1);
			}
		} finally {
			sizeQueue.release();
		}
		endPhase(monitor);
		stats.timeSearchingForSizes = System.currentTimeMillis() - sizingStart;

		Arrays.sort(list
			@Override
			public int compare(ObjectToPack a
				int cmp = (a.isDoNotDelta() ? 1 : 0)
						- (b.isDoNotDelta() ? 1 : 0);
				if (cmp != 0)
					return cmp;

				cmp = a.getType() - b.getType();
				if (cmp != 0)
					return cmp;

				cmp = (a.getPathHash() >>> 1) - (b.getPathHash() >>> 1);
				if (cmp != 0)
					return cmp;

				cmp = (a.getPathHash() & 1) - (b.getPathHash() & 1);
				if (cmp != 0)
					return cmp;

				cmp = (a.isEdge() ? 0 : 1) - (b.isEdge() ? 0 : 1);
				if (cmp != 0)
					return cmp;

				return b.getWeight() - a.getWeight();
			}
		});

		while (0 < cnt && list[cnt - 1].isDoNotDelta()) {
			if (!list[cnt - 1].isEdge())
				nonEdgeCnt--;
			cnt--;
		}
		if (cnt == 0)
			return;

		final long searchStart = System.currentTimeMillis();
		searchForDeltas(monitor
		stats.deltaSearchNonEdgeObjects = nonEdgeCnt;
		stats.timeCompressing = System.currentTimeMillis() - searchStart;

		for (int i = 0; i < cnt; i++)
			if (!list[i].isEdge() && list[i].isDeltaRepresentation())
				stats.deltasFound++;
	}

	private int findObjectsNeedingDelta(ObjectToPack[] list
		for (ObjectToPack otp : objectsLists[type]) {
				continue;
				continue;
			otp.setWeight(0);
			list[cnt++] = otp;
		}
		return cnt;
	}

	private void reselectNonDelta(ObjectToPack otp) throws IOException {
		otp.clearDeltaBase();
		otp.clearReuseAsIs();
		boolean old = reuseDeltas;
		reuseDeltas = false;
		reuseSupport.selectObjectRepresentation(this
				NullProgressMonitor.INSTANCE
				Collections.singleton(otp));
		reuseDeltas = old;
	}

	private void searchForDeltas(final ProgressMonitor monitor
			final ObjectToPack[] list
			throws MissingObjectException
			LargeObjectException
		int threads = config.getThreads();
		if (threads == 0)
			threads = Runtime.getRuntime().availableProcessors();
		if (threads <= 1 || cnt <= config.getDeltaSearchWindowSize())
			singleThreadDeltaSearch(monitor
		else
			parallelDeltaSearch(monitor
	}

	private void singleThreadDeltaSearch(ProgressMonitor monitor
			ObjectToPack[] list
		long totalWeight = 0;
		for (int i = 0; i < cnt; i++) {
			ObjectToPack o = list[i];
			totalWeight += DeltaTask.getAdjustedWeight(o);
		}

		long bytesPerUnit = 1;
		while (DeltaTask.MAX_METER <= (totalWeight / bytesPerUnit))
			bytesPerUnit <<= 10;
		int cost = (int) (totalWeight / bytesPerUnit);
		if (totalWeight % bytesPerUnit != 0)
			cost++;

		beginPhase(PackingPhase.COMPRESSING
		new DeltaWindow(config
				monitor
				list
		endPhase(monitor);
	}

	private void parallelDeltaSearch(ProgressMonitor monitor
			ObjectToPack[] list
		DeltaCache dc = new ThreadSafeDeltaCache(config);
		ThreadSafeProgressMonitor pm = new ThreadSafeProgressMonitor(monitor);
		DeltaTask.Block taskBlock = new DeltaTask.Block(threads
				reader
				list
		taskBlock.partitionTasks();
		beginPhase(PackingPhase.COMPRESSING
		pm.startWorkers(taskBlock.tasks.size());

		Executor executor = config.getExecutor();
		final List<Throwable> errors =
				Collections.synchronizedList(new ArrayList<Throwable>(threads));
		if (executor instanceof ExecutorService) {
			runTasks((ExecutorService) executor
		} else if (executor == null) {
			ExecutorService pool = Executors.newFixedThreadPool(threads);
			try {
				runTasks(pool
			} finally {
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
			}
		} else {
			for (DeltaTask task : taskBlock.tasks) {
				executor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							task.call();
						} catch (Throwable failure) {
							errors.add(failure);
						}
					}
				});
			}
			try {
				pm.waitForCompletion();
			} catch (InterruptedException ie) {
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

			throw new IOException(err.getMessage()
		}
		endPhase(monitor);
	}

	private static void runTasks(ExecutorService pool
			ThreadSafeProgressMonitor pm
			DeltaTask.Block tb
		List<Future<?>> futures = new ArrayList<>(tb.tasks.size());
		for (DeltaTask task : tb.tasks)
			futures.add(pool.submit(task));

		try {
			pm.waitForCompletion();
			for (Future<?> f : futures) {
				try {
					f.get();
				} catch (ExecutionException failed) {
					errors.add(failed.getCause());
				}
			}
		} catch (InterruptedException ie) {
			for (Future<?> f : futures)
				f.cancel(true);
			throw new IOException(
					JGitText.get().packingCancelledDuringObjectsWriting);
		}
	}

	private void writeObjects(PackOutputStream out) throws IOException {
		writeObjects(out
		writeObjects(out
		writeObjects(out
		writeObjects(out
	}

	private void writeObjects(PackOutputStream out
			throws IOException {
		if (list.isEmpty())
			return;

		typeStats = stats.objectTypes[list.get(0).getType()];
		long beginOffset = out.length();

		if (reuseSupport != null) {
			reuseSupport.writeObjects(out
		} else {
			for (ObjectToPack otp : list)
				out.writeObject(otp);
		}

		typeStats.bytes += out.length() - beginOffset;
		typeStats.cntObjects = list.size();
	}

	void writeObject(PackOutputStream out
		if (!otp.isWritten())
			writeObjectImpl(out
	}

	private void writeObjectImpl(PackOutputStream out
			throws IOException {
		if (otp.wantWrite()) {
			reselectNonDelta(otp);
		}
		otp.markWantWrite();

		while (otp.isReuseAsIs()) {
			writeBase(out
			if (otp.isWritten())

			crc32.reset();
			otp.setOffset(out.length());
			try {
				reuseSupport.copyObjectAsIs(out
				out.endObject();
				otp.setCRC((int) crc32.getValue());
				typeStats.reusedObjects++;
				if (otp.isDeltaRepresentation()) {
					typeStats.reusedDeltas++;
					typeStats.deltaBytes += out.length() - otp.getOffset();
				}
				return;
			} catch (StoredObjectRepresentationNotAvailableException gone) {
				if (otp.getOffset() == out.length()) {
					otp.setOffset(0);
					otp.clearDeltaBase();
					otp.clearReuseAsIs();
					reuseSupport.selectObjectRepresentation(this
							NullProgressMonitor.INSTANCE
							Collections.singleton(otp));
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
		otp.setCRC((int) crc32.getValue());
	}

	private void writeBase(PackOutputStream out
			throws IOException {
		if (base != null && !base.isWritten() && !base.isEdge())
			writeObjectImpl(out
	}

	private void writeWholeObjectDeflate(PackOutputStream out
			final ObjectToPack otp) throws IOException {
		final Deflater deflater = deflater();
		final ObjectLoader ldr = reader.open(otp

		crc32.reset();
		otp.setOffset(out.length());
		out.writeHeader(otp

		deflater.reset();
		DeflaterOutputStream dst = new DeflaterOutputStream(out
		ldr.copyTo(dst);
		dst.finish();
	}

	private void writeDeltaObjectDeflate(PackOutputStream out
			final ObjectToPack otp) throws IOException {
		writeBase(out

		crc32.reset();
		otp.setOffset(out.length());

		DeltaCache.Ref ref = otp.popCachedDelta();
		if (ref != null) {
			byte[] zbuf = ref.get();
			if (zbuf != null) {
				out.writeHeader(otp
				out.write(zbuf);
				typeStats.cntDeltas++;
				typeStats.deltaBytes += out.length() - otp.getOffset();
				return;
			}
		}

		try (TemporaryBuffer.Heap delta = delta(otp)) {
			out.writeHeader(otp

			Deflater deflater = deflater();
			deflater.reset();
			DeflaterOutputStream dst = new DeflaterOutputStream(out
			delta.writeTo(dst
			dst.finish();
		}
		typeStats.cntDeltas++;
		typeStats.deltaBytes += out.length() - otp.getOffset();
	}

	private TemporaryBuffer.Heap delta(ObjectToPack otp)
			throws IOException {
		DeltaIndex index = new DeltaIndex(buffer(otp.getDeltaBaseId()));
		byte[] res = buffer(otp);

		TemporaryBuffer.Heap delta = new TemporaryBuffer.Heap(res.length);
		index.encode(delta
		return delta;
	}

	private byte[] buffer(AnyObjectId objId) throws IOException {
		return buffer(config
	}

	static byte[] buffer(PackConfig config
			throws IOException {

		return or.open(objId).getCachedBytes(config.getBigFileThreshold());
	}

	private Deflater deflater() {
		if (myDeflater == null)
			myDeflater = new Deflater(config.getCompressionLevel());
		return myDeflater;
	}

	private void writeChecksum(PackOutputStream out) throws IOException {
		packcsum = out.getDigest();
		out.write(packcsum);
	}

	private void findObjectsToPack(@NonNull ProgressMonitor countingMonitor
			@NonNull ObjectWalk walker
			@NonNull Set<? extends ObjectId> have
			@NonNull Set<? extends ObjectId> noBitmaps) throws IOException {
		final long countingStart = System.currentTimeMillis();
		beginPhase(PackingPhase.COUNTING

		stats.interestingObjects = Collections.unmodifiableSet(new HashSet<ObjectId>(want));
		stats.uninterestingObjects = Collections.unmodifiableSet(new HashSet<ObjectId>(have));
		excludeFromBitmapSelection = noBitmaps;

		canBuildBitmaps = config.isBuildBitmaps()
				&& !shallowPack
				&& have.isEmpty()
				&& (excludeInPacks == null || excludeInPacks.length == 0);
		if (!shallowPack && useBitmaps) {
			BitmapIndex bitmapIndex = reader.getBitmapIndex();
			if (bitmapIndex != null) {
				BitmapWalker bitmapWalker = new BitmapWalker(
						walker
				findObjectsToPackUsingBitmaps(bitmapWalker
				endPhase(countingMonitor);
				stats.timeCounting = System.currentTimeMillis() - countingStart;
				stats.bitmapIndexMisses = bitmapWalker.getCountOfBitmapIndexMisses();
				return;
			}
		}

		List<ObjectId> all = new ArrayList<>(want.size() + have.size());
		all.addAll(want);
		all.addAll(have);


		walker.carry(include);

		int haveEst = have.size();
		if (have.isEmpty()) {
			walker.sort(RevSort.COMMIT_TIME_DESC);
		} else {
			walker.sort(RevSort.TOPO);
			if (thin)
				walker.sort(RevSort.BOUNDARY
		}

		List<RevObject> wantObjs = new ArrayList<>(want.size());
		List<RevObject> haveObjs = new ArrayList<>(haveEst);
		List<RevTag> wantTags = new ArrayList<>(want.size());

		AsyncRevObjectQueue q = walker.parseAny(all
		try {
			for (;;) {
				try {
					RevObject o = q.next();
					if (o == null)
						break;
					if (have.contains(o))
						haveObjs.add(o);
					if (want.contains(o)) {
						o.add(include);
						wantObjs.add(o);
						if (o instanceof RevTag)
							wantTags.add((RevTag) o);
					}
				} catch (MissingObjectException e) {
					if (ignoreMissingUninteresting
							&& have.contains(e.getObjectId()))
						continue;
					throw e;
				}
			}
		} finally {
			q.release();
		}

		if (!wantTags.isEmpty()) {
			all = new ArrayList<>(wantTags.size());
			for (RevTag tag : wantTags)
				all.add(tag.getObject());
			q = walker.parseAny(all
			try {
				while (q.next() != null) {
				}
			} finally {
				q.release();
			}
		}

		if (walker instanceof DepthWalk.ObjectWalk) {
			DepthWalk.ObjectWalk depthWalk = (DepthWalk.ObjectWalk) walker;
			for (RevObject obj : wantObjs) {
				depthWalk.markRoot(obj);
			}
			for (RevObject obj : haveObjs) {
				if (obj instanceof RevCommit) {
					RevTree t = ((RevCommit) obj).getTree();
					depthWalk.markUninteresting(t);
				}
			}

			if (unshallowObjects != null) {
				for (ObjectId id : unshallowObjects) {
					depthWalk.markUnshallow(walker.parseAny(id));
				}
			}
		} else {
			for (RevObject obj : wantObjs)
				walker.markStart(obj);
		}
		for (RevObject obj : haveObjs)
			walker.markUninteresting(obj);

		final int maxBases = config.getDeltaSearchWindowSize();
		Set<RevTree> baseTrees = new HashSet<>();
		BlockList<RevCommit> commits = new BlockList<>();
		Set<ObjectId> roots = new HashSet<>();
		RevCommit c;
		while ((c = walker.next()) != null) {
			if (exclude(c))
				continue;
			if (c.has(RevFlag.UNINTERESTING)) {
				if (baseTrees.size() <= maxBases)
					baseTrees.add(c.getTree());
				continue;
			}

			commits.add(c);
			if (c.getParentCount() == 0) {
				roots.add(c.copy());
			}
			countingMonitor.update(1);
		}
		stats.rootCommits = Collections.unmodifiableSet(roots);

		if (shallowPack) {
			for (RevCommit cmit : commits) {
				addObject(cmit
			}
		} else {
			int commitCnt = 0;
			boolean putTagTargets = false;
			for (RevCommit cmit : commits) {
				if (!cmit.has(added)) {
					cmit.add(added);
					addObject(cmit
					commitCnt++;
				}

				for (int i = 0; i < cmit.getParentCount(); i++) {
					RevCommit p = cmit.getParent(i);
					if (!p.has(added) && !p.has(RevFlag.UNINTERESTING)
							&& !exclude(p)) {
						p.add(added);
						addObject(p
						commitCnt++;
					}
				}

				if (!putTagTargets && 4096 < commitCnt) {
					for (ObjectId id : tagTargets) {
						RevObject obj = walker.lookupOrNull(id);
						if (obj instanceof RevCommit
								&& obj.has(include)
								&& !obj.has(RevFlag.UNINTERESTING)
								&& !obj.has(added)) {
							obj.add(added);
							addObject(obj
						}
					}
					putTagTargets = true;
				}
			}
		}
		commits = null;

		if (thin && !baseTrees.isEmpty()) {
			BaseSearch bases = new BaseSearch(countingMonitor
					objectsMap
			RevObject o;
			while ((o = walker.nextObject()) != null) {
				if (o.has(RevFlag.UNINTERESTING))
					continue;
				if (exclude(o))
					continue;

				int pathHash = walker.getPathHashCode();
				byte[] pathBuf = walker.getPathBuffer();
				int pathLen = walker.getPathLength();
				bases.addBase(o.getType()
				filterAndAddObject(o
				countingMonitor.update(1);
			}
		} else {
			RevObject o;
			while ((o = walker.nextObject()) != null) {
				if (o.has(RevFlag.UNINTERESTING))
					continue;
				if (exclude(o))
					continue;
				filterAndAddObject(o
				countingMonitor.update(1);
			}
		}

		for (CachedPack pack : cachedPacks)
			countingMonitor.update((int) pack.getObjectCount());
		endPhase(countingMonitor);
		stats.timeCounting = System.currentTimeMillis() - countingStart;
		stats.bitmapIndexMisses = -1;
	}

	private void findObjectsToPackUsingBitmaps(
			BitmapWalker bitmapWalker
			Set<? extends ObjectId> have)
			throws MissingObjectException
			IOException {
		BitmapBuilder haveBitmap = bitmapWalker.findObjects(have
		BitmapBuilder wantBitmap = bitmapWalker.findObjects(want
				false);
		BitmapBuilder needBitmap = wantBitmap.andNot(haveBitmap);

		if (useCachedPacks && reuseSupport != null && !reuseValidate
				&& (excludeInPacks == null || excludeInPacks.length == 0))
			cachedPacks.addAll(
					reuseSupport.getCachedPacksAndUpdate(needBitmap));

		for (BitmapObject obj : needBitmap) {
			ObjectId objectId = obj.getObjectId();
			if (exclude(objectId)) {
				needBitmap.remove(objectId);
				continue;
			}
			filterAndAddObject(objectId
		}

		if (thin)
			haveObjects = haveBitmap;
	}

	private static void pruneEdgesFromObjectList(List<ObjectToPack> list) {
		final int size = list.size();
		int src = 0;
		int dst = 0;

		for (; src < size; src++) {
			ObjectToPack obj = list.get(src);
			if (obj.isEdge())
				continue;
			if (dst != src)
				list.set(dst
			dst++;
		}

		while (dst < list.size())
			list.remove(list.size() - 1);
	}

	public void addObject(RevObject object)
			throws IncorrectObjectTypeException {
		if (!exclude(object))
			addObject(object
	}

	private void addObject(RevObject object
		addObject(object
	}

	private void addObject(
			final AnyObjectId src
		final ObjectToPack otp;
		if (reuseSupport != null)
			otp = reuseSupport.newObjectToPack(src
		else
			otp = new ObjectToPack(src
		otp.setPathHash(pathHashCode);
		objectsLists[type].add(otp);
		objectsMap.add(otp);
	}

	private void filterAndAddObject(@NonNull AnyObjectId src
			int pathHashCode
			throws IOException {

		boolean reject = filterSpec.getBlobLimit() >= 0 &&
			type == OBJ_BLOB &&
			!want.contains(src) &&
			reader.getObjectSize(src
		if (!reject) {
			addObject(src
		}
	}

	private boolean exclude(AnyObjectId objectId) {
		if (excludeInPacks == null)
			return false;
		if (excludeInPackLast.contains(objectId))
			return true;
		for (ObjectIdSet idx : excludeInPacks) {
			if (idx.contains(objectId)) {
				excludeInPackLast = idx;
				return true;
			}
		}
		return false;
	}

	public void select(ObjectToPack otp
		int nFmt = next.getFormat();

		if (!cachedPacks.isEmpty()) {
			if (otp.isEdge())
				return;
			if ((nFmt == PACK_WHOLE) | (nFmt == PACK_DELTA)) {
				for (CachedPack pack : cachedPacks) {
					if (pack.hasObject(otp
						otp.setEdge();
						otp.clearDeltaBase();
						otp.clearReuseAsIs();
						pruneCurrentObjectList = true;
						return;
					}
				}
			}
		}

		if (nFmt == PACK_DELTA && reuseDeltas && reuseDeltaFor(otp)) {
			ObjectId baseId = next.getDeltaBase();
			ObjectToPack ptr = objectsMap.get(baseId);
			if (ptr != null && !ptr.isEdge()) {
				otp.setDeltaBase(ptr);
				otp.setReuseAsIs();
			} else if (thin && have(ptr
				otp.setDeltaBase(baseId);
				otp.setReuseAsIs();
			} else {
				otp.clearDeltaBase();
				otp.clearReuseAsIs();
			}
		} else if (nFmt == PACK_WHOLE && config.isReuseObjects()) {
			int nWeight = next.getWeight();
			if (otp.isReuseAsIs() && !otp.isDeltaRepresentation()) {
				if (otp.getWeight() <= nWeight)
					return;
			}
			otp.clearDeltaBase();
			otp.setReuseAsIs();
			otp.setWeight(nWeight);
		} else {
			otp.clearDeltaBase();
			otp.clearReuseAsIs();
		}

		otp.setDeltaAttempted(reuseDeltas & next.wasDeltaAttempted());
		otp.select(next);
	}

	private final boolean have(ObjectToPack ptr
		return (ptr != null && ptr.isEdge())
				|| (haveObjects != null && haveObjects.contains(objectId));
	}

	public boolean prepareBitmapIndex(ProgressMonitor pm) throws IOException {
		if (!canBuildBitmaps || getObjectCount() > Integer.MAX_VALUE
				|| !cachedPacks.isEmpty())
			return false;

		if (pm == null)
			pm = NullProgressMonitor.INSTANCE;

		int numCommits = objectsLists[OBJ_COMMIT].size();
		List<ObjectToPack> byName = sortByName();
		sortedByName = null;
		objectsLists = null;
		objectsMap = null;
		writeBitmaps = new PackBitmapIndexBuilder(byName);
		byName = null;

		PackWriterBitmapPreparer bitmapPreparer = new PackWriterBitmapPreparer(
				reader

		Collection<PackWriterBitmapPreparer.BitmapCommit> selectedCommits = bitmapPreparer
				.selectCommits(numCommits

		beginPhase(PackingPhase.BUILDING_BITMAPS

		BitmapWalker walker = bitmapPreparer.newBitmapWalker();
		AnyObjectId last = null;
		for (PackWriterBitmapPreparer.BitmapCommit cmit : selectedCommits) {
			if (!cmit.isReuseWalker()) {
				walker = bitmapPreparer.newBitmapWalker();
			}
			BitmapBuilder bitmap = walker.findObjects(
					Collections.singleton(cmit)

			if (last != null && cmit.isReuseWalker() && !bitmap.contains(last))
				throw new IllegalStateException(MessageFormat.format(
						JGitText.get().bitmapMissingObject
						last.name()));
			last = cmit;
			writeBitmaps.addBitmap(cmit

			pm.update(1);
		}

		endPhase(pm);
		return true;
	}

	private boolean reuseDeltaFor(ObjectToPack otp) {
		int type = otp.getType();
			return true;
		if (type == OBJ_COMMIT)
			return reuseDeltaCommits;
		if (type == OBJ_TAG)
			return false;
		return true;
	}

	private class MutableState {
		private static final long OBJECT_TO_PACK_SIZE =

		private final long totalDeltaSearchBytes;

		private volatile PackingPhase phase;

		MutableState() {
			phase = PackingPhase.COUNTING;
			if (config.isDeltaCompress()) {
				int threads = config.getThreads();
				if (threads <= 0)
					threads = Runtime.getRuntime().availableProcessors();
				totalDeltaSearchBytes = (threads * config.getDeltaSearchMemoryLimit())
						+ config.getBigFileThreshold();
			} else
				totalDeltaSearchBytes = 0;
		}

		State snapshot() {
			long objCnt = 0;
			BlockList<ObjectToPack>[] lists = objectsLists;
			if (lists != null) {
				objCnt += lists[OBJ_COMMIT].size();
				objCnt += lists[OBJ_TREE].size();
				objCnt += lists[OBJ_BLOB].size();
				objCnt += lists[OBJ_TAG].size();
			}

			long bytesUsed = OBJECT_TO_PACK_SIZE * objCnt;
			PackingPhase curr = phase;
			if (curr == PackingPhase.COMPRESSING)
				bytesUsed += totalDeltaSearchBytes;
			return new State(curr
		}
	}

	public static enum PackingPhase {
		COUNTING

		GETTING_SIZES

		FINDING_SOURCES

		COMPRESSING

		WRITING

		BUILDING_BITMAPS;
	}

	public class State {
		private final PackingPhase phase;

		private final long bytesUsed;

		State(PackingPhase phase
			this.phase = phase;
			this.bytesUsed = bytesUsed;
		}

		public PackConfig getConfig() {
			return config;
		}

		public PackingPhase getPhase() {
			return phase;
		}

		public long estimateBytesUsed() {
			return bytesUsed;
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return "PackWriter.State[" + phase + "
		}
	}
}
