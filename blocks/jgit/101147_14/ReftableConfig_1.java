
package org.eclipse.jgit.internal.storage.reftable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.INDEX_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.LOG_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.MAX_RESTARTS;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.OBJ_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.REF_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_1ID;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_2ID;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_INDEX_RECORD;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_LOG_RECORD;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_NONE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_TEXT;
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
import org.eclipse.jgit.util.NB;

class BlockWriter {
	private final byte blockType;
	private final byte keyType;
	private final List<Entry> entries = new ArrayList<>();
	private final int blockSize;
	private final int restartInterval;

	private int bytesInKeySection;
	private int restartCnt;

	BlockWriter(byte type
		blockType = type;
		keyType = kt;
		blockSize = bs;
		restartInterval = ri;
	}

	byte blockType() {
		return blockType;
	}

	boolean padBetweenBlocks() {
		return blockType == REF_BLOCK_TYPE || blockType == OBJ_BLOCK_TYPE;
	}

	byte[] lastKey() {
		return entries.get(entries.size() - 1).key;
	}

	int entryCount() {
		return entries.size();
	}

	int currentSize() {
		return computeBlockSize(0
	}

	int estimateIndexSizeIfAdding(byte[] lastKey
		IndexEntry entry = new IndexEntry(lastKey
		return computeBlockSize(entry.size()
	}

	void addIndex(byte[] lastKey
		entries.add(new IndexEntry(lastKey
	}

	void addFirst(Entry entry) throws BlockSizeTooSmallException {
		if (!tryAdd(entry
			throw blockSizeTooSmall(entry);
		}
	}

	boolean tryAdd(Entry entry) {
		if (entry instanceof ObjEntry
				&& computeBlockSize(1
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
			byte[] prior = entries.get(entries.size() - 1).key;
			prefixLen = commonPrefix(prior
				restart = true;
				prefixLen = 0;
			} else if (prefixLen == 0) {
				restart = true;
			}
		}

		entry.restart = restart;
		entry.prefixLen = prefixLen;
		int entrySize = entry.size();
		if (computeBlockSize(entrySize
			return false;
		}

		bytesInKeySection += entrySize;
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

	private int computeBlockSize(int key
		return computeBlockSize(
				restartCnt + (restart ? 1 : 0)
				bytesInKeySection + key);
	}

	private static int computeBlockSize(int restartCnt
				+ entryBytes;
	}

	void writeTo(ReftableOutputStream os) throws IOException {
		if (blockType == INDEX_BLOCK_TYPE) {
			selectIndexRestarts();
		}
		if (restartCnt == 0 || restartCnt > MAX_RESTARTS) {
			throw new IllegalStateException();
		}

		os.beginBlock(blockType);
		os.writeInt16(restartCnt);
		int restartTbl = os.bytesWrittenInBlock();
		os.reserve(restartCnt * 3);

		int ri = 0;
		for (Entry entry : entries) {
			if (entry.restart) {
				int ptr = restartTbl + (ri++ * 3);
				int start = os.bytesWrittenInBlock();
				os.patchInt24(ptr
			}
			entry.writeKey(os);
			entry.writeValue(os);
		}
		os.flushBlock();
	}

	private void selectIndexRestarts() {
		restartCnt = 0;
		int ri = Math.max(restartInterval
		for (int k = 0; k < entries.size(); k++) {
			Entry e = entries.get(k);
			if ((k % ri) == 0) {
				e.restart = true;
				e.prefixLen = 0;
				restartCnt++;
			} else {
				e.restart = false;
			}
		}

		for (int k = 1; k < entries.size(); k++) {
			Entry e = entries.get(k);
			if (!e.restart) {
				byte[] prior = entries.get(k - 1).key;
				e.prefixLen = commonPrefix(prior
			}
		}
	}

	private BlockSizeTooSmallException blockSizeTooSmall(Entry entry) {
		int min = computeBlockSize(1
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

		int size() {
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
			return VALUE_INDEX_RECORD;
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

		RefEntry(Ref ref) {
			super(nameUtf8(ref));
			this.ref = ref;
		}

		@Override
		byte blockType() {
			return REF_BLOCK_TYPE;
		}

		@Override
		int valueType() {
			if (ref.isSymbolic()) {
				return VALUE_TEXT;
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
			if (ref.isSymbolic()) {
				int nameLen = 5 + nameUtf8(ref.getTarget()).length;
				return computeVarintSize(nameLen) + nameLen;
			} else if (ref.getStorage() == NEW && ref.getObjectId() == null) {
				return 0;
			} else if (ref.getPeeledObjectId() != null) {
				return 2 * OBJECT_ID_LENGTH;
			} else {
				return OBJECT_ID_LENGTH;
			}
		}

		@Override
		void writeValue(ReftableOutputStream os) throws IOException {
			if (ref.isSymbolic()) {
				String target = ref.getTarget().getName();
				return;
			}

			ObjectId id1 = ref.getObjectId();
			if (id1 == null) {
				if (ref.getStorage() == NEW) {
					return;
				}
				throw new IOException(JGitText.get().invalidId0);
			} else if (!ref.isPeeled()) {
				throw new IOException(JGitText.get().peeledRefIsRequired);
			}
			os.writeId(id1);

			ObjectId id2 = ref.getPeeledObjectId();
			if (id2 != null) {
				os.writeId(id2);
			}
		}

		private static byte[] nameUtf8(Ref ref) {
			return ref.getName().getBytes(UTF_8);
		}
	}

	static class TextEntry extends Entry {
		final byte[] value;

		TextEntry(String name
			super(name.getBytes(UTF_8));
			this.value = value.getBytes(UTF_8);
		}

		@Override
		byte blockType() {
			return REF_BLOCK_TYPE;
		}

		@Override
		int valueType() {
			return VALUE_TEXT;
		}

		@Override
		int valueSize() {
			return computeVarintSize(value.length) + value.length;
		}

		@Override
		void writeValue(ReftableOutputStream os) throws IOException {
			os.writeVarint(value.length);
			os.write(value);
		}
	}

	static class ObjEntry extends Entry {
		final IntList blockIds;

		ObjEntry(int idLen
			super(key(idLen
			this.blockIds = blockIds;
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
			blockIds.clear();
		}

		@Override
		byte blockType() {
			return OBJ_BLOCK_TYPE;
		}

		@Override
		int valueType() {
			int cnt = blockIds.size();
			return cnt != 0 && cnt <= VALUE_TYPE_MASK ? cnt : 0;
		}

		@Override
		int valueSize() {
			int cnt = blockIds.size();
			if (cnt == 0) {
				return computeVarintSize(0);
			}

			int n = 0;
			if (cnt > VALUE_TYPE_MASK) {
				n += computeVarintSize(cnt);
			}
			n += computeVarintSize(blockIds.get(0));
			for (int j = 1; j < cnt; j++) {
				int prior = blockIds.get(j - 1);
				int b = blockIds.get(j);
				n += computeVarintSize(b - prior);
			}
			return n;
		}

		@Override
		void writeValue(ReftableOutputStream os) throws IOException {
			int cnt = blockIds.size();
			if (cnt == 0) {
				os.writeVarint(0);
				return;
			}

			if (cnt > VALUE_TYPE_MASK) {
				os.writeVarint(cnt);
			}
			os.writeVarint(blockIds.get(0));
			for (int j = 1; j < cnt; j++) {
				int prior = blockIds.get(j - 1);
				int b = blockIds.get(j);
				os.writeVarint(b - prior);
			}
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
			return VALUE_LOG_RECORD;
		}

		@Override
		int valueSize() {
			return 2 * OBJECT_ID_LENGTH
					+ computeVarintSize(timeSecs)
					+ computeVarintSize(name.length) + name.length
					+ computeVarintSize(email.length) + email.length
					+ computeVarintSize(msg.length) + msg.length;
		}

		@Override
		void writeValue(ReftableOutputStream os) {
			os.writeId(oldId);
			os.writeId(newId);
			os.writeInt16(tz);
			os.writeVarint(timeSecs);
			os.writeVarintString(name);
			os.writeVarintString(email);
			os.writeVarintString(msg);
		}
	}
}
