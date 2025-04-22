
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TAG;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;
import static org.eclipse.jgit.storage.dht.ChunkInfo.OBJ_MIXED;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSubclassMap;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.storage.file.PackLock;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.LongList;

final class DhtPackParser extends PackParser {
	private final RepositoryKey repo;

	private final Database db;

	private final DhtInserterOptions options;

	private final MessageDigest digest;

	private final byte[] hdrBuf = new byte[ChunkFormatter.TRAILER_SIZE];

	private WriteBuffer dbWriteBuffer;

	private ChunkFormatter[] openChunks;

	private List<ChunkKey>[] keys;

	private Edges[] openEdges;

	private Map<ChunkKey

	private Map<ChunkKey

	private LongList objStreamPos;

	private LongList objChunkPtrs;

	private ChunkFormatter currChunkFmt;

	private long currStreamPos;

	private long currChunkPtr;

	private boolean currCanReuseHeader;

	private long currBasePtr;

	private long currInflatedSize;

	private int currDataPos;

	private long currDataLen;

	private List<ChunkKey> currFragments;

	private PackChunk dbChunk;

	private int dbPtr;

	private LinkedHashMap<ChunkKey

	private List<PackedObjectInfo> allObjects;

	private int linkedIdx;

	private final MutableObjectId idBuffer;

	private ObjectIdSubclassMap<DhtInfo> objectsByName;

	DhtPackParser(DhtObjDatabase objdb
			Database db
		super(objdb

		setCheckObjectCollisions(false);

		this.repo = repo;
		this.db = db;
		this.options = options;
		this.digest = Constants.newMessageDigest();

		dbWriteBuffer = db.newWriteBuffer();
		openChunks = new ChunkFormatter[5];
		keys = newListArray(5);
		openEdges = new Edges[5];
		chunkEdges = new HashMap<ChunkKey
		chunkInfo = new HashMap<ChunkKey
		idBuffer = new MutableObjectId();
		objectsByName = new ObjectIdSubclassMap<DhtInfo>();

		final int max = options.getParserCacheSize();
		chunkReadBackCache = new LinkedHashMap<ChunkKey
				true) {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean removeEldestEntry(Entry<ChunkKey
				return max < size();
			}
		};
	}

	@SuppressWarnings("unchecked")
	private static <T> List<T>[] newListArray(int size) {
		return new List[size];
	}

	@Override
	public PackLock parse(ProgressMonitor progress) throws IOException {
		boolean success = false;
		try {
			PackLock lock = super.parse(progress);

			chunkReadBackCache = null;
			openChunks = null;
			openEdges = null;

			final int objCnt = getObjectCount();
			if (objCnt == 0) {
				return lock;
			}

			for (int i = 0; i < objCnt; i++) {
				DhtInfo oe = (DhtInfo) getObject(i);
				oe.setOffset(oe.chunkPtr);
				objectsByName.add(oe);
			}

			allObjects = getSortedObjectList(new Comparator<PackedObjectInfo>() {
				public int compare(PackedObjectInfo a
					return Long.signum(a.getOffset() - b.getOffset());
				}
			});

			computeChunkEdges();
			putChunkIndexes();
			dbWriteBuffer.flush();

			chunkEdges = null;
			objectsByName = null;

			putGlobalIndex();
			dbWriteBuffer.flush();

			success = true;
			return lock;
		} finally {
			dbWriteBuffer = null;
			openChunks = null;
			openEdges = null;
			objStreamPos = null;
			objChunkPtrs = null;
			currChunkFmt = null;
			currFragments = null;
			dbChunk = null;
			chunkReadBackCache = null;
			chunkInfo = null;
			chunkEdges = null;

			if (!success)
				rollback();

			keys = null;
			allObjects = null;
		}
	}

	private void rollback() {
		try {

			dbWriteBuffer = db.newWriteBuffer();

			for (--linkedIdx; 0 <= linkedIdx; linkedIdx--) {
				DhtInfo oe = (DhtInfo) allObjects.get(linkedIdx);
				ObjectIndexKey objKey = ObjectIndexKey.create(repo
				ChunkKey oeKey = chunkOf(oe.getOffset());
				db.objectIndex().remove(objKey
			}

			deleteChunks(keys[OBJ_COMMIT]);
			deleteChunks(keys[OBJ_TREE]);
			deleteChunks(keys[OBJ_BLOB]);
			deleteChunks(keys[OBJ_TAG]);

			dbWriteBuffer.flush();
		} catch (Throwable cleanupError) {
		}
	}

	private void deleteChunks(List<ChunkKey> list) throws DhtException {
		if (list != null) {
			for (ChunkKey key : list) {
				db.chunk().remove(key
				db.repository().removeInfo(repo
			}
		}
	}

	private void putGlobalIndex() throws DhtException {
		for (linkedIdx = 0; linkedIdx < allObjects.size(); linkedIdx++) {
			DhtInfo oe = (DhtInfo) allObjects.get(linkedIdx);
			ChunkKey key = chunkOf(oe.chunkPtr);
			ChunkInfo info = chunkInfo.get(key);
			db.objectIndex().add(ObjectIndexKey.create(repo
					oe.link(key
		}
	}

	private void computeChunkEdges() {
		int beginIdx = 0;
		ChunkKey key = chunkOf(allObjects.get(0).getOffset());
		int type = typeOf(allObjects.get(0).getOffset());

		int objIdx = 1;
		for (; objIdx < allObjects.size(); objIdx++) {
			DhtInfo oe = (DhtInfo) allObjects.get(objIdx);
			ChunkKey oeKey = chunkOf(oe.getOffset());
			if (!key.equals(oeKey)) {
				computeEdges(allObjects.subList(beginIdx
				beginIdx = objIdx;

				key = oeKey;
				type = typeOf(oe.getOffset());
			}
			if (type != OBJ_MIXED && type != typeOf(oe.getOffset()))
				type = OBJ_MIXED;
		}
		computeEdges(allObjects.subList(beginIdx
	}

	private void computeEdges(List<PackedObjectInfo> objs
			int type) {
		Edges edges = chunkEdges.get(key);
		if (edges == null)
			return;

		for (PackedObjectInfo obj : objs)
			edges.remove(obj);

		switch (type) {
		case OBJ_COMMIT:
			edges.commitEdges = toChunkList(edges.commitIds);
			break;
		case OBJ_TREE:
			break;
		}

		edges.commitIds = null;
	}

	private List<ChunkKey> toChunkList(Set<ObjectId> objects) {
		if (objects == null || objects.isEmpty())
			return null;

		Map<ChunkKey
		for (ObjectId obj : objects) {
			if (!(obj instanceof DhtInfo)) {
				obj = objectsByName.get(obj);
				if (obj == null)
					continue;
			}

			long chunkPtr = ((DhtInfo) obj).chunkPtr;
			ChunkKey key = chunkOf(chunkPtr);
			ChunkOrderingEntry e = map.get(key);
			if (e == null) {
				e = new ChunkOrderingEntry();
				e.key = key;
				e.order = chunkIdx(chunkPtr);
				map.put(key
			} else {
				e.order = Math.min(e.order
			}
		}

		ChunkOrderingEntry[] tmp = map.values().toArray(
				new ChunkOrderingEntry[map.size()]);
		Arrays.sort(tmp);

		ChunkKey[] out = new ChunkKey[tmp.length];
		for (int i = 0; i < tmp.length; i++)
			out[i] = tmp[i].key;
		return Arrays.asList(out);
	}

	private static final class ChunkOrderingEntry implements
			Comparable<ChunkOrderingEntry> {
		ChunkKey key;

		int order;

		public int compareTo(ChunkOrderingEntry o) {
			return order - o.order;
		}
	}

	private void putChunkIndexes() throws DhtException {
		int sIdx = 0;
		ChunkKey key = chunkOf(allObjects.get(0).getOffset());
		int type = typeOf(allObjects.get(0).getOffset());

		int objIdx = 1;
		for (; objIdx < allObjects.size(); objIdx++) {
			DhtInfo oe = (DhtInfo) allObjects.get(objIdx);
			ChunkKey oeKey = chunkOf(oe.getOffset());
			if (!key.equals(oeKey)) {
				putChunkIndex(allObjects.subList(sIdx
				sIdx = objIdx;

				key = oeKey;
				type = typeOf(oe.getOffset());
			}
			if (type != OBJ_MIXED && type != typeOf(oe.getOffset()))
				type = OBJ_MIXED;
		}
		putChunkIndex(allObjects.subList(sIdx
	}

	private void putChunkIndex(List<PackedObjectInfo> objs
			int type) throws DhtException {
		ChunkInfo info = chunkInfo.get(key);
		info.objectsTotal = objs.size();
		info.objectType = type;

		PackChunk.Members builder = new PackChunk.Members();
		builder.setChunkKey(key);

		byte[] index = ChunkIndex.create(objs);
		info.indexSize = index.length;
		builder.setChunkIndex(index);

		Edges edges = chunkEdges.get(key);
		if (edges != null) {
			ChunkPrefetch.Hint commits = null;
			ChunkPrefetch.Hint trees = null;

			switch (type) {
			case OBJ_COMMIT:
				commits = toHint(edges.commitEdges
				break;
			case OBJ_TREE:
				trees = toHint(null
				break;
			}

			ChunkPrefetch prefetch = new ChunkPrefetch(commits
			if (!prefetch.isEmpty()) {
				info.prefetchSize = prefetch.toBytes().length;
				builder.setPrefetch(prefetch);
			}
		}

		db.repository().put(repo
		db.chunk().put(builder
	}

	private List<ChunkKey> sequentialHint(ChunkKey key
		List<ChunkKey> all = keys(type);
		int idx = all.indexOf(key);
		if (0 <= idx) {
			int max = options.getPrefetchDepth();
			int end = Math.min(idx + 1 + max
			return all.subList(idx + 1
		}
		return null;
	}

	private ChunkPrefetch.Hint toHint(List<ChunkKey> edge
			List<ChunkKey> sequential) {
		return new ChunkPrefetch.Hint(edge
	}

	@Override
	protected PackedObjectInfo newInfo(AnyObjectId id
			ObjectId baseId) {
		DhtInfo obj = new DhtInfo(id);
		if (delta != null) {
			DhtDelta d = (DhtDelta) delta;
			obj.chunkPtr = d.chunkPtr;
			obj.base = baseId;
			obj.setSize(d.getSize());
		}
		return obj;
	}

	@Override
	protected void onPackHeader(long objCnt) throws IOException {
		if (Integer.MAX_VALUE < objCnt) {
			throw new DhtException(MessageFormat.format(
					DhtText.get().tooManyObjectsInPack
		}

		objStreamPos = new LongList((int) objCnt);
		objChunkPtrs = new LongList((int) objCnt);
	}

	@Override
	protected void onBeginWholeObject(long streamPosition
			long inflatedSize) throws IOException {
		ChunkFormatter w = begin(type);
		if (!w.whole(type
			endChunk(type);
			w = begin(type);
			if (!w.whole(type
				throw panicCannotInsert();
		}

		currStreamPos = streamPosition;
		currDataPos = w.position();
		currDataLen = 0;
		currCanReuseHeader = true;
	}

	@Override
	protected void onEndWholeObject(PackedObjectInfo info) throws IOException {
		if (currFragments != null)
			finishFragmentedObject();

		objStreamPos.add(currStreamPos);
		objChunkPtrs.add(currChunkPtr);

		DhtInfo oe = (DhtInfo) info;
		oe.chunkPtr = currChunkPtr;
		oe.setSize((int) Math.min(currDataLen
	}

	@Override
	protected void onBeginOfsDelta(long deltaPos
			throws IOException {
		long basePtr = objChunkPtrs.get(findStreamIndex(basePos));
		int type = typeOf(basePtr);
		ChunkFormatter w = begin(type);

		if (isInCurrentChunk(basePtr)) {
			if (w.ofsDelta(sz
				currStreamPos = deltaPos;
				currDataPos = w.position();
				currDataLen = 0;
				currBasePtr = basePtr;
				currInflatedSize = sz;
				currCanReuseHeader = false;
				return;
			}

			endChunk(type);
			w = begin(type);
		}

		ChunkKey baseChunkKey = chunkOf(basePtr);
		if (!w.chunkDelta(sz
			endChunk(type);
			w = begin(type);
			if (!w.chunkDelta(sz
				throw panicCannotInsert();
		}

		currStreamPos = deltaPos;
		currDataPos = w.position();
		currDataLen = 0;
		currBasePtr = basePtr;
		currInflatedSize = sz;
		currCanReuseHeader = true;
	}

	@Override
	protected void onBeginRefDelta(long deltaPos
			throws IOException {
		int type = OBJ_BLOB;
		ChunkFormatter w = begin(type);
		if (!w.refDelta(sz
			endChunk(type);
			w = begin(type);
			if (!w.refDelta(sz
				throw panicCannotInsert();
		}

		currStreamPos = deltaPos;
		currDataPos = w.position();
		currDataLen = 0;
		currCanReuseHeader = true;
	}

	@Override
	protected DhtDelta onEndDelta() throws IOException {
		if (currFragments != null)
			finishFragmentedObject();

		objStreamPos.add(currStreamPos);
		objChunkPtrs.add(currChunkPtr);

		DhtDelta delta = new DhtDelta();
		delta.chunkPtr = currChunkPtr;
		delta.setSize((int) Math.min(currDataLen
		return delta;
	}

	@Override
	protected void onObjectData(Source src
			throws IOException {
		if (src != Source.INPUT)
			return;

		if (currChunkFmt.append(raw
			currDataLen += len;
			return;
		}

		if (currChunkFmt.getObjectCount() == 1)
			currFragments = new LinkedList<ChunkKey>();
		if (currFragments != null) {
			appendToFragment(raw
			currDataLen += len;
			return;
		}

		int dataPos = currDataPos;
		final int dataEnd = currChunkFmt.position();
		final int hdrPos = offsetOf(currChunkPtr);
		final int hdrLen = dataPos - hdrPos;
		final int type = typeOf(currChunkPtr);
		byte[] dataOld = currChunkFmt.getRawChunkDataArray();

		final int tsz = ChunkFormatter.TRAILER_SIZE;
		System.arraycopy(dataOld

		final ChunkFormatter w;
		if (currCanReuseHeader) {
			final int typeOld = currChunkFmt.getCurrentObjectType();

			currChunkFmt.rollback();
			endChunk(type);
			w = begin(type);
			w.adjustObjectCount(1

			if (!w.append(hdrBuf
				throw panicCannotInsert();
			if (tsz < hdrLen && !w.append(dataOld
				throw panicCannotInsert();

		} else {
			currChunkFmt.rollback();
			endChunk(type);
			w = begin(type);
			ChunkKey baseKey = chunkOf(currBasePtr);
			int basePos = offsetOf(currBasePtr);
			if (!w.chunkDelta(currInflatedSize
				throw panicCannotInsert();
		}

		if (hdrLen < tsz) {
			int left = Math.min(dataEnd - dataPos
			if (0 < left && !w.append(hdrBuf
				throw panicCannotInsert();
			dataPos += left;
		}

		if (dataPos < dataEnd && !w.append(dataOld
			throw panicCannotInsert();
		dataOld = null;

		if (!w.append(raw
			currFragments = new LinkedList<ChunkKey>();
			appendToFragment(raw
		}
		currDataLen += len;
	}

	private void appendToFragment(byte[] raw
			throws DhtException {
		while (0 < len) {
			if (currChunkFmt.free() == 0) {
				int typeCode = typeOf(currChunkPtr);
				currChunkFmt.setFragment();
				currFragments.add(endChunk(typeCode));

				currChunkFmt = newChunk(typeCode);
				openChunks[typeCode] = currChunkFmt;
			}
			int n = Math.min(len
			currChunkFmt.append(raw
			pos += n;
			len -= n;
		}
	}

	private void finishFragmentedObject() throws DhtException {
		currChunkFmt.setFragment();
		ChunkKey key = endChunk(typeOf(currChunkPtr));
		if (key != null)
			currFragments.add(key);

		for (ChunkKey k : currFragments) {
					.setFragments(new ChunkFragments(currFragments))
					dbWriteBuffer);
		}
		currFragments = null;
	}

	@Override
	protected void onInflatedObjectData(PackedObjectInfo obj
			byte[] data) throws IOException {
		switch (typeCode) {
		case OBJ_COMMIT:
			updateCommitEdges((DhtInfo) obj
			break;

		case OBJ_TREE:
			updateTreeEdges((DhtInfo) obj
			break;
		}
	}

	private void updateCommitEdges(DhtInfo obj
		objectsByName.add(obj);

		Edges edges = edges(obj.chunkPtr);
		edges.remove(obj);


		int ptr = 46;
		while (raw[ptr] == 'p') {
			idBuffer.fromString(raw
			edges.commit(lookupByName(idBuffer));
			ptr += 48;
		}
	}

	private void updateTreeEdges(DhtInfo obj
	}

	private ObjectId lookupByName(AnyObjectId obj) {
		DhtInfo info = objectsByName.get(obj);
		return info != null ? info : obj.copy();
	}

	private Edges edges(long chunkPtr) {
		if (isInCurrentChunk(chunkPtr)) {
			int type = typeOf(chunkPtr);
			Edges s = openEdges[type];
			if (s == null) {
				s = new Edges();
				openEdges[type] = s;
			}
			return s;
		} else {
			ChunkKey key = chunkOf(chunkPtr);
			Edges s = chunkEdges.get(key);
			if (s == null) {
				s = new Edges();
				chunkEdges.put(key
			}
			return s;
		}
	}

	private static class Edges {
		Set<ObjectId> commitIds;

		List<ChunkKey> commitEdges;

		void commit(ObjectId id) {
			if (commitIds == null)
				commitIds = new HashSet<ObjectId>();
			commitIds.add(id);
		}

		void remove(AnyObjectId id) {
			if (commitIds != null)
				commitIds.remove(id);
		}
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(PackedObjectInfo obj
			ObjectTypeAndSize info) throws IOException {
		return seekDatabase(((DhtInfo) obj).chunkPtr
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(UnresolvedDelta delta
			ObjectTypeAndSize info) throws IOException {
		return seekDatabase(((DhtDelta) delta).chunkPtr
	}

	private ObjectTypeAndSize seekDatabase(long chunkPos
			throws DhtException {
		seekChunk(chunkOf(chunkPos)
		dbPtr = dbChunk.readObjectTypeAndSize(offsetOf(chunkPos)
		if (info.type == PackChunk.OBJ_CHUNK_DELTA)
			info.type = Constants.OBJ_REF_DELTA;
		return info;
	}

	@Override
	protected int readDatabase(byte[] dst
		int n = dbChunk.read(dbPtr
		if (0 < n) {
			dbPtr += n;
			return n;
		}

		ChunkKey next = dbChunk.getNextFragment();
		if (next == null)
			return 0;

		seekChunk(next
		n = dbChunk.read(0
		dbPtr = n;
		return n;
	}

	private void seekChunk(ChunkKey key
			DhtTimeoutException {
		if (dbChunk == null || !dbChunk.getChunkKey().equals(key)) {
			dbChunk = chunkReadBackCache.get(key);
			if (dbChunk == null) {
				dbWriteBuffer.flush();

				Collection<PackChunk.Members> found;
				Context opt = Context.READ_REPAIR;
				Sync<Collection<PackChunk.Members>> sync = Sync.create();
				db.chunk().get(opt
				try {
					found = sync.get(DhtReaderOptions.DEFAULT.getTimeout());
				} catch (InterruptedException e) {
					throw new DhtTimeoutException(e);
				} catch (TimeoutException e) {
					throw new DhtTimeoutException(e);
				}

				if (found.isEmpty()) {
					throw new DhtException(MessageFormat.format(
							DhtText.get().missingChunk
				}

				dbChunk = found.iterator().next().build();
				if (cache)
					chunkReadBackCache.put(key
			}
		}
	}

	@Override
	protected boolean onAppendBase(int typeCode
			PackedObjectInfo info) throws IOException {
	}

	@Override
	protected void onEndThinPack() throws IOException {
	}

	@Override
	protected void onPackFooter(byte[] hash) throws IOException {


		endChunk(OBJ_COMMIT);
		endChunk(OBJ_TREE);
		endChunk(OBJ_BLOB);
		endChunk(OBJ_TAG);

		objStreamPos = null;
		objChunkPtrs = null;
	}

	@Override
	protected void onObjectHeader(Source src
			throws IOException {
	}

	@Override
	protected void onStoreStream(byte[] raw
			throws IOException {
	}

	@Override
	protected boolean checkCRC(int oldCRC) {
	}

	private ChunkFormatter begin(int typeCode) {
		final ChunkFormatter w = chunk(typeCode);
		currChunkFmt = w;
		currChunkPtr = position(w
		return w;
	}

	private ChunkKey endChunk(int typeCode) throws DhtException {
		ChunkFormatter w = openChunks[typeCode];
		if (w == null)
			return null;

		openChunks[typeCode] = null;
		currChunkFmt = null;

		if (w.isEmpty())
			return null;

		ChunkKey key = w.end(digest);
		keys(typeCode).add(key);
		chunkInfo.put(key

		Edges e = openEdges[typeCode];
		if (e != null) {
			chunkEdges.put(key
			openEdges[typeCode] = null;
		}

		if (currFragments == null)
			chunkReadBackCache.put(key

		w.unsafePut(db
		return key;
	}

	private List<ChunkKey> keys(int typeCode) {
		List<ChunkKey> list = keys[typeCode];
		if (list == null)
			keys[typeCode] = list = new ArrayList<ChunkKey>(4);
		return list;
	}

	private ChunkFormatter chunk(int typeCode) {
		ChunkFormatter w = openChunks[typeCode];
		if (w == null) {
			w = newChunk(typeCode);
			openChunks[typeCode] = w;
		}
		return w;
	}

	private int findStreamIndex(long streamPosition) throws DhtException {
		int high = objStreamPos.size();
		int low = 0;
		do {
			final int mid = (low + high) >>> 1;
			final long pos = objStreamPos.get(mid);
			if (streamPosition < pos)
				high = mid;
			else if (streamPosition == pos)
				return mid;
			else
				low = mid + 1;
		} while (low < high);
		throw new DhtException(MessageFormat.format(
				DhtText.get().noSavedTypeForBase
	}

	private long position(ChunkFormatter w
		List<ChunkKey> list = keys(typeCode);
		int idx = list.size();
		int ptr = w.position();
		return (((long) typeCode) << 61) | (((long) idx) << 32) | ptr;
	}

	private static int typeOf(long position) {
		return (int) (position >>> 61);
	}

	private boolean isInCurrentChunk(long position) {
		List<ChunkKey> list = keys(typeOf(position));
		return chunkIdx(position) == list.size();
	}

	private ChunkKey chunkOf(long position) {
		List<ChunkKey> list = keys(typeOf(position));
		return list.get(chunkIdx(position));
	}

	private static int chunkIdx(long position) {
		return ((int) ((position << 3) >>> (32 + 3)));
	}

	private static int offsetOf(long position) {
		return (int) position;
	}

	private ChunkFormatter newChunk(int typeCode) {
		ChunkFormatter fmt;

		fmt = new ChunkFormatter(repo
		fmt.setSource(ChunkInfo.Source.RECEIVE);
		fmt.setObjectType(typeCode);
		return fmt;
	}

	private static DhtException panicCannotInsert() {
		return new DhtException(DhtText.get().cannotInsertObject);
	}

	static class DhtInfo extends PackedObjectInfo {
		long chunkPtr;

		ObjectId base;

		DhtInfo(AnyObjectId id) {
			super(id);
		}


		int getSize() {
			return getCRC();
		}

		void setSize(int size) {
			setCRC(size);
		}

		ObjectInfo link(ChunkKey chunkKey
			return new ObjectInfo(chunkKey
					base
		}
	}

	static class DhtDelta extends UnresolvedDelta {
		long chunkPtr;


		int getSize() {
			return getCRC();
		}

		void setSize(int size) {
			setCRC(size);
		}
	}
}
