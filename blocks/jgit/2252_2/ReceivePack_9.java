
package org.eclipse.jgit.transport;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.InflaterCache;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSubclassMap;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.storage.file.PackLock;
import org.eclipse.jgit.storage.pack.BinaryDelta;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;

public abstract class PackParser {
	private static final int BUFFER_SIZE = 8192;

	public static enum Source {
		INPUT

		DATABASE;
	}

	private final ObjectDatabase objectDatabase;

	private InflaterStream inflater;

	private byte[] tempBuffer;

	private byte[] hdrBuf;

	private final MessageDigest objectDigest;

	private final MutableObjectId tempObjectId;

	private InputStream in;

	private byte[] buf;

	private long bBase;

	private int bOffset;

	private int bAvail;

	private ObjectChecker objCheck;

	private boolean allowThin;

	private boolean needBaseObjectIds;

	private long objectCount;

	private PackedObjectInfo[] entries;

	private ObjectIdSubclassMap<ObjectId> newObjectIds;

	private int deltaCount;

	private int entryCount;

	private ObjectIdSubclassMap<DeltaChain> baseById;

	private ObjectIdSubclassMap<ObjectId> baseObjectIds;

	private LongMap<UnresolvedDelta> baseByPos;

	private List<PackedObjectInfo> deferredCheckBlobs;

	private MessageDigest packDigest;

	private ObjectReader readCurs;

	private String lockMessage;

	protected PackParser(final ObjectDatabase odb
		objectDatabase = odb.newCachedDatabase();
		in = src;

		inflater = new InflaterStream();
		readCurs = objectDatabase.newReader();
		buf = new byte[BUFFER_SIZE];
		tempBuffer = new byte[BUFFER_SIZE];
		hdrBuf = new byte[64];
		objectDigest = Constants.newMessageDigest();
		tempObjectId = new MutableObjectId();
		packDigest = Constants.newMessageDigest();
	}

	public boolean isAllowThin() {
		return allowThin;
	}

	public void setAllowThin(final boolean allow) {
		allowThin = allow;
	}

	public void setNeedNewObjectIds(boolean b) {
		if (b)
			newObjectIds = new ObjectIdSubclassMap<ObjectId>();
		else
			newObjectIds = null;
	}

	private boolean needNewObjectIds() {
		return newObjectIds != null;
	}

	public void setNeedBaseObjectIds(boolean b) {
		this.needBaseObjectIds = b;
	}

	public ObjectIdSubclassMap<ObjectId> getNewObjectIds() {
		if (newObjectIds != null)
			return newObjectIds;
		return new ObjectIdSubclassMap<ObjectId>();
	}

	public ObjectIdSubclassMap<ObjectId> getBaseObjectIds() {
		if (baseObjectIds != null)
			return baseObjectIds;
		return new ObjectIdSubclassMap<ObjectId>();
	}

	public void setObjectChecker(final ObjectChecker oc) {
		objCheck = oc;
	}

	public void setObjectChecking(final boolean on) {
		setObjectChecker(on ? new ObjectChecker() : null);
	}

	public String getLockMessage() {
		return lockMessage;
	}

	public void setLockMessage(String msg) {
		lockMessage = msg;
	}

	public int getObjectCount() {
		return entryCount;
	}

	public PackedObjectInfo getObject(int nth) {
		return entries[nth];
	}

	public List<PackedObjectInfo> getSortedObjectList(
			Comparator<PackedObjectInfo> cmp) {
		Arrays.sort(entries
		List<PackedObjectInfo> list = Arrays.asList(entries);
		if (entryCount < entries.length)
			list = list.subList(0
		return list;
	}

	public PackLock parse(ProgressMonitor progress) throws IOException {
		if (progress == null)
			progress = NullProgressMonitor.INSTANCE;
		try {
			readPackHeader();

			entries = new PackedObjectInfo[(int) objectCount];
			baseById = new ObjectIdSubclassMap<DeltaChain>();
			baseByPos = new LongMap<UnresolvedDelta>();
			deferredCheckBlobs = new ArrayList<PackedObjectInfo>();

			progress.beginTask(JGitText.get().receivingObjects
					(int) objectCount);
			for (int done = 0; done < objectCount; done++) {
				indexOneObject();
				progress.update(1);
				if (progress.isCancelled())
					throw new IOException(JGitText.get().downloadCancelled);
			}
			readPackFooter();
			endInput();
			if (!deferredCheckBlobs.isEmpty())
				doDeferredCheckBlobs();
			progress.endTask();
			if (deltaCount > 0) {
				resolveDeltas(progress);
				if (entryCount < objectCount) {
					if (!isAllowThin()) {
						throw new IOException(MessageFormat.format(JGitText
								.get().packHasUnresolvedDeltas
								(objectCount - entryCount)));
					}

					resolveDeltasWithExternalBases(progress);
				}
			}

			packDigest = null;
			baseById = null;
			baseByPos = null;
		} finally {
			try {
				if (readCurs != null)
					readCurs.release();
			} finally {
				readCurs = null;
			}

			try {
				inflater.release();
			} finally {
				inflater = null;
				objectDatabase.close();
			}

			progress.endTask();
		}
	}

	private void resolveDeltas(final ProgressMonitor progress)
			throws IOException {
		progress.beginTask(JGitText.get().resolvingDeltas
		final int last = entryCount;
		for (int i = 0; i < last; i++) {
			final int before = entryCount;
			resolveDeltas(entries[i]);
			progress.update(entryCount - before);
			if (progress.isCancelled())
				throw new IOException(
						JGitText.get().downloadCancelledDuringIndexing);
		}
		progress.endTask();
	}

	private void resolveDeltas(final PackedObjectInfo oe) throws IOException {
		UnresolvedDelta children = firstChildOf(oe);
		if (children == null)
			return;

		DeltaVisit visit = new DeltaVisit();
		visit.nextChild = children;

		ObjectTypeAndSize info = openDatabase(oe
		switch (info.type) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			visit.data = inflateAndReturn(Source.DATABASE
			visit.id = oe;
			break;
		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().unknownObjectType
		}

		if (!checkCRC(oe.getCRC())) {
			throw new IOException(MessageFormat.format(
					JGitText.get().corruptionDetectedReReadingAt
							.getOffset()));
		}

		resolveDeltas(visit.next()
	}

	private void resolveDeltas(DeltaVisit visit
			ObjectTypeAndSize info) throws IOException {
		do {
			info = openDatabase(visit.delta
			switch (info.type) {
			case Constants.OBJ_OFS_DELTA:
			case Constants.OBJ_REF_DELTA:
				break;

			default:
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownObjectType
			}

			visit.data = BinaryDelta.apply(visit.parent.data
					inflateAndReturn(Source.DATABASE

			if (!checkCRC(visit.delta.crc))
				throw new IOException(MessageFormat.format(
						JGitText.get().corruptionDetectedReReadingAt
						visit.delta.position));

			objectDigest.update(Constants.encodedTypeString(type));
			objectDigest.update((byte) ' ');
			objectDigest.update(Constants.encodeASCII(visit.data.length));
			objectDigest.update((byte) 0);
			objectDigest.update(visit.data);
			tempObjectId.fromRaw(objectDigest.digest()

			verifySafeObject(tempObjectId

			PackedObjectInfo oe;
			oe = newInfo(tempObjectId
			oe.setOffset(visit.delta.position);
			addObjectAndTrack(oe);
			visit.id = oe;

			visit.nextChild = firstChildOf(oe);
			visit = visit.next();
		} while (visit != null);
	}

	protected ObjectTypeAndSize readObjectHeader(ObjectTypeAndSize info)
			throws IOException {
		int hdrPtr = 0;
		int c = readFrom(Source.DATABASE);
		hdrBuf[hdrPtr++] = (byte) c;

		info.type = (c >> 4) & 7;
		long sz = c & 15;
		int shift = 4;
		while ((c & 0x80) != 0) {
			c = readFrom(Source.DATABASE);
			hdrBuf[hdrPtr++] = (byte) c;
			sz += (c & 0x7f) << shift;
			shift += 7;
		}
		info.size = sz;

		switch (info.type) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			onObjectHeader(Source.DATABASE
			break;

		case Constants.OBJ_OFS_DELTA:
			c = readFrom(Source.DATABASE);
			hdrBuf[hdrPtr++] = (byte) c;
			while ((c & 128) != 0) {
				c = readFrom(Source.DATABASE);
				hdrBuf[hdrPtr++] = (byte) c;
			}
			onObjectHeader(Source.DATABASE
			break;

		case Constants.OBJ_REF_DELTA:
			System.arraycopy(buf
			hdrPtr += 20;
			use(20);
			onObjectHeader(Source.DATABASE
			break;

		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().unknownObjectType
		}
		return info;
	}

	private UnresolvedDelta removeBaseById(final AnyObjectId id) {
		final DeltaChain d = baseById.get(id);
		return d != null ? d.remove() : null;
	}

	private static UnresolvedDelta reverse(UnresolvedDelta c) {
		UnresolvedDelta tail = null;
		while (c != null) {
			final UnresolvedDelta n = c.next;
			c.next = tail;
			tail = c;
			c = n;
		}
		return tail;
	}

	private UnresolvedDelta firstChildOf(PackedObjectInfo oe) {
		UnresolvedDelta a = reverse(removeBaseById(oe));
		UnresolvedDelta b = reverse(baseByPos.remove(oe.getOffset()));

		if (a == null)
			return b;
		if (b == null)
			return a;

		UnresolvedDelta first = null;
		UnresolvedDelta last = null;
		while (a != null || b != null) {
			UnresolvedDelta curr;
			if (b == null || (a != null && a.position < b.position)) {
				curr = a;
				a = a.next;
			} else {
				curr = b;
				b = b.next;
			}
			if (last != null)
				last.next = curr;
			else
				first = curr;
			last = curr;
			curr.next = null;
		}
		return first;
	}

	private void resolveDeltasWithExternalBases(final ProgressMonitor progress)
			throws IOException {
		growEntries(baseById.size());

		if (needBaseObjectIds)
			baseObjectIds = new ObjectIdSubclassMap<ObjectId>();

		final List<DeltaChain> missing = new ArrayList<DeltaChain>(64);
		for (final DeltaChain baseId : baseById) {
			if (baseId.head == null)
				continue;

			if (needBaseObjectIds)
				baseObjectIds.add(baseId);

			final ObjectLoader ldr;
			try {
				ldr = readCurs.open(baseId);
			} catch (MissingObjectException notFound) {
				missing.add(baseId);
				continue;
			}

			final DeltaVisit visit = new DeltaVisit();
			visit.data = ldr.getCachedBytes(Integer.MAX_VALUE);
			visit.id = baseId;
			final int typeCode = ldr.getType();
			final PackedObjectInfo oe = newInfo(baseId

			if (onAppendBase(typeCode
				entries[entryCount++] = oe;

			visit.nextChild = firstChildOf(oe);
			resolveDeltas(visit.next()

			if (progress.isCancelled())
				throw new IOException(
						JGitText.get().downloadCancelledDuringIndexing);
		}

		for (final DeltaChain base : missing) {
			if (base.head != null)
				throw new MissingObjectException(base
		}

		onEndThinPack();
	}

	private void growEntries(int extraObjects) {
		final PackedObjectInfo[] ne;

		ne = new PackedObjectInfo[(int) objectCount + extraObjects];
		System.arraycopy(entries
		entries = ne;
	}

	private void readPackHeader() throws IOException {
		final int hdrln = Constants.PACK_SIGNATURE.length + 4 + 4;
		final int p = fill(Source.INPUT
		for (int k = 0; k < Constants.PACK_SIGNATURE.length; k++)
			if (buf[p + k] != Constants.PACK_SIGNATURE[k])
				throw new IOException(JGitText.get().notAPACKFile);

		final long vers = NB.decodeUInt32(buf
		if (vers != 2 && vers != 3)
			throw new IOException(MessageFormat.format(
					JGitText.get().unsupportedPackVersion
		objectCount = NB.decodeUInt32(buf
		use(hdrln);
	}

	private void readPackFooter() throws IOException {
		sync();
		final byte[] actHash = packDigest.digest();

		final int c = fill(Source.INPUT
		final byte[] srcHash = new byte[20];
		System.arraycopy(buf
		use(20);

		if (!Arrays.equals(actHash
			throw new CorruptObjectException(
					JGitText.get().corruptObjectPackfileChecksumIncorrect);

		onPackFooter(srcHash);
	}

	private void endInput() {
		in = null;
	}

	private void indexOneObject() throws IOException {
		final long streamPosition = streamPosition();

		int hdrPtr = 0;
		int c = readFrom(Source.INPUT);
		hdrBuf[hdrPtr++] = (byte) c;

		final int typeCode = (c >> 4) & 7;
		long sz = c & 15;
		int shift = 4;
		while ((c & 0x80) != 0) {
			c = readFrom(Source.INPUT);
			hdrBuf[hdrPtr++] = (byte) c;
			sz += (c & 0x7f) << shift;
			shift += 7;
		}

		switch (typeCode) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			onBeginWholeObject(streamPosition
			onObjectHeader(Source.INPUT
			whole(streamPosition
			break;

		case Constants.OBJ_OFS_DELTA: {
			c = readFrom(Source.INPUT);
			hdrBuf[hdrPtr++] = (byte) c;
			long ofs = c & 127;
			while ((c & 128) != 0) {
				ofs += 1;
				c = readFrom(Source.INPUT);
				hdrBuf[hdrPtr++] = (byte) c;
				ofs <<= 7;
				ofs += (c & 127);
			}
			final long base = streamPosition - ofs;
			onBeginOfsDelta(streamPosition
			onObjectHeader(Source.INPUT
			inflateAndSkip(Source.INPUT
			UnresolvedDelta n = onEndDelta();
			n.position = streamPosition;
			n.next = baseByPos.put(base
			deltaCount++;
			break;
		}

		case Constants.OBJ_REF_DELTA: {
			c = fill(Source.INPUT
			final ObjectId base = ObjectId.fromRaw(buf
			System.arraycopy(buf
			hdrPtr += 20;
			use(20);
			DeltaChain r = baseById.get(base);
			if (r == null) {
				r = new DeltaChain(base);
				baseById.add(r);
			}
			onBeginRefDelta(streamPosition
			onObjectHeader(Source.INPUT
			inflateAndSkip(Source.INPUT
			UnresolvedDelta n = onEndDelta();
			n.position = streamPosition;
			r.add(n);
			deltaCount++;
			break;
		}

		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().unknownObjectType
		}
	}

	private void whole(final long pos
			throws IOException {
		objectDigest.update(Constants.encodedTypeString(type));
		objectDigest.update((byte) ' ');
		objectDigest.update(Constants.encodeASCII(sz));
		objectDigest.update((byte) 0);

		boolean checkContentLater = false;
		if (type == Constants.OBJ_BLOB) {
			byte[] readBuffer = buffer();
			InputStream inf = inflate(Source.INPUT
			long cnt = 0;
			while (cnt < sz) {
				int r = inf.read(readBuffer);
				if (r <= 0)
					break;
				objectDigest.update(readBuffer
				cnt += r;
			}
			inf.close();
			tempObjectId.fromRaw(objectDigest.digest()
			checkContentLater = readCurs.has(tempObjectId);

		} else {
			final byte[] data = inflateAndReturn(Source.INPUT
			objectDigest.update(data);
			tempObjectId.fromRaw(objectDigest.digest()
			verifySafeObject(tempObjectId
		}

		PackedObjectInfo obj = newInfo(tempObjectId
		obj.setOffset(pos);
		onEndWholeObject(obj);
		addObjectAndTrack(obj);
		if (checkContentLater)
			deferredCheckBlobs.add(obj);
	}

	private void verifySafeObject(final AnyObjectId id
			final byte[] data) throws IOException {
		if (objCheck != null) {
			try {
				objCheck.check(type
			} catch (CorruptObjectException e) {
				throw new IOException(MessageFormat.format(
						JGitText.get().invalidObject
								.typeString(type)
			}
		}

		try {
			final ObjectLoader ldr = readCurs.open(id
			final byte[] existingData = ldr.getCachedBytes(data.length);
			if (!Arrays.equals(data
				throw new IOException(MessageFormat.format(
						JGitText.get().collisionOn
			}
		} catch (MissingObjectException notLocal) {
		}
	}

	private void doDeferredCheckBlobs() throws IOException {
		final byte[] readBuffer = buffer();
		final byte[] curBuffer = new byte[readBuffer.length];
		ObjectTypeAndSize info = new ObjectTypeAndSize();

		for (PackedObjectInfo obj : deferredCheckBlobs) {
			info = openDatabase(obj

			if (info.type != Constants.OBJ_BLOB)
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownObjectType

			ObjectStream cur = readCurs.open(obj
			try {
				long sz = info.size;
				if (cur.getSize() != sz)
					throw new IOException(MessageFormat.format(
							JGitText.get().collisionOn
				InputStream pck = inflate(Source.DATABASE
				while (0 < sz) {
					int n = (int) Math.min(readBuffer.length
					IO.readFully(cur
					IO.readFully(pck
					for (int i = 0; i < n; i++) {
						if (curBuffer[i] != readBuffer[i])
							throw new IOException(MessageFormat.format(JGitText
									.get().collisionOn
					}
					sz -= n;
				}
				pck.close();
			} finally {
				cur.close();
			}
		}
	}

	private long streamPosition() {
		return bBase + bOffset;
	}

	private ObjectTypeAndSize openDatabase(PackedObjectInfo obj
			ObjectTypeAndSize info) throws IOException {
		bOffset = 0;
		bAvail = 0;
		return seekDatabase(obj
	}

	private ObjectTypeAndSize openDatabase(UnresolvedDelta delta
			ObjectTypeAndSize info) throws IOException {
		bOffset = 0;
		bAvail = 0;
		return seekDatabase(delta
	}

	private int readFrom(final Source src) throws IOException {
		if (bAvail == 0)
			fill(src
		bAvail--;
		return buf[bOffset++] & 0xff;
	}

	private void use(final int cnt) {
		bOffset += cnt;
		bAvail -= cnt;
	}

	private int fill(final Source src
		while (bAvail < need) {
			int next = bOffset + bAvail;
			int free = buf.length - next;
			if (free + bAvail < need) {
				switch (src) {
				case INPUT:
					sync();
					break;
				case DATABASE:
					if (bAvail > 0)
						System.arraycopy(buf
					bOffset = 0;
					break;
				}
				next = bAvail;
				free = buf.length - next;
			}
			switch (src) {
			case INPUT:
				next = in.read(buf
				break;
			case DATABASE:
				next = readDatabase(buf
				break;
			}
			if (next <= 0)
				throw new EOFException(JGitText.get().packfileIsTruncated);
			bAvail += next;
		}
		return bOffset;
	}

	private void sync() throws IOException {
		packDigest.update(buf
		onStoreStream(buf
		if (bAvail > 0)
			System.arraycopy(buf
		bBase += bOffset;
		bOffset = 0;
	}

	protected byte[] buffer() {
		return tempBuffer;
	}

	protected PackedObjectInfo newInfo(AnyObjectId id
			ObjectId deltaBase) {
		PackedObjectInfo oe = new PackedObjectInfo(id);
		if (delta != null)
			oe.setCRC(delta.crc);
		return oe;
	}

	protected abstract void onStoreStream(byte[] raw
			throws IOException;

	protected abstract void onObjectHeader(Source src
			int len) throws IOException;

	protected abstract void onObjectData(Source src
			int len) throws IOException;

	protected abstract void onPackFooter(byte[] hash) throws IOException;

	protected abstract boolean onAppendBase(int typeCode
			PackedObjectInfo info) throws IOException;

	protected abstract void onEndThinPack() throws IOException;

	protected abstract ObjectTypeAndSize seekDatabase(PackedObjectInfo obj
			ObjectTypeAndSize info) throws IOException;

	protected abstract ObjectTypeAndSize seekDatabase(UnresolvedDelta delta
			ObjectTypeAndSize info) throws IOException;

	protected abstract int readDatabase(byte[] dst
			throws IOException;

	protected abstract boolean checkCRC(int oldCRC);

	protected abstract void onBeginWholeObject(long streamPosition
			long inflatedSize) throws IOException;

	protected abstract void onEndWholeObject(PackedObjectInfo info)
			throws IOException;

	protected abstract void onBeginOfsDelta(long deltaStreamPosition
			long baseStreamPosition

	protected abstract void onBeginRefDelta(long deltaStreamPosition
			AnyObjectId baseId

	protected UnresolvedDelta onEndDelta() throws IOException {
		return new UnresolvedDelta();
	}

	public static class ObjectTypeAndSize {
		public int type;

		public long size;
	}

	private void inflateAndSkip(final Source src
			throws IOException {
		final InputStream inf = inflate(src
		IO.skipFully(inf
		inf.close();
	}

	private byte[] inflateAndReturn(final Source src
			throws IOException {
		final byte[] dst = new byte[(int) inflatedSize];
		final InputStream inf = inflate(src
		IO.readFully(inf
		inf.close();
		return dst;
	}

	private InputStream inflate(final Source src
			throws IOException {
		inflater.open(src
		return inflater;
	}

	private static class DeltaChain extends ObjectId {
		UnresolvedDelta head;

		DeltaChain(final AnyObjectId id) {
			super(id);
		}

		UnresolvedDelta remove() {
			final UnresolvedDelta r = head;
			if (r != null)
				head = null;
			return r;
		}

		void add(final UnresolvedDelta d) {
			d.next = head;
			head = d;
		}
	}

	public static class UnresolvedDelta {
		long position;

		int crc;

		UnresolvedDelta next;

		public long getOffset() {
			return position;
		}

		public int getCRC() {
			return crc;
		}

		public void setCRC(int crc32) {
			crc = crc32;
		}
	}

	private static class DeltaVisit {
		final UnresolvedDelta delta;

		ObjectId id;

		byte[] data;

		DeltaVisit parent;

		UnresolvedDelta nextChild;

		DeltaVisit() {
		}

		DeltaVisit(DeltaVisit parent) {
			this.parent = parent;
			this.delta = parent.nextChild;
			parent.nextChild = delta.next;
		}

		DeltaVisit next() {
			if (parent != null && parent.nextChild == null) {
				parent.data = null;
				parent = parent.parent;
			}

			if (nextChild != null)
				return new DeltaVisit(this);

			if (parent != null)
				return new DeltaVisit(parent);
			return null;
		}
	}

	private void addObjectAndTrack(PackedObjectInfo oe) {
		entries[entryCount++] = oe;
		if (needNewObjectIds())
			newObjectIds.add(oe);
	}

	private class InflaterStream extends InputStream {
		private final Inflater inf;

		private final byte[] skipBuffer;

		private Source src;

		private long expectedSize;

		private long actualSize;

		private int p;

		InflaterStream() {
			inf = InflaterCache.get();
			skipBuffer = new byte[512];
		}

		void release() {
			inf.reset();
			InflaterCache.release(inf);
		}

		void open(Source source
			src = source;
			expectedSize = inflatedSize;
			actualSize = 0;

			p = fill(src
			inf.setInput(buf
		}

		@Override
		public long skip(long toSkip) throws IOException {
			long n = 0;
			while (n < toSkip) {
				final int cnt = (int) Math.min(skipBuffer.length
				final int r = read(skipBuffer
				if (r <= 0)
					break;
				n += r;
			}
			return n;
		}

		@Override
		public int read() throws IOException {
			int n = read(skipBuffer
			return n == 1 ? skipBuffer[0] & 0xff : -1;
		}

		@Override
		public int read(byte[] dst
			try {
				int n = 0;
				while (n < cnt) {
					int r = inf.inflate(dst
					if (r == 0) {
						if (inf.finished())
							break;
						if (inf.needsInput()) {
							onObjectData(src
							use(bAvail);

							p = fill(src
							inf.setInput(buf
						} else {
							throw new CorruptObjectException(
									MessageFormat
											.format(
													JGitText.get().packfileCorruptionDetected
													JGitText.get().unknownZlibError));
						}
					} else {
						n += r;
					}
				}
				actualSize += n;
				return 0 < n ? n : -1;
			} catch (DataFormatException dfe) {
				throw new CorruptObjectException(MessageFormat.format(JGitText
						.get().packfileCorruptionDetected
			}
		}

		@Override
		public void close() throws IOException {
			if (read(skipBuffer) != -1 || actualSize != expectedSize) {
				throw new CorruptObjectException(MessageFormat.format(JGitText
						.get().packfileCorruptionDetected
						JGitText.get().wrongDecompressedLength));
			}

			int used = bAvail - inf.getRemaining();
			if (0 < used) {
				onObjectData(src
				use(used);
			}

			inf.reset();
		}
	}
}
