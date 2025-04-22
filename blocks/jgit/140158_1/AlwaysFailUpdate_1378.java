
package org.eclipse.jgit.internal.storage.reftable;

import static java.lang.Math.log;
import static org.eclipse.jgit.internal.storage.reftable.BlockWriter.padBetweenBlocks;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_FOOTER_LEN;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_HEADER_LEN;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_HEADER_MAGIC;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.INDEX_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.LOG_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.MAX_BLOCK_SIZE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.MAX_RESTARTS;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.OBJ_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.REF_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VERSION_1;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.CRC32;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.storage.reftable.BlockWriter.DeleteLogEntry;
import org.eclipse.jgit.internal.storage.reftable.BlockWriter.Entry;
import org.eclipse.jgit.internal.storage.reftable.BlockWriter.IndexEntry;
import org.eclipse.jgit.internal.storage.reftable.BlockWriter.LogEntry;
import org.eclipse.jgit.internal.storage.reftable.BlockWriter.ObjEntry;
import org.eclipse.jgit.internal.storage.reftable.BlockWriter.RefEntry;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.lib.ObjectIdSubclassMap;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.util.LongList;
import org.eclipse.jgit.util.NB;

public class ReftableWriter {
	private ReftableConfig config;
	private int refBlockSize;
	private int logBlockSize;
	private int restartInterval;
	private int maxIndexLevels;
	private boolean alignBlocks;
	private boolean indexObjects;

	private long minUpdateIndex;
	private long maxUpdateIndex;

	private ReftableOutputStream out;
	private ObjectIdSubclassMap<RefList> obj2ref;

	private BlockWriter cur;
	private Section refs;
	private Section objs;
	private Section logs;
	private int objIdLen;
	private Stats stats;

	public ReftableWriter() {
		this(new ReftableConfig());
	}

	public ReftableWriter(ReftableConfig cfg) {
		config = cfg;
	}

	public ReftableWriter setConfig(ReftableConfig cfg) {
		this.config = cfg != null ? cfg : new ReftableConfig();
		return this;
	}

	public ReftableWriter setMinUpdateIndex(long min) {
		minUpdateIndex = min;
		return this;
	}

	public ReftableWriter setMaxUpdateIndex(long max) {
		maxUpdateIndex = max;
		return this;
	}

	public ReftableWriter begin(OutputStream os) throws IOException {
		refBlockSize = config.getRefBlockSize();
		logBlockSize = config.getLogBlockSize();
		restartInterval = config.getRestartInterval();
		maxIndexLevels = config.getMaxIndexLevels();
		alignBlocks = config.isAlignBlocks();
		indexObjects = config.isIndexObjects();

		if (refBlockSize <= 0) {
			refBlockSize = 4 << 10;
		} else if (refBlockSize > MAX_BLOCK_SIZE) {
			throw new IllegalArgumentException();
		}
		if (logBlockSize <= 0) {
			logBlockSize = 2 * refBlockSize;
		}
		if (restartInterval <= 0) {
			restartInterval = refBlockSize < (60 << 10) ? 16 : 64;
		}

		out = new ReftableOutputStream(os
		refs = new Section(REF_BLOCK_TYPE);
		if (indexObjects) {
			obj2ref = new ObjectIdSubclassMap<>();
		}
		writeFileHeader();
		return this;
	}

	public ReftableWriter sortAndWriteRefs(Collection<Ref> refsToPack)
			throws IOException {
		Iterator<RefEntry> itr = refsToPack.stream()
				.map(r -> new RefEntry(r
				.sorted(Entry::compare)
				.iterator();
		while (itr.hasNext()) {
			RefEntry entry = itr.next();
			long blockPos = refs.write(entry);
			indexRef(entry.ref
		}
		return this;
	}

	public void writeRef(Ref ref) throws IOException {
		writeRef(ref
	}

	public void writeRef(Ref ref
		if (updateIndex < minUpdateIndex) {
			throw new IllegalArgumentException();
		}
		long d = updateIndex - minUpdateIndex;
		long blockPos = refs.write(new RefEntry(ref
		indexRef(ref
	}

	private void indexRef(Ref ref
		if (indexObjects && !ref.isSymbolic()) {
			indexId(ref.getObjectId()
			indexId(ref.getPeeledObjectId()
		}
	}

	private void indexId(ObjectId id
		if (id != null) {
			RefList l = obj2ref.get(id);
			if (l == null) {
				l = new RefList(id);
				obj2ref.add(l);
			}
			l.addBlock(blockPos);
		}
	}

	public void writeLog(String ref
			ObjectId oldId
					throws IOException {
		beginLog();
		logs.write(new LogEntry(ref
	}

	public void deleteLog(String ref
		beginLog();
		logs.write(new DeleteLogEntry(ref
	}

	private void beginLog() throws IOException {
		if (logs == null) {
			out.flushFileHeader();
			out.setBlockSize(logBlockSize);
			logs = new Section(LOG_BLOCK_TYPE);
		}
	}

	public long estimateTotalBytes() {
		long bytes = out.size();
		if (bytes == 0) {
			bytes += FILE_HEADER_LEN;
		}
		if (cur != null) {
			long curBlockPos = out.size();
			int sz = cur.currentSize();
			bytes += sz;

			IndexBuilder idx = null;
			if (cur.blockType() == REF_BLOCK_TYPE) {
				idx = refs.idx;
			} else if (cur.blockType() == LOG_BLOCK_TYPE) {
				idx = logs.idx;
			}
			if (idx != null && shouldHaveIndex(idx)) {
				if (idx == refs.idx) {
					bytes += out.estimatePadBetweenBlocks(sz);
				}
				bytes += idx.estimateBytes(curBlockPos);
			}
		}
		bytes += FILE_FOOTER_LEN;
		return bytes;
	}

	public ReftableWriter finish() throws IOException {
		finishRefAndObjSections();
		finishLogSection();
		writeFileFooter();
		out.finishFile();

		stats = new Stats(this
		out = null;
		obj2ref = null;
		cur = null;
		refs = null;
		objs = null;
		logs = null;
		return this;
	}

	private void finishRefAndObjSections() throws IOException {
		if (cur != null && cur.blockType() == REF_BLOCK_TYPE) {
			refs.finishSectionMaybeWriteIndex();
			if (indexObjects && !obj2ref.isEmpty() && refs.idx.bytes > 0) {
				writeObjBlocks();
			}
			obj2ref = null;
		}
	}

	private void writeObjBlocks() throws IOException {
		List<RefList> sorted = sortById(obj2ref);
		obj2ref = null;
		objIdLen = shortestUniqueAbbreviation(sorted);

		out.padBetweenBlocksToNextBlock();
		objs = new Section(OBJ_BLOCK_TYPE);
		objs.entryCnt = sorted.size();
		for (RefList l : sorted) {
			objs.write(new ObjEntry(objIdLen
		}
		objs.finishSectionMaybeWriteIndex();
	}

	private void finishLogSection() throws IOException {
		if (cur != null && cur.blockType() == LOG_BLOCK_TYPE) {
			logs.finishSectionMaybeWriteIndex();
		}
	}

	private boolean shouldHaveIndex(IndexBuilder idx) {
		int threshold;
		if (idx == refs.idx && alignBlocks) {
			threshold = 4;
		} else {
			threshold = 1;
		}
		return idx.entries.size() + (cur != null ? 1 : 0) > threshold;
	}

	private void writeFileHeader() {
		byte[] hdr = new byte[FILE_HEADER_LEN];
		encodeHeader(hdr);
		out.write(hdr
	}

	private void encodeHeader(byte[] hdr) {
		System.arraycopy(FILE_HEADER_MAGIC
		int bs = alignBlocks ? refBlockSize : 0;
		NB.encodeInt32(hdr
		NB.encodeInt64(hdr
		NB.encodeInt64(hdr
	}

	private void writeFileFooter() {
		int ftrLen = FILE_FOOTER_LEN;
		byte[] ftr = new byte[ftrLen];
		encodeHeader(ftr);

		NB.encodeInt64(ftr
		NB.encodeInt64(ftr
		NB.encodeInt64(ftr
		NB.encodeInt64(ftr
		NB.encodeInt64(ftr

		CRC32 crc = new CRC32();
		crc.update(ftr
		NB.encodeInt32(ftr

		out.write(ftr
	}

	private static long firstBlockPosition(@Nullable Section s) {
		return s != null ? s.firstBlockPosition : 0;
	}

	private static long indexPosition(@Nullable Section s) {
		return s != null && s.idx != null ? s.idx.rootPosition : 0;
	}

	public Stats getStats() {
		return stats;
	}

	public static class Stats {
		private final int refBlockSize;
		private final int logBlockSize;
		private final int restartInterval;

		private final long minUpdateIndex;
		private final long maxUpdateIndex;

		private final long refCnt;
		private final long objCnt;
		private final int objIdLen;
		private final long logCnt;
		private final long refBytes;
		private final long objBytes;
		private final long logBytes;
		private final long paddingUsed;
		private final long totalBytes;

		private final int refIndexSize;
		private final int refIndexLevels;
		private final int objIndexSize;
		private final int objIndexLevels;

		Stats(ReftableWriter w
			refBlockSize = w.refBlockSize;
			logBlockSize = w.logBlockSize;
			restartInterval = w.restartInterval;

			minUpdateIndex = w.minUpdateIndex;
			maxUpdateIndex = w.maxUpdateIndex;
			paddingUsed = o.paddingUsed();
			totalBytes = o.size();

			refCnt = w.refs.entryCnt;
			refBytes = w.refs.bytes;

			objCnt = w.objs != null ? w.objs.entryCnt : 0;
			objBytes = w.objs != null ? w.objs.bytes : 0;
			objIdLen = w.objIdLen;

			logCnt = w.logs != null ? w.logs.entryCnt : 0;
			logBytes = w.logs != null ? w.logs.bytes : 0;

			IndexBuilder refIdx = w.refs.idx;
			refIndexSize = refIdx.bytes;
			refIndexLevels = refIdx.levels;

			IndexBuilder objIdx = w.objs != null ? w.objs.idx : null;
			objIndexSize = objIdx != null ? objIdx.bytes : 0;
			objIndexLevels = objIdx != null ? objIdx.levels : 0;
		}

		public int refBlockSize() {
			return refBlockSize;
		}

		public int logBlockSize() {
			return logBlockSize;
		}

		public int restartInterval() {
			return restartInterval;
		}

		public long minUpdateIndex() {
			return minUpdateIndex;
		}

		public long maxUpdateIndex() {
			return maxUpdateIndex;
		}

		public long refCount() {
			return refCnt;
		}

		public long objCount() {
			return objCnt;
		}

		public long logCount() {
			return logCnt;
		}

		public long refBytes() {
			return refBytes;
		}

		public long objBytes() {
			return objBytes;
		}

		public long logBytes() {
			return logBytes;
		}

		public long totalBytes() {
			return totalBytes;
		}

		public long paddingBytes() {
			return paddingUsed;
		}

		public int refIndexSize() {
			return refIndexSize;
		}

		public int refIndexLevels() {
			return refIndexLevels;
		}

		public int objIndexSize() {
			return objIndexSize;
		}

		public int objIndexLevels() {
			return objIndexLevels;
		}

		public int objIdLength() {
			return objIdLen;
		}
	}

	private static List<RefList> sortById(ObjectIdSubclassMap<RefList> m) {
		List<RefList> s = new ArrayList<>(m.size());
		for (RefList l : m) {
			s.add(l);
		}
		Collections.sort(s);
		return s;
	}

	private static int shortestUniqueAbbreviation(List<RefList> in) {
		int bytes = Math.max(2
		Set<AbbreviatedObjectId> tmp = new HashSet<>((int) (in.size() * 0.75f));
		retry: for (;;) {
			int hexLen = bytes * 2;
			for (ObjectId id : in) {
				AbbreviatedObjectId a = id.abbreviate(hexLen);
				if (!tmp.add(a)) {
					if (++bytes >= OBJECT_ID_LENGTH) {
						return OBJECT_ID_LENGTH;
					}
					tmp.clear();
					continue retry;
				}
			}
			return bytes;
		}
	}

	private static class RefList extends ObjectIdOwnerMap.Entry {
		final LongList blockPos = new LongList(2);

		RefList(AnyObjectId id) {
			super(id);
		}

		void addBlock(long pos) {
			if (!blockPos.contains(pos)) {
				blockPos.add(pos);
			}
		}
	}

	private class Section {
		final IndexBuilder idx;
		final long firstBlockPosition;

		long entryCnt;
		long bytes;

		Section(byte keyType) {
			idx = new IndexBuilder(keyType);
			firstBlockPosition = out.size();
		}

		long write(BlockWriter.Entry entry) throws IOException {
			if (cur == null) {
				beginBlock(entry);
			} else if (!cur.tryAdd(entry)) {
				flushCurBlock();
				if (cur.padBetweenBlocks()) {
					out.padBetweenBlocksToNextBlock();
				}
				beginBlock(entry);
			}
			entryCnt++;
			return out.size();
		}

		private void beginBlock(BlockWriter.Entry entry)
				throws BlockSizeTooSmallException {
			byte blockType = entry.blockType();
			int bs = out.bytesAvailableInBlock();
			cur = new BlockWriter(blockType
			cur.mustAdd(entry);
		}

		void flushCurBlock() throws IOException {
			idx.entries.add(new IndexEntry(cur.lastKey()
			cur.writeTo(out);
		}

		void finishSectionMaybeWriteIndex() throws IOException {
			flushCurBlock();
			cur = null;
			if (shouldHaveIndex(idx)) {
				idx.writeIndex();
			}
			bytes = out.size() - firstBlockPosition;
		}
	}

	private class IndexBuilder {
		final byte keyType;
		List<IndexEntry> entries = new ArrayList<>();
		long rootPosition;
		int bytes;
		int levels;

		IndexBuilder(byte kt) {
			keyType = kt;
		}

		int estimateBytes(long curBlockPos) {
			BlockWriter b = new BlockWriter(
					INDEX_BLOCK_TYPE
					MAX_BLOCK_SIZE
					Math.max(restartInterval
			try {
				for (Entry e : entries) {
					b.mustAdd(e);
				}
				if (cur != null) {
					b.mustAdd(new IndexEntry(cur.lastKey()
				}
			} catch (BlockSizeTooSmallException e) {
				return b.currentSize();
			}
			return b.currentSize();
		}

		void writeIndex() throws IOException {
			if (padBetweenBlocks(keyType)) {
				out.padBetweenBlocksToNextBlock();
			}
			long startPos = out.size();
			writeMultiLevelIndex(entries);
			bytes = (int) (out.size() - startPos);
			entries = null;
		}

		private void writeMultiLevelIndex(List<IndexEntry> keys)
				throws IOException {
			levels = 1;
			while (maxIndexLevels == 0 || levels < maxIndexLevels) {
				keys = writeOneLevel(keys);
				if (keys == null) {
					return;
				}
				levels++;
			}

			BlockWriter b = new BlockWriter(
					INDEX_BLOCK_TYPE
					MAX_BLOCK_SIZE
					Math.max(restartInterval
			for (Entry e : keys) {
				b.mustAdd(e);
			}
			rootPosition = out.size();
			b.writeTo(out);
		}

		private List<IndexEntry> writeOneLevel(List<IndexEntry> keys)
				throws IOException {
			Section thisLevel = new Section(keyType);
			for (Entry e : keys) {
				thisLevel.write(e);
			}
			if (!thisLevel.idx.entries.isEmpty()) {
				thisLevel.flushCurBlock();
				if (cur.padBetweenBlocks()) {
					out.padBetweenBlocksToNextBlock();
				}
				cur = null;
				return thisLevel.idx.entries;
			}

			rootPosition = out.size();
			cur.writeTo(out);
			cur = null;
			return null;
		}
	}
}
