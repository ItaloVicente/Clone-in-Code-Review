
package org.eclipse.jgit.internal.storage.reftable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.INDEX_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.LOG_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.MAX_RESTARTS;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.REF_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_1ID;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_2ID;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_NONE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_SYMREF;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.reverseTime;
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
	private final List<Entry> entries = new ArrayList<>();
	private final int blockSize;
	private final int restartInterval;

	private int bytesInKeyTable;
	private int restartCnt;

	BlockWriter(byte type
		blockType = type;
		blockSize = bs;
		restartInterval = ri;
	}

	byte[] lastKey() {
		return entries.get(entries.size() - 1).key;
	}

	int entryCount() {
		return entries.size();
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
		if (tryAdd(entry
			return true;
		} else if (nextShouldBeRestart()) {
			return tryAdd(entry
		}
		return false;
	}

	private boolean tryAdd(Entry entry
		byte[] name = entry.key;
		int prefixLen = 0;
		boolean restart = tryRestart && nextShouldBeRestart();
		if (!restart) {
			byte[] prior = entries.get(entries.size() - 1).key;
			prefixLen = commonPrefix(prior
			if (prefixLen == 0) {
				restart = true;
			}
		}

		int entrySize = entry.size(prefixLen);
		if (computeBlockSize(entrySize
			return false;
		}

		bytesInKeyTable += entrySize;
		entries.add(entry);
		if (restart) {
			entry.restart = true;
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
				+ bytesInKeyTable + key
				+ (restartCnt + (restart ? 1 : 0)) * 4
	}

	void writeTo(ReftableOutputStream os) throws IOException {
		if (blockType == INDEX_BLOCK_TYPE) {
			selectIndexRestarts();
		}
		if (restartCnt > MAX_RESTARTS) {
			throw new IllegalStateException();
		}

		IntList restartOffsets = new IntList(restartCnt);
		byte[] prior = {};

		os.beginBlock(blockType);
		for (int entryIdx = 0; entryIdx < entries.size(); entryIdx++) {
			Entry entry = entries.get(entryIdx);
			if (entry.restart) {
				restartOffsets.add(os.bytesWrittenInBlock());
			}
			entry.writeKey(os
			entry.writeValue(os);
			prior = entry.key;
		}
		for (int i = 0; i < restartOffsets.size(); i++) {
			os.writeInt32(restartOffsets.get(i));
		}
		os.writeInt16(restartOffsets.size() - 1);
		os.flushBlock();
	}

	private void selectIndexRestarts() {
		int ir = Math.max(restartInterval
		for (int k = 0; k < entries.size(); k++) {
			if ((k % ir) == 0) {
				entries.get(k).restart = true;
			}
		}
	}

	private BlockSizeTooSmallException blockSizeTooSmall(Entry entry) {
		int min = computeBlockSize(entry.size(0)
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


	static abstract class Entry {
		final byte[] key;
		boolean restart;

		Entry(byte[] key) {
			this.key = key;
		}

		void writeKey(ReftableOutputStream os
			int pfx;
			int sfx;
			if (restart) {
				pfx = 0;
				sfx = key.length;
			} else {
				pfx = commonPrefix(prior
				sfx = key.length - pfx;
			}
			os.writeVarint(pfx);
			os.writeVarint(sfx);
			os.write(key
		}

		int size(int prefixLen) {
			int suffixLen = key.length - prefixLen;
			return computeVarintSize(prefixLen)
					+ computeVarintSize(suffixLen)
					+ suffixLen
					+ valueSize();
		}

		abstract byte blockType();
		abstract int valueSize();
		abstract void writeValue(ReftableOutputStream os) throws IOException;
	}

	static class IndexEntry extends Entry {
		private final long blockOffset;

		IndexEntry(byte[] key
			super(key);
			this.blockOffset = blockOffset;
		}

		@Override
		byte blockType() {
			return INDEX_BLOCK_TYPE;
		}

		@Override
		int valueSize() {
			return computeVarintSize(blockOffset);
		}

		@Override
		void writeValue(ReftableOutputStream os) {
			os.writeVarint(blockOffset);
		}
	}

	static class RefEntry extends Entry {
		private final Ref ref;

		RefEntry(Ref ref) {
			super(nameUtf8(ref));
			this.ref = ref;
		}

		@Override
		byte blockType() {
			return REF_BLOCK_TYPE;
		}

		@Override
		int valueSize() {
			int type;
			int valLen;
			if (ref.isSymbolic()) {
				int nameLen = nameUtf8(ref.getTarget()).length;
				type = VALUE_SYMREF;
				valLen = computeVarintSize(nameLen) + nameLen;
			} else if (ref.getStorage() == NEW && ref.getObjectId() == null) {
				type = VALUE_NONE;
				valLen = 0;
			} else if (ref.getPeeledObjectId() != null) {
				type = VALUE_2ID;
				valLen = 2 * OBJECT_ID_LENGTH;
			} else {
				type = VALUE_1ID;
				valLen = OBJECT_ID_LENGTH;
			}
			return computeVarintSize(type) + valLen;
		}

		@Override
		void writeValue(ReftableOutputStream os) throws IOException {
			if (ref.isSymbolic()) {
				os.writeVarint(VALUE_SYMREF);
				os.writeVarintString(ref.getTarget().getName());
				return;
			}

			ObjectId id1 = ref.getObjectId();
			if (id1 == null) {
				if (ref.getStorage() == NEW) {
					os.writeVarint(VALUE_NONE);
					return;
				}
				throw new IOException(JGitText.get().invalidId0);
			} else if (!ref.isPeeled()) {
				throw new IOException(JGitText.get().peeledRefIsRequired);
			}

			ObjectId id2 = ref.getPeeledObjectId();
			if (id2 != null) {
				os.writeVarint(VALUE_2ID);
				os.writeId(id1);
				os.writeId(id2);
			} else {
				os.writeVarint(VALUE_1ID);
				os.writeId(id1);
			}
		}

		private static byte[] nameUtf8(Ref ref) {
			return ref.getName().getBytes(UTF_8);
		}
	}

	static class LogEntry extends Entry {
		final ObjectId oldId;
		final ObjectId newId;
		final short tz;
		final byte[] name;
		final byte[] email;
		final byte[] msg;

		LogEntry(String refName
				ObjectId oldId
				String message) {
			super(key(refName

			this.oldId = oldId;
			this.newId = newId;
			this.tz = (short) who.getTimeZoneOffset();
			this.name = who.getName().getBytes(UTF_8);
			this.email = who.getEmailAddress().getBytes(UTF_8);
			this.msg = message.getBytes(UTF_8);
		}

		static byte[] key(String refName
			byte[] name = refName.getBytes(UTF_8);
			byte[] key = Arrays.copyOf(name
			NB.encodeInt64(key
			return key;
		}

		@Override
		byte blockType() {
			return LOG_BLOCK_TYPE;
		}

		@Override
		int valueSize() {
			return 2 * OBJECT_ID_LENGTH
					+ computeVarintSize(name.length) + name.length
					+ computeVarintSize(email.length) + email.length
					+ computeVarintSize(msg.length) + msg.length;
		}

		@Override
		void writeValue(ReftableOutputStream os) {
			os.writeId(oldId);
			os.writeId(newId);
			os.writeInt16(tz);
			os.writeVarintString(name);
			os.writeVarintString(email);
			os.writeVarintString(msg);
		}
	}
}
