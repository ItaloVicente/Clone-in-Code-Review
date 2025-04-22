
package org.eclipse.jgit.internal.storage.reftable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_HEADER_LEN;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.INDEX_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.LOG_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.LOG_DATA;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.LOG_NONE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.MAX_RESTARTS;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.OBJ_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.REF_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_1ID;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_2ID;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_NONE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_SYMREF;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_TYPE_MASK;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.reverseUpdateIndex;
import static org.eclipse.jgit.internal.storage.reftable.ReftableOutputStream.computeVarintSize;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.util.IntList;
import org.eclipse.jgit.util.LongList;
import org.eclipse.jgit.util.NB;

class BlockWriter {
	private final byte blockType;
	private final byte keyType;
	private final List<Entry> entries;
	private final int blockLimitBytes;
	private final int restartInterval;

	private int entriesSumBytes;
	private int restartCnt;

	BlockWriter(byte type
		blockType = type;
		keyType = kt;
		blockLimitBytes = bs;
		restartInterval = ri;
		entries = new ArrayList<>(estimateEntryCount(type
	}

	private static int estimateEntryCount(byte blockType
			int blockLimitBytes) {
		double avgBytesPerEntry;
		switch (blockType) {
		case REF_BLOCK_TYPE:
		default:
			avgBytesPerEntry = 35.31;
			break;

		case OBJ_BLOCK_TYPE:
			avgBytesPerEntry = 4.19;
			break;

		case LOG_BLOCK_TYPE:
			avgBytesPerEntry = 101.14;
			break;

		case INDEX_BLOCK_TYPE:
			switch (keyType) {
			case REF_BLOCK_TYPE:
			case LOG_BLOCK_TYPE:
			default:
				avgBytesPerEntry = 27.44;
				break;

			case OBJ_BLOCK_TYPE:
				avgBytesPerEntry = 11.57;
				break;
			}
		}

		int cnt = (int) (Math.ceil(blockLimitBytes / avgBytesPerEntry));
		return Math.min(cnt
	}

	byte blockType() {
		return blockType;
	}

	boolean padBetweenBlocks() {
		return padBetweenBlocks(blockType)
				|| (blockType == INDEX_BLOCK_TYPE && padBetweenBlocks(keyType));
	}

	static boolean padBetweenBlocks(byte type) {
		return type == REF_BLOCK_TYPE || type == OBJ_BLOCK_TYPE;
	}

	byte[] lastKey() {
		return entries.get(entries.size() - 1).key;
	}

	int currentSize() {
		return computeBlockBytes(0
	}

	void mustAdd(Entry entry) throws BlockSizeTooSmallException {
		if (!tryAdd(entry
			throw blockSizeTooSmall(entry);
		}
	}

	boolean tryAdd(Entry entry) {
		if (entry instanceof ObjEntry
				&& computeBlockBytes(entry.sizeBytes()
			((ObjEntry) entry).markScanRequired();
		}

		if (tryAdd(entry
			return true;
		} else if (nextShouldBeRestart()) {
			return tryAdd(entry
		}
		return false;
	}

	private boolean tryAdd(Entry entry
		byte[] key = entry.key;
		int prefixLen = 0;
		boolean restart = tryRestart && nextShouldBeRestart();
		if (!restart) {
			Entry priorEntry = entries.get(entries.size() - 1);
			byte[] prior = priorEntry.key;
			prefixLen = commonPrefix(prior
				restart = true;
				prefixLen = 0;
			} else if (prefixLen == 0) {
				restart = true;
			}
		}

		entry.restart = restart;
		entry.prefixLen = prefixLen;
		int entryBytes = entry.sizeBytes();
		if (computeBlockBytes(entryBytes
			return false;
		}

		entriesSumBytes += entryBytes;
		entries.add(entry);
		if (restart) {
			restartCnt++;
		}
		return true;
	}

	private boolean nextShouldBeRestart() {
		int cnt = entries.size();
		return (cnt == 0 || ((cnt + 1) % restartInterval) == 0)
				&& restartCnt < MAX_RESTARTS;
	}

	private int computeBlockBytes(int entryBytes
		return computeBlockBytes(
				entriesSumBytes + entryBytes
				restartCnt + (restart ? 1 : 0));
	}

	private static int computeBlockBytes(int entryBytes
				+ entryBytes
	}

	void writeTo(ReftableOutputStream os) throws IOException {
		os.beginBlock(blockType);
		IntList restarts = new IntList(restartCnt);
		for (Entry entry : entries) {
			if (entry.restart) {
				restarts.add(os.bytesWrittenInBlock());
			}
			entry.writeKey(os);
			entry.writeValue(os);
		}
		if (restarts.size() == 0 || restarts.size() > MAX_RESTARTS) {
			throw new IllegalStateException();
		}
		for (int i = 0; i < restarts.size(); i++) {
			os.writeInt24(restarts.get(i));
		}
		os.writeInt16(restarts.size());
		os.flushBlock();
	}

	private BlockSizeTooSmallException blockSizeTooSmall(Entry entry) {
		int min = FILE_HEADER_LEN + computeBlockBytes(entry.sizeBytes()
		return new BlockSizeTooSmallException(min);
	}

	static int commonPrefix(byte[] a
		int len = Math.min(n
		for (int i = 0; i < len; i++) {
			if (a[i] != b[i]) {
				return i;
			}
		}
		return len;
	}

	static int encodeSuffixAndType(int sfx
		return (sfx << 3) | valueType;
	}

	static int compare(
			byte[] a
			byte[] b
		int aEnd = ai + aLen;
		int bEnd = bi + bLen;
		while (ai < aEnd && bi < bEnd) {
			int c = (a[ai++] & 0xff) - (b[bi++] & 0xff);
			if (c != 0) {
				return c;
			}
		}
		return aLen - bLen;
	}

	static abstract class Entry {
		static int compare(Entry ea
			byte[] a = ea.key;
			byte[] b = eb.key;
			return BlockWriter.compare(a
		}

		final byte[] key;
		int prefixLen;
		boolean restart;

		Entry(byte[] key) {
			this.key = key;
		}

		void writeKey(ReftableOutputStream os) {
			int sfxLen = key.length - prefixLen;
			os.writeVarint(prefixLen);
			os.writeVarint(encodeSuffixAndType(sfxLen
			os.write(key
		}

		int sizeBytes() {
			int sfxLen = key.length - prefixLen;
			int sfx = encodeSuffixAndType(sfxLen
			return computeVarintSize(prefixLen)
					+ computeVarintSize(sfx)
					+ sfxLen
					+ valueSize();
		}

		abstract byte blockType();
		abstract int valueType();
		abstract int valueSize();
		abstract void writeValue(ReftableOutputStream os) throws IOException;
	}

	static class IndexEntry extends Entry {
		private final long blockPosition;

		IndexEntry(byte[] key
			super(key);
			this.blockPosition = blockPosition;
		}

		@Override
		byte blockType() {
			return INDEX_BLOCK_TYPE;
		}

		@Override
		int valueType() {
			return 0;
		}

		@Override
		int valueSize() {
			return computeVarintSize(blockPosition);
		}

		@Override
		void writeValue(ReftableOutputStream os) {
			os.writeVarint(blockPosition);
		}
	}

	static class RefEntry extends Entry {
		final Ref ref;
		final long updateIndexDelta;

		RefEntry(Ref ref
			super(nameUtf8(ref));
			this.ref = ref;
			this.updateIndexDelta = updateIndexDelta;
		}

		@Override
		byte blockType() {
			return REF_BLOCK_TYPE;
		}

		@Override
		int valueType() {
			if (ref.isSymbolic()) {
				return VALUE_SYMREF;
			} else if (ref.getStorage() == NEW && ref.getObjectId() == null) {
				return VALUE_NONE;
			} else if (ref.getPeeledObjectId() != null) {
				return VALUE_2ID;
			} else {
				return VALUE_1ID;
			}
		}

		@Override
		int valueSize() {
			int n = computeVarintSize(updateIndexDelta);
			switch (valueType()) {
			case VALUE_NONE:
				return n;
			case VALUE_1ID:
				return n + OBJECT_ID_LENGTH;
			case VALUE_2ID:
				return n + 2 * OBJECT_ID_LENGTH;
			case VALUE_SYMREF:
				if (ref.isSymbolic()) {
					int nameLen = nameUtf8(ref.getTarget()).length;
					return n + computeVarintSize(nameLen) + nameLen;
				}
			}
			throw new IllegalStateException();
		}

		@Override
		void writeValue(ReftableOutputStream os) throws IOException {
			os.writeVarint(updateIndexDelta);
			switch (valueType()) {
			case VALUE_NONE:
				return;

			case VALUE_1ID: {
				ObjectId id1 = ref.getObjectId();
				if (!ref.isPeeled()) {
					throw new IOException(JGitText.get().peeledRefIsRequired);
				} else if (id1 == null) {
					throw new IOException(JGitText.get().invalidId0);
				}
				os.writeId(id1);
				return;
			}

			case VALUE_2ID: {
				ObjectId id1 = ref.getObjectId();
				ObjectId id2 = ref.getPeeledObjectId();
				if (!ref.isPeeled()) {
					throw new IOException(JGitText.get().peeledRefIsRequired);
				} else if (id1 == null || id2 == null) {
					throw new IOException(JGitText.get().invalidId0);
				}
				os.writeId(id1);
				os.writeId(id2);
				return;
			}

			case VALUE_SYMREF:
				if (ref.isSymbolic()) {
					os.writeVarintString(ref.getTarget().getName());
					return;
				}
			}
			throw new IllegalStateException();
		}

		private static byte[] nameUtf8(Ref ref) {
			return ref.getName().getBytes(UTF_8);
		}
	}

	static class ObjEntry extends Entry {
		final LongList blockPos;

		ObjEntry(int idLen
			super(key(idLen
			this.blockPos = blockPos;
		}

		private static byte[] key(int idLen
			byte[] key = new byte[OBJECT_ID_LENGTH];
			id.copyRawTo(key
			if (idLen < OBJECT_ID_LENGTH) {
				return Arrays.copyOf(key
			}
			return key;
		}

		void markScanRequired() {
			blockPos.clear();
		}

		@Override
		byte blockType() {
			return OBJ_BLOCK_TYPE;
		}

		@Override
		int valueType() {
			int cnt = blockPos.size();
			return cnt != 0 && cnt <= VALUE_TYPE_MASK ? cnt : 0;
		}

		@Override
		int valueSize() {
			int cnt = blockPos.size();
			if (cnt == 0) {
				return computeVarintSize(0);
			}

			int n = 0;
			if (cnt > VALUE_TYPE_MASK) {
				n += computeVarintSize(cnt);
			}
			n += computeVarintSize(blockPos.get(0));
			for (int j = 1; j < cnt; j++) {
				long prior = blockPos.get(j - 1);
				long b = blockPos.get(j);
				n += computeVarintSize(b - prior);
			}
			return n;
		}

		@Override
		void writeValue(ReftableOutputStream os) throws IOException {
			int cnt = blockPos.size();
			if (cnt == 0) {
				os.writeVarint(0);
				return;
			}

			if (cnt > VALUE_TYPE_MASK) {
				os.writeVarint(cnt);
			}
			os.writeVarint(blockPos.get(0));
			for (int j = 1; j < cnt; j++) {
				long prior = blockPos.get(j - 1);
				long b = blockPos.get(j);
				os.writeVarint(b - prior);
			}
		}
	}

	static class DeleteLogEntry extends Entry {
		DeleteLogEntry(String refName
			super(LogEntry.key(refName
		}

		@Override
		byte blockType() {
			return LOG_BLOCK_TYPE;
		}

		@Override
		int valueType() {
			return LOG_NONE;
		}

		@Override
		int valueSize() {
			return 0;
		}

		@Override
		void writeValue(ReftableOutputStream os) {
		}
	}

	static class LogEntry extends Entry {
		final ObjectId oldId;
		final ObjectId newId;
		final long timeSecs;
		final short tz;
		final byte[] name;
		final byte[] email;
		final byte[] msg;

		LogEntry(String refName
				ObjectId oldId
			super(key(refName

			this.oldId = oldId;
			this.newId = newId;
			this.timeSecs = who.getWhen().getTime() / 1000L;
			this.tz = (short) who.getTimeZoneOffset();
			this.name = who.getName().getBytes(UTF_8);
			this.email = who.getEmailAddress().getBytes(UTF_8);
			this.msg = message.getBytes(UTF_8);
		}

		static byte[] key(String ref
			byte[] name = ref.getBytes(UTF_8);
			byte[] key = Arrays.copyOf(name
			NB.encodeInt64(key
			return key;
		}

		@Override
		byte blockType() {
			return LOG_BLOCK_TYPE;
		}

		@Override
		int valueType() {
			return LOG_DATA;
		}

		@Override
		int valueSize() {
			return 2 * OBJECT_ID_LENGTH
					+ computeVarintSize(name.length) + name.length
					+ computeVarintSize(email.length) + email.length
					+ computeVarintSize(timeSecs)
					+ computeVarintSize(msg.length) + msg.length;
		}

		@Override
		void writeValue(ReftableOutputStream os) {
			os.writeId(oldId);
			os.writeId(newId);
			os.writeVarintString(name);
			os.writeVarintString(email);
			os.writeVarint(timeSecs);
			os.writeInt16(tz);
			os.writeVarintString(msg);
		}
	}
}
