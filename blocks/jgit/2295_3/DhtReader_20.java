
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_OFS_DELTA;
import static org.eclipse.jgit.lib.Constants.OBJ_REF_DELTA;
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
import java.util.Set;
import java.util.Map.Entry;
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
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.LongList;

public class DhtPackParser extends PackParser {
	private final RepositoryKey repo;

	private final Database db;

	private final DhtInserterOptions options;

	private final MessageDigest chunkKeyDigest;

	private Boolean saveAsCachedPack;

	private WriteBuffer dbWriteBuffer;

	private ChunkFormatter[] openChunks;

	private Edges[] openEdges;

	private List<ChunkInfo>[] infoByOrder;

	private Map<ChunkKey

	private Map<ChunkKey

	private Map<ChunkKey

	private Map<ChunkKey

	private LongList objStreamPos;

	private LongList objChunkPtrs;

	private ChunkFormatter currChunk;

	private int currType;

	private long currChunkPtr;

	private long currBasePtr;

	private int currDataPos;

	private long currPackedSize;

	private long currInflatedSize;

	private List<ChunkKey> currFragments;

	private PackChunk dbChunk;

	private int dbPtr;

	private LinkedHashMap<ChunkKey

	private List<DhtInfo> allObjects;

	private int linkedIdx;

	private CachedPackKey cachedPackKey;

	private CanonicalTreeParser treeParser;

	private final MutableObjectId idBuffer;

	private ObjectIdSubclassMap<DhtInfo> objectsByName;

	DhtPackParser(DhtObjDatabase objdb
			Database db
		super(objdb

		setCheckObjectCollisions(false);

		this.repo = repo;
		this.db = db;
		this.options = options;
		this.chunkKeyDigest = Constants.newMessageDigest();

		dbWriteBuffer = db.newWriteBuffer();
		openChunks = new ChunkFormatter[5];
		openEdges = new Edges[5];
		infoByOrder = newListArray(5);
		infoByKey = new HashMap<ChunkKey
		dirtyMeta = new HashMap<ChunkKey
		chunkMeta = new HashMap<ChunkKey
		chunkEdges = new HashMap<ChunkKey
		treeParser = new CanonicalTreeParser();
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

	public boolean isSaveAsCachedPack() {
		return saveAsCachedPack != null && saveAsCachedPack.booleanValue();
	}

	public void setSaveAsCachedPack(boolean save) {
		saveAsCachedPack = Boolean.valueOf(save);
	}

	@Override
	public PackLock parse(ProgressMonitor receiving
			throws IOException {
		boolean success = false;
		try {
			PackLock lock = super.parse(receiving

			chunkReadBackCache = null;
			openChunks = null;
			openEdges = null;
			treeParser = null;

			final int objCnt = getObjectCount();
			if (objCnt == 0) {
				return lock;
			}

			allObjects = sortedObjectList();

			if (isSaveAsCachedPack())
				putCachedPack();
			computeChunkEdges();
			putChunkIndexes();
			putDirtyMeta();

			chunkMeta = null;
			chunkEdges = null;
			dirtyMeta = null;
			objectsByName = null;
			dbWriteBuffer.flush();

			putGlobalIndex();
			dbWriteBuffer.flush();

			success = true;
			return lock;
		} finally {
			openChunks = null;
			openEdges = null;
			objStreamPos = null;
			objChunkPtrs = null;
			currChunk = null;
			currFragments = null;
			dbChunk = null;
			chunkReadBackCache = null;
			infoByKey = null;
			chunkMeta = null;
			chunkEdges = null;
			treeParser = null;

			if (!success)
				rollback();

			infoByOrder = null;
			allObjects = null;
			dbWriteBuffer = null;
		}
	}

	private void putCachedPack() throws DhtException {
		CachedPackInfo info = new CachedPackInfo();

		for (DhtInfo obj : objectsByName) {
			if (!obj.isInPack())
				return;

			if (!obj.isReferenced())
				info.tips.add(obj.copy());
		}

		MessageDigest version = Constants.newMessageDigest();
		addChunkList(info
		addChunkList(info
		addChunkList(info
		addChunkList(info

		info.name = computePackName();
		info.version = ObjectId.fromRaw(version.digest());

		cachedPackKey = info.getRowKey();
		for (List<ChunkInfo> list : infoByOrder) {
			if (list == null)
				continue;
			for (ChunkInfo c : list) {
				c.cachedPack = cachedPackKey;
				if (c.isFragment())
					db.repository().put(repo
			}
		}

		db.repository().put(repo
	}

	private void addChunkList(CachedPackInfo info
			List<ChunkInfo> list) {
		if (list == null)
			return;
		for (ChunkInfo c : list) {
			int len = c.chunkSize - ChunkFormatter.TRAILER_SIZE;
			info.totalBytes += len;
			info.totalObjects += c.objectsTotal;
			info.chunks.add(c.getChunkKey());
			version.update(c.getChunkKey().toShortBytes());
		}
	}

	private ObjectId computePackName() {
		int cnt = allObjects.size();
		DhtInfo[] sortedByName = allObjects.toArray(new DhtInfo[cnt]);
		Arrays.sort(sortedByName);

		byte[] buf = new byte[Constants.OBJECT_ID_LENGTH];
		MessageDigest md = Constants.newMessageDigest();
		for (DhtInfo otp : sortedByName) {
			otp.copyRawTo(buf
			md.update(buf
		}
		return ObjectId.fromRaw(md.digest());
	}

	@SuppressWarnings("unchecked")
	private List<DhtInfo> sortedObjectList() {
		List list = getSortedObjectList(new Comparator<PackedObjectInfo>() {
			public int compare(PackedObjectInfo o1
				DhtInfo a = (DhtInfo) o1;
				DhtInfo b = (DhtInfo) o2;
				return Long.signum(a.chunkPtr - b.chunkPtr);
			}
		});
		return list;
	}

	private void rollback() throws DhtException {
		try {
			dbWriteBuffer.abort();
			dbWriteBuffer = db.newWriteBuffer();

			if (cachedPackKey != null)
				db.repository().remove(repo

			for (--linkedIdx; 0 <= linkedIdx; linkedIdx--) {
				DhtInfo oe = allObjects.get(linkedIdx);
				ObjectIndexKey objKey = ObjectIndexKey.create(repo
				ChunkKey oeKey = chunkOf(oe.chunkPtr);
				db.objectIndex().remove(objKey
			}

			deleteChunks(infoByOrder[OBJ_COMMIT]);
			deleteChunks(infoByOrder[OBJ_TREE]);
			deleteChunks(infoByOrder[OBJ_BLOB]);
			deleteChunks(infoByOrder[OBJ_TAG]);

			dbWriteBuffer.flush();
		} catch (Throwable err) {
			throw new DhtException(DhtText.get().packParserRollbackFailed
		}
	}

	private void deleteChunks(List<ChunkInfo> list) throws DhtException {
		if (list != null) {
			for (ChunkInfo info : list) {
				ChunkKey key = info.getChunkKey();
				db.chunk().remove(key
				db.repository().remove(repo
			}
		}
	}

	private void putGlobalIndex() throws DhtException {
		for (linkedIdx = 0; linkedIdx < allObjects.size(); linkedIdx++) {
			DhtInfo oe = allObjects.get(linkedIdx);
			ObjectIndexKey objKey = ObjectIndexKey.create(repo
			ChunkKey chunkKey = chunkOf(oe.chunkPtr);
			db.objectIndex().add(objKey
		}
	}

	private void computeChunkEdges() throws DhtException {
		int beginIdx = 0;
		ChunkKey key = chunkOf(allObjects.get(0).chunkPtr);
		int type = typeOf(allObjects.get(0).chunkPtr);

		int objIdx = 1;
		for (; objIdx < allObjects.size(); objIdx++) {
			DhtInfo oe = allObjects.get(objIdx);
			ChunkKey oeKey = chunkOf(oe.chunkPtr);
			if (!key.equals(oeKey)) {
				computeEdges(allObjects.subList(beginIdx
				beginIdx = objIdx;

				key = oeKey;
				type = typeOf(oe.chunkPtr);
			}
			if (type != OBJ_MIXED && type != typeOf(oe.chunkPtr))
				type = OBJ_MIXED;
		}
		computeEdges(allObjects.subList(beginIdx
	}

	private void computeEdges(List<DhtInfo> objs
			throws DhtException {
		Edges edges = chunkEdges.get(key);
		if (edges == null)
			return;

		for (DhtInfo obj : objs)
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

	private List<ChunkKey> toChunkList(Set<DhtInfo> objects)
			throws DhtException {
		if (objects == null || objects.isEmpty())
			return null;

		Map<ChunkKey
		for (DhtInfo obj : objects) {
			if (!obj.isInPack())
				continue;

			long chunkPtr = obj.chunkPtr;
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
		DhtInfo oe = allObjects.get(0);
		oe.setOffset(offsetOf(oe.chunkPtr));

		ChunkKey key = chunkOf(oe.chunkPtr);
		int type = typeOf(oe.chunkPtr);

		int objIdx = 1;
		for (; objIdx < allObjects.size(); objIdx++) {
			oe = allObjects.get(objIdx);
			oe.setOffset(offsetOf(oe.chunkPtr));

			ChunkKey oeKey = chunkOf(oe.chunkPtr);
			if (!key.equals(oeKey)) {
				putChunkIndex(allObjects.subList(sIdx
				sIdx = objIdx;

				key = oeKey;
				type = typeOf(oe.chunkPtr);
			}
			if (type != OBJ_MIXED && type != typeOf(oe.chunkPtr))
				type = OBJ_MIXED;
		}
		putChunkIndex(allObjects.subList(sIdx
	}

	private void putChunkIndex(List<DhtInfo> objectList
			throws DhtException {
		ChunkInfo info = infoByKey.get(key);
		info.objectsTotal = objectList.size();
		info.objectType = type;

		PackChunk.Members builder = new PackChunk.Members();
		builder.setChunkKey(key);

		byte[] index = ChunkIndex.create(objectList);
		info.indexSize = index.length;
		builder.setChunkIndex(index);

		ChunkMeta meta = dirtyMeta.remove(key);
		if (meta == null)
			meta = chunkMeta.get(key);
		if (meta == null)
			meta = new ChunkMeta(key);

		Edges edges = chunkEdges.get(key);
		if (edges != null) {
			switch (type) {
			case OBJ_COMMIT: {
				List<ChunkKey> e = edges.commitEdges;
				meta.commitPrefetch = new ChunkMeta.PrefetchHint(e
				break;
			}
			case OBJ_TREE: {
				List<ChunkKey> s = sequentialHint(key
				meta.treePrefetch = new ChunkMeta.PrefetchHint(null
				break;
			}
			}
		}

		if (meta.isEmpty()) {
			info.metaSize = 0;
		} else {
			info.metaSize = meta.toBytes().length;
			builder.setMeta(meta);
		}

		db.repository().put(repo
		db.chunk().put(builder
	}

	private List<ChunkKey> sequentialHint(ChunkKey key
		List<ChunkInfo> infoList = infoByOrder[typeCode];
		if (infoList == null)
			return Collections.emptyList();

		List<ChunkKey> all = new ArrayList<ChunkKey>(infoList.size());
		for (ChunkInfo info : infoList)
			all.add(info.getChunkKey());

		int idx = all.indexOf(key);
		if (0 <= idx) {
			int max = options.getPrefetchDepth();
			int end = Math.min(idx + 1 + max
			return all.subList(idx + 1
		}
		return null;
	}

	private void putDirtyMeta() throws DhtException {
		for (ChunkMeta meta : dirtyMeta.values()) {
			PackChunk.Members builder = new PackChunk.Members();
			builder.setChunkKey(meta.getChunkKey());
			builder.setMeta(meta);
			db.chunk().put(builder
		}
	}

	@Override
	protected PackedObjectInfo newInfo(AnyObjectId id
			ObjectId baseId) {
		DhtInfo obj = objectsByName.addIfAbsent(new DhtInfo(id));
		if (delta != null) {
			DhtDelta d = (DhtDelta) delta;
			obj.chunkPtr = d.chunkPtr;
			obj.packedSize = d.packedSize;
			obj.inflatedSize = d.inflatedSize;
			obj.base = baseId;
			obj.setType(d.getType());
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

		if (saveAsCachedPack == null)
			setSaveAsCachedPack(1000 < objCnt);
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

		currType = type;
		currDataPos = w.position();
		currPackedSize = 0;
		currInflatedSize = inflatedSize;
		objStreamPos.add(streamPosition);
	}

	@Override
	protected void onEndWholeObject(PackedObjectInfo info) throws IOException {
		endOneObject();

		DhtInfo oe = (DhtInfo) info;
		oe.chunkPtr = currChunkPtr;
		oe.packedSize = currPackedSize;
		oe.inflatedSize = currInflatedSize;
		oe.setType(currType);
	}

	private void endOneObject() throws DhtException {
		if (currFragments != null)
			endFragmentedObject();
		objChunkPtrs.add(currChunkPtr);
	}

	@Override
	protected void onBeginOfsDelta(long deltaPos
			long inflatedSize) throws IOException {
		long basePtr = objChunkPtrs.get(findStreamIndex(basePos));
		int type = typeOf(basePtr);

		currType = type;
		currPackedSize = 0;
		currInflatedSize = inflatedSize;
		currBasePtr = basePtr;
		objStreamPos.add(deltaPos);

		ChunkFormatter w = begin(type);
		if (isInCurrentChunk(basePtr)) {
			if (w.ofsDelta(inflatedSize
				currDataPos = w.position();
				return;
			}

			endChunk(type);
			w = begin(type);
		}

		if (!longOfsDelta(w
			endChunk(type);
			w = begin(type);
			if (!longOfsDelta(w
				throw panicCannotInsert();
		}

		currDataPos = w.position();
	}

	@Override
	protected void onBeginRefDelta(long deltaPos
			long inflatedSize) throws IOException {
		int typeCode;
		DhtInfo baseInfo = objectsByName.get(baseId);
		if (baseInfo != null && baseInfo.isInPack()) {
			typeCode = baseInfo.getType();
			currType = typeCode;
		} else {
			typeCode = OBJ_BLOB;
			currType = -1;
		}

		ChunkFormatter w = begin(typeCode);
		if (!w.refDelta(inflatedSize
			endChunk(typeCode);
			w = begin(typeCode);
			if (!w.refDelta(inflatedSize
				throw panicCannotInsert();
		}

		currDataPos = w.position();
		currPackedSize = 0;
		currInflatedSize = inflatedSize;
		objStreamPos.add(deltaPos);
	}

	@Override
	protected DhtDelta onEndDelta() throws IOException {
		endOneObject();

		DhtDelta delta = new DhtDelta();
		delta.chunkPtr = currChunkPtr;
		delta.packedSize = currPackedSize;
		delta.inflatedSize = currInflatedSize;
		if (0 < currType)
			delta.setType(currType);
		return delta;
	}

	@Override
	protected void onObjectData(Source src
			throws IOException {
		if (src != Source.INPUT)
			return;

		if (currChunk.append(raw
			currPackedSize += len;
			return;
		}

		if (currFragments == null && currChunk.getObjectCount() == 1)
			currFragments = new LinkedList<ChunkKey>();
		if (currFragments != null) {
			appendToFragment(raw
			return;
		}

		final int dataPos = currDataPos;
		final int dataEnd = currChunk.position();
		final int hdrPos = offsetOf(currChunkPtr);
		final int hdrLen = dataPos - hdrPos;
		final int type = typeOf(currChunkPtr);
		byte[] dataOld = currChunk.getRawChunkDataArray();
		final int typeOld = currChunk.getCurrentObjectType();

		currChunk.rollback();
		endChunk(type);

		final ChunkFormatter w = begin(type);
		switch (typeOld) {
		case OBJ_COMMIT:
		case OBJ_BLOB:
		case OBJ_TREE:
		case OBJ_TAG:
		case OBJ_REF_DELTA:
			w.adjustObjectCount(1
			if (!w.append(dataOld
				throw panicCannotInsert();
			break;

		case OBJ_OFS_DELTA:
			if (!longOfsDelta(w
				throw panicCannotInsert();
			break;

		default:
			throw new DhtException("Internal programming error: " + typeOld);
		}

		currDataPos = w.position();
		if (dataPos < dataEnd && !w.append(dataOld
			throw panicCannotInsert();
		dataOld = null;

		if (w.append(raw
			currPackedSize += len;
		} else {
			currFragments = new LinkedList<ChunkKey>();
			appendToFragment(raw
		}
	}

	private boolean longOfsDelta(ChunkFormatter w
		final int type = typeOf(basePtr);
		final List<ChunkInfo> infoList = infoByOrder[type];
		final int baseIdx = chunkIdx(basePtr);
		final ChunkInfo baseInfo = infoList.get(baseIdx);

		long relativeChunkStart = 0;
		for (int i = infoList.size() - 1; baseIdx <= i; i--) {
			ChunkInfo info = infoList.get(i);
			int packSize = info.chunkSize - ChunkFormatter.TRAILER_SIZE;
			relativeChunkStart += packSize;
		}

		long ofs = w.position() + relativeChunkStart - offsetOf(basePtr);
		if (w.ofsDelta(infSize
			w.useBaseChunk(relativeChunkStart
			return true;
		}
		return false;
	}

	private void appendToFragment(byte[] raw
			throws DhtException {
		while (0 < len) {
			if (currChunk.free() == 0) {
				int typeCode = typeOf(currChunkPtr);
				currChunk.setFragment();
				currFragments.add(endChunk(typeCode));
				currChunk = openChunk(typeCode);
			}

			int n = Math.min(len
			currChunk.append(raw
			currPackedSize += n;
			pos += n;
			len -= n;
		}
	}

	private void endFragmentedObject() throws DhtException {
		currChunk.setFragment();
		ChunkKey lastKey = endChunk(typeOf(currChunkPtr));
		if (lastKey != null)
			currFragments.add(lastKey);

		for (ChunkKey key : currFragments) {
			ChunkMeta meta = chunkMeta.get(key);
			if (meta == null) {
				meta = new ChunkMeta(key);
				chunkMeta.put(key
			}
			meta.fragments = currFragments;
			dirtyMeta.put(key
		}
		currFragments = null;
	}

	@Override
	protected void onInflatedObjectData(PackedObjectInfo obj
			byte[] data) throws IOException {
		DhtInfo info = (DhtInfo) obj;
		info.inflatedSize = data.length;
		info.setType(typeCode);

		switch (typeCode) {
		case OBJ_COMMIT:
			onCommit(info
			break;

		case OBJ_TREE:
			onTree(info
			break;

		case OBJ_TAG:
			onTag(info
			break;
		}
	}

	private void onCommit(DhtInfo obj
		Edges edges = edges(obj.chunkPtr);
		edges.remove(obj);

		if (isSaveAsCachedPack()) {
			idBuffer.fromString(raw
			lookupByName(idBuffer).setReferenced();
		}

		int ptr = 46;
		while (raw[ptr] == 'p') {
			idBuffer.fromString(raw
			DhtInfo p = lookupByName(idBuffer);
			p.setReferenced();
			edges.commit(p);
			ptr += 48;
		}
	}

	private void onTree(DhtInfo obj
		if (isSaveAsCachedPack()) {
			treeParser.reset(data);
			while (!treeParser.eof()) {
				idBuffer.fromRaw(treeParser.idBuffer()
				lookupByName(idBuffer).setReferenced();
				treeParser.next();
			}
		}
	}

	private void onTag(DhtInfo obj
		if (isSaveAsCachedPack()) {
			idBuffer.fromString(data
			lookupByName(idBuffer).setReferenced();
		}
	}

	private DhtInfo lookupByName(AnyObjectId obj) {
		DhtInfo info = objectsByName.get(obj);
		if (info == null) {
			info = new DhtInfo(obj);
			objectsByName.add(info);
		}
		return info;
	}

	private Edges edges(long chunkPtr) throws DhtException {
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
		Set<DhtInfo> commitIds;

		List<ChunkKey> commitEdges;

		void commit(DhtInfo id) {
			if (commitIds == null)
				commitIds = new HashSet<DhtInfo>();
			commitIds.add(id);
		}

		void remove(DhtInfo id) {
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

	private ObjectTypeAndSize seekDatabase(long chunkPtr
			throws DhtException {
		seekChunk(chunkOf(chunkPtr)
		dbPtr = dbChunk.readObjectTypeAndSize(offsetOf(chunkPtr)
		return info;
	}

	@Override
	protected int readDatabase(byte[] dst
		int n = dbChunk.read(dbPtr
		if (0 < n) {
			dbPtr += n;
			return n;
		}


		ChunkMeta meta = chunkMeta.get(dbChunk.getChunkKey());
		if (meta == null)
			return 0;

		ChunkKey next = meta.getNextFragment(dbChunk.getChunkKey());
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

	private ChunkFormatter begin(int typeCode) throws DhtException {
		ChunkFormatter w = openChunk(typeCode);
		currChunk = w;
		currChunkPtr = makeObjectPointer(w
		return w;
	}

	private ChunkFormatter openChunk(int typeCode) throws DhtException {
		if (typeCode == 0)
			throw new DhtException("Invalid internal typeCode 0");

		ChunkFormatter w = openChunks[typeCode];
		if (w == null) {
			w = new ChunkFormatter(repo
			w.setSource(ChunkInfo.Source.RECEIVE);
			w.setObjectType(typeCode);
			openChunks[typeCode] = w;
		}
		return w;
	}

	private ChunkKey endChunk(int typeCode) throws DhtException {
		ChunkFormatter w = openChunks[typeCode];
		if (w == null)
			return null;

		openChunks[typeCode] = null;
		currChunk = null;

		if (w.isEmpty())
			return null;

		ChunkKey key = w.end(chunkKeyDigest);
		ChunkInfo info = w.getChunkInfo();

		if (infoByOrder[typeCode] == null)
			infoByOrder[typeCode] = new ArrayList<ChunkInfo>();
		infoByOrder[typeCode].add(info);
		infoByKey.put(key

		if (w.getChunkMeta() != null)
			chunkMeta.put(key

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

	private long makeObjectPointer(ChunkFormatter w
		List<ChunkInfo> list = infoByOrder[typeCode];
		int idx = list == null ? 0 : list.size();
		int ptr = w.position();
		return (((long) typeCode) << 61) | (((long) idx) << 32) | ptr;
	}

	private static int typeOf(long objectPtr) {
		return (int) (objectPtr >>> 61);
	}

	private static int chunkIdx(long objectPtr) {
		return ((int) ((objectPtr << 3) >>> (32 + 3)));
	}

	private static int offsetOf(long objectPtr) {
		return (int) objectPtr;
	}

	private boolean isInCurrentChunk(long objectPtr) {
		List<ChunkInfo> list = infoByOrder[typeOf(objectPtr)];
		if (list == null)
			return chunkIdx(objectPtr) == 0;
		return chunkIdx(objectPtr) == list.size();
	}

	private ChunkKey chunkOf(long objectPtr) throws DhtException {
		List<ChunkInfo> list = infoByOrder[typeOf(objectPtr)];
		int idx = chunkIdx(objectPtr);
		if (list == null || list.size() <= idx) {
			throw new DhtException(MessageFormat.format(
					DhtText.get().packParserInvalidPointer
					Constants.typeString(typeOf(objectPtr))
					Integer.valueOf(idx)
					Integer.valueOf(offsetOf(objectPtr))));
		}
		return list.get(idx).getChunkKey();
	}

	private static DhtException panicCannotInsert() {
		return new DhtException(DhtText.get().cannotInsertObject);
	}

	static class DhtInfo extends PackedObjectInfo {
		private static final int REFERENCED = 1 << 3;

		long chunkPtr;

		long packedSize;

		long inflatedSize;

		ObjectId base;

		DhtInfo(AnyObjectId id) {
			super(id);
		}

		boolean isInPack() {
			return chunkPtr != 0;
		}

		boolean isReferenced() {
			return (getCRC() & REFERENCED) != 0;
		}

		void setReferenced() {
			setCRC(getCRC() | REFERENCED);
		}

		int getType() {
			return getCRC() & 7;
		}

		void setType(int type) {
			setCRC((getCRC() & ~7) | type);
		}

		ObjectInfo info(ChunkKey chunkKey) {
			return new ObjectInfo(chunkKey
					packedSize
		}
	}

	static class DhtDelta extends UnresolvedDelta {
		long chunkPtr;

		long packedSize;

		long inflatedSize;

		int getType() {
			return getCRC() & 7;
		}

		void setType(int type) {
			setCRC((getCRC() & ~7) | type);
		}
	}
}
