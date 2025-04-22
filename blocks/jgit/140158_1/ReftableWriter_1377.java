
package org.eclipse.jgit.internal.storage.reftable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.reftable.BlockReader.decodeBlockLen;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_FOOTER_LEN;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_HEADER_LEN;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.INDEX_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.LOG_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.REF_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VERSION_1;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.isFileHeaderMagic;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.zip.CRC32;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.internal.storage.reftable.BlockWriter.LogEntry;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.util.LongList;
import org.eclipse.jgit.util.LongMap;
import org.eclipse.jgit.util.NB;

public class ReftableReader extends Reftable {
	private final BlockSource src;

	private int blockSize = -1;
	private long minUpdateIndex;
	private long maxUpdateIndex;

	private long refEnd;
	private long objPosition;
	private long objEnd;
	private long logPosition;
	private long logEnd;
	private int objIdLen;

	private long refIndexPosition = -1;
	private long objIndexPosition = -1;
	private long logIndexPosition = -1;

	private BlockReader refIndex;
	private BlockReader objIndex;
	private BlockReader logIndex;
	private LongMap<BlockReader> indexCache;

	public ReftableReader(BlockSource src) {
		this.src = src;
	}

	public int blockSize() throws IOException {
		if (blockSize == -1) {
			readFileHeader();
		}
		return blockSize;
	}

	public long minUpdateIndex() throws IOException {
		if (blockSize == -1) {
			readFileHeader();
		}
		return minUpdateIndex;
	}

	public long maxUpdateIndex() throws IOException {
		if (blockSize == -1) {
			readFileHeader();
		}
		return maxUpdateIndex;
	}

	@Override
	public RefCursor allRefs() throws IOException {
		if (blockSize == -1) {
			readFileHeader();
		}

		long end = refEnd > 0 ? refEnd : (src.size() - FILE_FOOTER_LEN);
		src.adviseSequentialRead(0

		RefCursorImpl i = new RefCursorImpl(end
		i.block = readBlock(0
		return i;
	}

	@Override
	public RefCursor seekRef(String refName) throws IOException {
		initRefIndex();

		byte[] key = refName.getBytes(UTF_8);
		RefCursorImpl i = new RefCursorImpl(refEnd
		i.block = seek(REF_BLOCK_TYPE
		return i;
	}

	@Override
	public RefCursor seekRefsWithPrefix(String prefix) throws IOException {
		initRefIndex();

		byte[] key = prefix.getBytes(UTF_8);
		RefCursorImpl i = new RefCursorImpl(refEnd
		i.block = seek(REF_BLOCK_TYPE
		return i;
	}

	@Override
	public RefCursor byObjectId(AnyObjectId id) throws IOException {
		initObjIndex();
		ObjCursorImpl i = new ObjCursorImpl(refEnd
		if (objIndex != null) {
			i.initSeek();
		} else {
			i.initScan();
		}
		return i;
	}

	@Override
	public LogCursor allLogs() throws IOException {
		initLogIndex();
		if (logPosition > 0) {
			src.adviseSequentialRead(logPosition
			LogCursorImpl i = new LogCursorImpl(logEnd
			i.block = readBlock(logPosition
			return i;
		}
		return new EmptyLogCursor();
	}

	@Override
	public LogCursor seekLog(String refName
			throws IOException {
		initLogIndex();
		if (logPosition > 0) {
			byte[] key = LogEntry.key(refName
			byte[] match = refName.getBytes(UTF_8);
			LogCursorImpl i = new LogCursorImpl(logEnd
			i.block = seek(LOG_BLOCK_TYPE
			return i;
		}
		return new EmptyLogCursor();
	}

	private BlockReader seek(byte blockType
			long startPos
		if (idx != null) {
			BlockReader block = idx;
			do {
				if (block.seekKey(key) > 0) {
					return null;
				}
				long pos = block.readPositionFromIndex();
				block = readBlock(pos
			} while (block.type() == INDEX_BLOCK_TYPE);
			block.seekKey(key);
			return block;
		}
		return binarySearch(blockType
	}

	private BlockReader binarySearch(byte blockType
			long startPos
		if (blockSize == 0) {
			BlockReader b = readBlock(startPos
			if (blockType != b.type()) {
				return null;
			}
			b.seekKey(key);
			return b;
		}

		int low = (int) (startPos / blockSize);
		int end = blocksIn(startPos
		BlockReader block = null;
		do {
			int mid = (low + end) >>> 1;
			block = readBlock(((long) mid) * blockSize
			if (blockType != block.type()) {
				return null;
			}
			int cmp = block.seekKey(key);
			if (cmp < 0) {
				end = mid;
			} else if (cmp == 0) {
				break;
				low = mid + 1;
			}
		} while (low < end);
		return block;
	}

	private void readFileHeader() throws IOException {
		readHeaderOrFooter(0
	}

	private void readFileFooter() throws IOException {
		int ftrLen = FILE_FOOTER_LEN;
		byte[] ftr = readHeaderOrFooter(src.size() - ftrLen

		CRC32 crc = new CRC32();
		crc.update(ftr
		if (crc.getValue() != NB.decodeUInt32(ftr
			throw new IOException(JGitText.get().invalidReftableCRC);
		}

		refIndexPosition = NB.decodeInt64(ftr
		long p = NB.decodeInt64(ftr
		objPosition = p >>> 5;
		objIdLen = (int) (p & 0x1f);
		objIndexPosition = NB.decodeInt64(ftr
		logPosition = NB.decodeInt64(ftr
		logIndexPosition = NB.decodeInt64(ftr

		if (refIndexPosition > 0) {
			refEnd = refIndexPosition;
		} else if (objPosition > 0) {
			refEnd = objPosition;
		} else if (logPosition > 0) {
			refEnd = logPosition;
		} else {
			refEnd = src.size() - ftrLen;
		}

		if (objPosition > 0) {
			if (objIndexPosition > 0) {
				objEnd = objIndexPosition;
			} else if (logPosition > 0) {
				objEnd = logPosition;
			} else {
				objEnd = src.size() - ftrLen;
			}
		}

		if (logPosition > 0) {
			if (logIndexPosition > 0) {
				logEnd = logIndexPosition;
			} else {
				logEnd = src.size() - ftrLen;
			}
		}
	}

	private byte[] readHeaderOrFooter(long pos
		ByteBuffer buf = src.read(pos
		if (buf.position() != len) {
			throw new IOException(JGitText.get().shortReadOfBlock);
		}

		byte[] tmp = new byte[len];
		buf.flip();
		buf.get(tmp);
		if (!isFileHeaderMagic(tmp
			throw new IOException(JGitText.get().invalidReftableFile);
		}

		int v = NB.decodeInt32(tmp
		int version = v >>> 24;
		if (VERSION_1 != version) {
			throw new IOException(MessageFormat.format(
					JGitText.get().unsupportedReftableVersion
					Integer.valueOf(version)));
		}
		if (blockSize == -1) {
			blockSize = v & 0xffffff;
		}
		minUpdateIndex = NB.decodeInt64(tmp
		maxUpdateIndex = NB.decodeInt64(tmp
		return tmp;
	}

	private void initRefIndex() throws IOException {
		if (refIndexPosition < 0) {
			readFileFooter();
		}
		if (refIndex == null && refIndexPosition > 0) {
			refIndex = readIndex(refIndexPosition);
		}
	}

	private void initObjIndex() throws IOException {
		if (objIndexPosition < 0) {
			readFileFooter();
		}
		if (objIndex == null && objIndexPosition > 0) {
			objIndex = readIndex(objIndexPosition);
		}
	}

	private void initLogIndex() throws IOException {
		if (logIndexPosition < 0) {
			readFileFooter();
		}
		if (logIndex == null && logIndexPosition > 0) {
			logIndex = readIndex(logIndexPosition);
		}
	}

	private BlockReader readIndex(long pos) throws IOException {
		int sz = readBlockLen(pos);
		BlockReader i = new BlockReader();
		i.readBlock(src
		i.verifyIndex();
		return i;
	}

	private int readBlockLen(long pos) throws IOException {
		int sz = pos == 0 ? FILE_HEADER_LEN + 4 : 4;
		ByteBuffer tmp = src.read(pos
		if (tmp.position() < sz) {
			throw new IOException(JGitText.get().invalidReftableFile);
		}
		byte[] buf;
		if (tmp.hasArray() && tmp.arrayOffset() == 0) {
			buf = tmp.array();
		} else {
			buf = new byte[sz];
			tmp.flip();
			tmp.get(buf);
		}
		if (pos == 0 && buf[FILE_HEADER_LEN] == FILE_BLOCK_TYPE) {
			return FILE_HEADER_LEN;
		}
		int p = pos == 0 ? FILE_HEADER_LEN : 0;
		return decodeBlockLen(NB.decodeInt32(buf
	}

	private BlockReader readBlock(long pos
		if (indexCache != null) {
			BlockReader b = indexCache.get(pos);
			if (b != null) {
				return b;
			}
		}

		int sz = blockSize;
		if (sz == 0) {
			sz = readBlockLen(pos);
		} else if (pos + sz > end) {
		}

		BlockReader b = new BlockReader();
		b.readBlock(src
		if (b.type() == INDEX_BLOCK_TYPE && !b.truncated()) {
			if (indexCache == null) {
				indexCache = new LongMap<>();
			}
			indexCache.put(pos
		}
		return b;
	}

	private int blocksIn(long pos
		int blocks = (int) ((end - pos) / blockSize);
		return end % blockSize == 0 ? blocks : (blocks + 1);
	}

	public long size() throws IOException {
		return src.size();
	}

	@Override
	public void close() throws IOException {
		src.close();
	}

	private class RefCursorImpl extends RefCursor {
		private final long scanEnd;
		private final byte[] match;
		private final boolean prefix;

		private Ref ref;
		BlockReader block;

		RefCursorImpl(long scanEnd
			this.scanEnd = scanEnd;
			this.match = match;
			this.prefix = prefix;
		}

		@Override
		public boolean next() throws IOException {
			for (;;) {
				if (block == null || block.type() != REF_BLOCK_TYPE) {
					return false;
				} else if (!block.next()) {
					long pos = block.endPosition();
					if (pos >= scanEnd) {
						return false;
					}
					block = readBlock(pos
					continue;
				}

				block.parseKey();
				if (match != null && !block.match(match
					block.skipValue();
					return false;
				}

				ref = block.readRef(minUpdateIndex);
				if (!includeDeletes && wasDeleted()) {
					continue;
				}
				return true;
			}
		}

		@Override
		public Ref getRef() {
			return ref;
		}

		@Override
		public void close() {
		}
	}

	private class LogCursorImpl extends LogCursor {
		private final long scanEnd;
		private final byte[] match;

		private String refName;
		private long updateIndex;
		private ReflogEntry entry;
		BlockReader block;

		LogCursorImpl(long scanEnd
			this.scanEnd = scanEnd;
			this.match = match;
		}

		@Override
		public boolean next() throws IOException {
			for (;;) {
				if (block == null || block.type() != LOG_BLOCK_TYPE) {
					return false;
				} else if (!block.next()) {
					long pos = block.endPosition();
					if (pos >= scanEnd) {
						return false;
					}
					block = readBlock(pos
					continue;
				}

				block.parseKey();
				if (match != null && !block.match(match
					block.skipValue();
					return false;
				}

				refName = block.name();
				updateIndex = block.readLogUpdateIndex();
				entry = block.readLogEntry();
				if (entry == null && !includeDeletes) {
					continue;
				}
				return true;
			}
		}

		@Override
		public String getRefName() {
			return refName;
		}

		@Override
		public long getUpdateIndex() {
			return updateIndex;
		}

		@Override
		public ReflogEntry getReflogEntry() {
			return entry;
		}

		@Override
		public void close() {
		}
	}

	static final LongList EMPTY_LONG_LIST = new LongList(0);

	private class ObjCursorImpl extends RefCursor {
		private final long scanEnd;
		private final ObjectId match;

		private Ref ref;
		private int listIdx;

		private LongList blockPos;
		private BlockReader block;

		ObjCursorImpl(long scanEnd
			this.scanEnd = scanEnd;
			this.match = id.copy();
		}

		void initSeek() throws IOException {
			byte[] rawId = new byte[OBJECT_ID_LENGTH];
			match.copyRawTo(rawId
			byte[] key = Arrays.copyOf(rawId

			BlockReader b = objIndex;
			do {
				if (b.seekKey(key) > 0) {
					blockPos = EMPTY_LONG_LIST;
					return;
				}
				long pos = b.readPositionFromIndex();
				b = readBlock(pos
			} while (b.type() == INDEX_BLOCK_TYPE);
			b.seekKey(key);
			while (b.next()) {
				b.parseKey();
				if (b.match(key
					blockPos = b.readBlockPositionList();
					if (blockPos == null) {
						initScan();
						return;
					}
					break;
				}
				b.skipValue();
			}
			if (blockPos == null) {
				blockPos = EMPTY_LONG_LIST;
			}
			if (blockPos.size() > 0) {
				long pos = blockPos.get(listIdx++);
				block = readBlock(pos
			}
		}

		void initScan() throws IOException {
			block = readBlock(0
		}

		@Override
		public boolean next() throws IOException {
			for (;;) {
				if (block == null || block.type() != REF_BLOCK_TYPE) {
					return false;
				} else if (!block.next()) {
					long pos;
					if (blockPos != null) {
						if (listIdx >= blockPos.size()) {
							return false;
						}
						pos = blockPos.get(listIdx++);
					} else {
						pos = block.endPosition();
					}
					if (pos >= scanEnd) {
						return false;
					}
					block = readBlock(pos
					continue;
				}

				block.parseKey();
				ref = block.readRef(minUpdateIndex);
				ObjectId id = ref.getObjectId();
				if (id != null && match.equals(id)
						&& (includeDeletes || !wasDeleted())) {
					return true;
				}
			}
		}

		@Override
		public Ref getRef() {
			return ref;
		}

		@Override
		public void close() {
		}
	}
}
