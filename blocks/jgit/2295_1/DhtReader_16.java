
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TAG;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.storage.file.PackLock;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.LongList;

class DhtPackParser extends PackParser {
	private final RepositoryKey repo;

	private final Database db;

	private final WriteBuffer buffer;

	private final DhtInserterOptions options;

	private final MessageDigest digest;

	private ChunkWriter[] writers;

	private List<ChunkKey>[] keys;

	private LongList objStreamPositions;

	private LongList objChunkPositions;

	private ChunkWriter currWriter;

	private long currStreamPos;

	private long currChunkPos;

	private int currDataPos;

	private boolean currReuseHeader;

	private long currBaseKey;

	private long currDeltaSize;

	private long currDataSize;

	private List<ChunkKey> currFragments;

	private PackChunk dbChunk;

	private int dbPtr;

	private LinkedHashMap<ChunkKey

	DhtPackParser(DhtObjDatabase objdb
			Database db
		super(objdb
		this.repo = repo;
		this.db = db;
		this.buffer = buffer;
		this.options = options;
		this.digest = Constants.newMessageDigest();

		writers = new ChunkWriter[5];
		keys = new List[5];

		final int max = 4;
		recentChunks = new LinkedHashMap<ChunkKey
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean removeEldestEntry(Entry<ChunkKey
				return max < size();
			}
		};
	}

	@Override
	public PackLock parse(ProgressMonitor progress) throws IOException {
		PackLock lock = super.parse(progress);

		putIndex();
		buffer.flush();

		objStreamPositions = null;
		objChunkPositions = null;
		writers = null;
		keys = null;
		recentChunks = null;

		return lock;
	}

	private void putIndex() throws DhtException {
		final int objCnt = getObjectCount();
		if (objCnt == 0)
			return;

		for (int i = 0; i < objCnt; i++) {
			PackedObjectInfo oe = getObject(i);
			oe.setOffset(((DhtInfo) oe).chunkPos);
		}

		List<PackedObjectInfo> all = getSortedObjectList(new Comparator<PackedObjectInfo>() {
			public int compare(PackedObjectInfo a
				return Long.signum(a.getOffset() - b.getOffset());
			}
		});

		int beginIdx = 0;
		ChunkKey key = chunkOf(all.get(0).getOffset());
		linkToChunk((DhtInfo) all.get(0)

		for (int i = 1; i < all.size(); i++) {
			DhtInfo oe = (DhtInfo) all.get(i);
			ChunkKey oeKey = chunkOf(oe.getOffset());
			if (!key.equals(oeKey)) {
				putIndex(all
				beginIdx = i;
				key = oeKey;
			}
			linkToChunk(oe
		}
		putIndex(all
	}

	private void linkToChunk(DhtInfo o
		db.objectIndex().add(ObjectIndexKey.create(repo
	}

	private void putIndex(List<PackedObjectInfo> all
			throws DhtException {
		db.chunks().putIndex(key
	}

	@Override
	protected PackedObjectInfo newInfo(AnyObjectId id
			ObjectId baseId) {
		DhtInfo obj = new DhtInfo(id);
		if (delta != null) {
			DhtDelta d = (DhtDelta) delta;
			obj.chunkPos = d.chunkPos;
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

		objStreamPositions = new LongList((int) objCnt);
		objChunkPositions = new LongList((int) objCnt);
	}

	@Override
	protected void onBeginWholeObject(long streamPosition
			long inflatedSize) throws IOException {
		currStreamPos = streamPosition;
		currDataSize = 0;

		ChunkWriter w = begin(type);
		if (!w.whole(type
			flush(type);
			w = begin(type);
			if (!w.whole(type
				throw panicCannotInsert();
		}

		currDataPos = w.position();
		currReuseHeader = true;
	}

	@Override
	protected void onEndWholeObject(PackedObjectInfo info) throws IOException {
		if (currFragments != null)
			finishFragmentedObject();

		objStreamPositions.add(currStreamPos);
		objChunkPositions.add(currChunkPos);

		DhtInfo oe = (DhtInfo) info;
		oe.chunkPos = currChunkPos;
		oe.setSize((int) Math.min(currDataSize
	}

	@Override
	protected void onBeginOfsDelta(long deltaPos
			throws IOException {
		currStreamPos = deltaPos;
		currDataSize = 0;

		int baseIdx = findStreamPosition(basePos);
		long baseKey = objChunkPositions.get(baseIdx);
		int type = typeOf(baseKey);
		ChunkWriter w = begin(type);

		if (isCurrentChunk(baseKey)) {
			if (w.ofsDelta(sz
				currDataPos = w.position();
				currBaseKey = baseKey;
				currDeltaSize = sz;
				currReuseHeader = false;
				return;
			}

			flush(type);
			w = begin(type);
		}

		ChunkKey baseChunk = chunkOf(baseKey);
		if (!w.chunkDelta(sz
			flush(type);
			w = begin(type);
			if (!w.chunkDelta(sz
				throw panicCannotInsert();
		}

		currDataPos = w.position();
		currReuseHeader = true;
	}

	@Override
	protected void onBeginRefDelta(long deltaPos
			throws IOException {
		currStreamPos = deltaPos;
		currDataSize = 0;

		int type = OBJ_BLOB;
		ChunkWriter w = begin(type);
		if (!w.refDelta(sz
			flush(type);
			w = begin(type);
			if (!w.refDelta(sz
				throw panicCannotInsert();
		}

		currDataPos = w.position();
		currReuseHeader = true;
	}

	@Override
	protected DhtDelta onEndDelta() throws IOException {
		if (currFragments != null)
			finishFragmentedObject();

		objStreamPositions.add(currStreamPos);
		objChunkPositions.add(currChunkPos);

		DhtDelta delta = new DhtDelta();
		delta.chunkPos = currChunkPos;
		delta.setSize((int) Math.min(currDataSize
		return delta;
	}

	@Override
	protected void onObjectData(Source src
			throws IOException {
		if (src != Source.INPUT)
			return;

		currDataSize += len;

		if (currWriter.append(raw
			return;

		if (currWriter.getObjectCount() == 1)
			currFragments = new LinkedList<ChunkKey>();
		if (currFragments != null) {
			appendToFragment(raw
			return;
		}

		final ChunkWriter oldWriter = currWriter;
		final long oldPos = currChunkPos;
		final int type = typeOf(oldPos);
		int dataPos = currDataPos;
		int dataEnd = currWriter.position();

		flush(type);
		currWriter = begin(type);

		if (currReuseHeader) {
			dataPos = offsetOf(oldPos);
		} else {
			if (currWriter == oldWriter && dataPos < 64) {
				writers[type] = null;
				currWriter = begin(type);
			}

			ChunkKey c = chunkOf(currBaseKey);
			if (!currWriter.chunkDelta(currDeltaSize
				throw panicCannotInsert();
		}

		if (!currWriter.append(oldWriter

		if (!currWriter.append(raw
			currFragments = new LinkedList<ChunkKey>();
			appendToFragment(raw
		}
	}

	private void appendToFragment(byte[] raw
			throws DhtException {
		while (0 < len) {
			if (currWriter.free() == 0)
				currFragments.add(flush(typeOf(currChunkPos)));
			int n = Math.min(len
			currWriter.append(raw
			pos += n;
			len -= n;
		}
	}

	private void finishFragmentedObject() throws DhtException {
		ChunkKey key = flush(typeOf(currChunkPos));
		if (key != null)
			currFragments.add(key);

		byte[] bin = ChunkFragments.toByteArray(currFragments);
		for (ChunkKey k : currFragments)
			db.chunks().putFragments(k
		currFragments = null;
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(PackedObjectInfo obj
			ObjectTypeAndSize info) throws IOException {
		return seekDatabase(((DhtInfo) obj).chunkPos
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(UnresolvedDelta delta
			ObjectTypeAndSize info) throws IOException {
		return seekDatabase(((DhtDelta) delta).chunkPos
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

		if (dbPtr != dbChunk.size())

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
			dbChunk = recentChunks.get(key);
			if (dbChunk == null) {
				buffer.flush();

				Collection<PackChunk> found;
				Sync<Collection<PackChunk>> sync = Sync.create();
				db.chunks().get(Collections.singleton(key)
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

				dbChunk = found.iterator().next();
				if (cache)
					recentChunks.put(key
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

		flush(OBJ_COMMIT);
		flush(OBJ_TREE);
		flush(OBJ_BLOB);
		flush(OBJ_TAG);
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

	private ChunkWriter begin(int typeCode) {
		final ChunkWriter w = writer(typeCode);
		currWriter = w;
		currChunkPos = position(w
		return w;
	}

	private ChunkKey flush(int typeCode) throws DhtException {
		ChunkWriter w = writers[typeCode];
		if (w == null)
			return null;

		Map<ChunkKey
		if (currFragments == null)
			cache = recentChunks;
		ChunkKey key = w.putData(digest
		if (key == null)
			return null;

		keys(typeCode).add(key);
		return key;
	}

	private List<ChunkKey> keys(int typeCode) {
		List<ChunkKey> list = keys[typeCode];
		if (list == null)
			keys[typeCode] = list = new ArrayList<ChunkKey>(4);
		return list;
	}

	private ChunkWriter writer(int typeCode) {
		ChunkWriter w = writers[typeCode];
		if (w == null)
			writers[typeCode] = w = newWriter();
		return w;
	}

	private int findStreamPosition(long streamPosition) throws DhtException {
		int high = objStreamPositions.size();
		int low = 0;
		do {
			final int mid = (low + high) >>> 1;
			final long pos = objStreamPositions.get(mid);
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

	private long position(ChunkWriter w
		List<ChunkKey> list = keys(typeCode);
		int idx = list.size();
		int ptr = w.position();
		return (((long) typeCode) << 61) | (((long) idx) << 32) | ptr;
	}

	private static int typeOf(long position) {
		return (int) (position >>> 61);
	}

	private boolean isCurrentChunk(long position) {
		List<ChunkKey> list = keys(typeOf(position));
		int idx = ((int) ((position << 3) >>> (32 + 3)));
		return idx == list.size();
	}

	private ChunkKey chunkOf(long position) {
		List<ChunkKey> list = keys(typeOf(position));
		int idx = ((int) ((position << 3) >>> (32 + 3)));
		return list.get(idx);
	}

	private static int offsetOf(long position) {
		return (int) position;
	}

	private ChunkWriter newWriter() {
		return new ChunkWriter(repo
	}

	private static DhtException panicCannotInsert() {
		return new DhtException(DhtText.get().cannotInsertObject);
	}

	static class DhtInfo extends PackedObjectInfo {
		long chunkPos;

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

		ChunkLink link(ChunkKey chunkKey) {
			int pos = offsetOf(chunkPos);
			return new ChunkLink(chunkKey
		}
	}

	static class DhtDelta extends UnresolvedDelta {
		long chunkPos;


		int getSize() {
			return getCRC();
		}

		void setSize(int size) {
			setCRC(size);
		}
	}
}
