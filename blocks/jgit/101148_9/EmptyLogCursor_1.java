
package org.eclipse.jgit.internal.storage.reftable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.reftable.BlockWriter.compare;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_HEADER_LEN;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.INDEX_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.LOG_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.OBJ_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.REF_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_1ID;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_2ID;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_LEN_SPECIFIED;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_NONE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_SYMREF;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.VALUE_TYPE_MASK;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.reverseTime;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.lib.CheckoutEntry;
import org.eclipse.jgit.lib.InflaterCache;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.util.IntList;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;

class BlockReader {
	private byte blockType;
	private long endPosition;

	private byte[] buf;
	private int bufLen;
	private int ptr;

	private int keysStart;
	private int keysEnd;
	private int restartIdx;
	private int restartCount;

	private byte[] nameBuf = new byte[256];
	private int nameLen;
	private int valueType;

	byte type() {
		return blockType;
	}

	long endPosition() {
		return endPosition;
	}

	boolean next() {
		return ptr < keysEnd;
	}

	void parseKey() {
		int pfx = readVarint32();
		valueType = readVarint32();
		int sfx = valueType >>> 3;
		if (pfx + sfx > nameBuf.length) {
			int n = Math.max(pfx + sfx
			nameBuf = Arrays.copyOf(nameBuf
		}
		System.arraycopy(buf
		ptr += sfx;
		nameLen = pfx + sfx;
	}

	String name() {
		int len = nameLen;
		if (blockType == LOG_BLOCK_TYPE) {
			len -= 9;
		}
		return RawParseUtils.decode(UTF_8
	}

	boolean match(byte[] match
		int len = nameLen;
		if (blockType == LOG_BLOCK_TYPE) {
			len -= 9;
		}
		if (matchIsPrefix) {
			return len >= match.length
					&& compare(
							match
							nameBuf
		}
		return compare(match
	}

	boolean matchAbbrevId(byte[] match) {
		int n = Math.min(match.length
		return compare(match
	}

	long readIndex() throws IOException {
		if (blockType != INDEX_BLOCK_TYPE) {
			throw invalidBlock();
		}

		int n = readVarint32() >>> 3;
		return readVarint64();
	}

	Ref readRef() throws IOException {
		String name = RawParseUtils.decode(UTF_8
		switch (valueType & VALUE_TYPE_MASK) {
			return newRef(name);

		case VALUE_1ID:
			return new ObjectIdRef.PeeledNonTag(PACKED

			ObjectId id1 = readValueId();
			ObjectId id2 = readValueId();
			return new ObjectIdRef.PeeledTag(PACKED
		}

		case VALUE_SYMREF:
			return new SymbolicRef(name

		case VALUE_LEN_SPECIFIED:
		default:
			throw invalidBlock();
		}
	}

	IntList readBlockList() {
		int n = valueType & VALUE_TYPE_MASK;
		if (n == VALUE_TYPE_MASK) {
			n += readVarint32();
		}
		IntList b = new IntList(n);
		b.add(readVarint32());
		for (int j = 1; j < n; j++) {
			int prior = b.get(j - 1);
			b.add(prior + readVarint32());
		}
		return b;
	}

	long readLogTimeUsec() {
		return reverseTime(NB.decodeInt64(nameBuf
	}

	ReflogEntry readLog(long timeUsec) {
		ObjectId oldId = readValueId();
		ObjectId newId = readValueId();
		short tz = readInt16();
		String name = readValueString();
		String email = readValueString();
		String comment = readValueString();

		return new ReflogEntry() {
			@Override
			public ObjectId getOldId() {
				return oldId;
			}

			@Override
			public ObjectId getNewId() {
				return newId;
			}

			@Override
			public PersonIdent getWho() {
				long ms = TimeUnit.MICROSECONDS.toMillis(timeUsec);
				return new PersonIdent(name
			}

			@Override
			public String getComment() {
				return comment;
			}

			@Override
			public CheckoutEntry parseCheckout() {
				return null;
			}
		};
	}

	private ObjectId readValueId() {
		ObjectId id = ObjectId.fromRaw(buf
		ptr += OBJECT_ID_LENGTH;
		return id;
	}

	private String readValueString() {
		int len = readVarint32();
		int end = ptr + len;
		String s = RawParseUtils.decode(UTF_8
		ptr = end;
		return s;
	}

	void readBlock(BlockSource src
			throws IOException {
		readBlockIntoBuf(src
		parseBlockStart(pos);
	}

	private void readBlockIntoBuf(BlockSource src
			throws IOException {
		ByteBuffer b = src.read(pos
		bufLen = b.position();
		if (bufLen <= 0) {
			throw invalidBlock();
		}
		if (b.hasArray() && b.arrayOffset() == 0) {
			buf = b.array();
		} else {
			buf = new byte[bufLen];
			b.flip();
			b.get(buf);
		}
		endPosition = pos + bufLen;
	}

	private void parseBlockStart(long pos) throws IOException {
		ptr = 0;
		if (pos == 0) {
			if (bufLen == FILE_HEADER_LEN) {
				setupEmptyFileBlock();
				return;
			}
		}

		int typeAndSize = NB.decodeInt32(buf
		ptr += 4;

		blockType = (byte) (typeAndSize >>> 24);
		int blockLen;
		if ((blockType & INDEX_BLOCK_TYPE) == INDEX_BLOCK_TYPE) {
			blockType = INDEX_BLOCK_TYPE;
			blockLen = decodeIndexSize(typeAndSize);
		} else {
			blockLen = typeAndSize & 0xffffff;
		}
		if (blockType == LOG_BLOCK_TYPE) {
			long deflatedSize = inflateBuf(blockLen);
			endPosition = pos + 4 + deflatedSize;
		}
		if (bufLen < blockLen) {
			throw invalidBlock();
		} else if (bufLen > blockLen) {
			bufLen = blockLen;
		}

		keysStart = ptr;
		if (blockType != FILE_BLOCK_TYPE) {
			restartCount = 1 + NB.decodeUInt16(buf
			restartIdx = bufLen - (restartCount * 4 + 2);
			keysEnd = restartIdx;
		} else {
			keysEnd = keysStart;
		}
	}

	static int decodeIndexSize(int typeAndSize) {
		return typeAndSize & 0x7fffffff;
	}

	private long inflateBuf(int blockLen) throws IOException {
		byte[] dst = new byte[4 + blockLen];
		System.arraycopy(buf

		long deflatedSize = 0;
		Inflater inf = InflaterCache.get();
		try {
			inf.setInput(buf
			for (int o = 4;;) {
				int n = inf.inflate(dst
				o += n;
				if (inf.finished()) {
					deflatedSize = inf.getBytesRead();
					break;
				} else if (n <= 0) {
					throw invalidBlock();
				}
			}
		} catch (DataFormatException e) {
			throw invalidBlock(e);
		} finally {
			InflaterCache.release(inf);
		}

		buf = dst;
		bufLen = dst.length;
		return deflatedSize;
	}

	private void setupEmptyFileBlock() {
		blockType = FILE_BLOCK_TYPE;
		ptr = FILE_HEADER_LEN;
		restartCount = 0;
		restartIdx = bufLen;
		keysStart = bufLen;
		keysEnd = bufLen;
	}

	void verifyIndex() throws IOException {
		if (blockType != INDEX_BLOCK_TYPE) {
			throw invalidBlock();
		}
	}

	int seekKey(byte[] key) {
		return seek(key
	}

	int seekAbbrevId(byte[] key) {
		return seek(key
	}

	private static int compareKey(boolean useKeyLen
			byte[] b
		if (useKeyLen) {
			return compare(a
		}
		int n = Math.min(bLen
		return compare(a
	}

	private int seek(byte[] key
		int low = 0;
		int end = restartCount;
		for (;;) {
			int mid = (low + end) >>> 1;
			int p = NB.decodeInt32(buf
			int n = readVarint32() >>> 3;
			int cmp = compareKey(useKeyLen
			if (cmp < 0) {
				end = mid;
			} else if (cmp == 0) {
				ptr = p;
				return 0;
				low = mid + 1;
			}
			if (low >= end) {
				return seekToKey(key
			}
		}
	}

	private int seekToKey(byte[] key
			int rPtr
		if (rCmp < 0) {
			if (rIdx == 0) {
				ptr = keysStart;
				return -1;
			}
			ptr = NB.decodeInt32(buf
		} else {
			ptr = rPtr;
		}

		int cmp;
		do {
			int savePtr = ptr;
			parseKey();
			cmp = compareKey(useKeyLen
			if (cmp <= 0) {
				ptr = savePtr;
				return cmp < 0 && savePtr == keysStart ? -1 : 0;
			}
			skipValue();
		} while (ptr < keysEnd);
		return cmp;
	}

	void skipValue() {
		switch (blockType) {
		case REF_BLOCK_TYPE:
			switch (valueType & VALUE_TYPE_MASK) {
			case VALUE_NONE:
				return;
			case VALUE_1ID:
				ptr += OBJECT_ID_LENGTH;
				return;
			case VALUE_2ID:
				ptr += 2 * OBJECT_ID_LENGTH;
				return;
			case VALUE_SYMREF:
			case VALUE_LEN_SPECIFIED:
				skipString();
				return;
			}
			break;

		case OBJ_BLOCK_TYPE: {
			int n = valueType & VALUE_TYPE_MASK;
			if (n == VALUE_TYPE_MASK) {
				n += readVarint32();
			}
			while (n-- > 0) {
				readVarint32();
			}
			return;
		}

		case INDEX_BLOCK_TYPE:
			readVarint32();
			return;

		case LOG_BLOCK_TYPE:
			return;
		}

		throw new IllegalStateException();
	}

	private void skipString() {
		ptr += n;
	}

	private short readInt16() {
		return (short) NB.decodeUInt16(buf
	}

	private int readVarint32() {
		byte c = buf[ptr++];
		int val = c & 0x7f;
		while ((c & 0x80) != 0) {
			c = buf[ptr++];
			val++;
			val <<= 7;
			val |= (c & 0x7f);
		}
		return val;
	}

	private long readVarint64() {
		byte c = buf[ptr++];
		long val = c & 0x7f;
		while ((c & 0x80) != 0) {
			c = buf[ptr++];
			val++;
			val <<= 7;
			val |= (c & 0x7f);
		}
		return val;
	}

	private static Ref newRef(String name) {
		return new ObjectIdRef.Unpeeled(NEW
	}

	private static IOException invalidBlock() {
		return invalidBlock(null);
	}

	private static IOException invalidBlock(Throwable cause) {
		return new IOException(JGitText.get().invalidReftableBlock
	}
}
