
package org.eclipse.jgit.internal.storage.reftable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_HEADER_LEN;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.INDEX_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.LOG_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.REF_BLOCK_TYPE;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.CheckoutEntry;
import org.eclipse.jgit.lib.InflaterCache;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;

class BlockReader {
	private long blockStartPosition;
	private long blockEndPosition;
	private byte blockType;

	private byte[] buf;
	private int bufLen;
	private int ptr;

	private int keysStart;
	private int keysEnd;
	private int restartIdx;
	private int restartCount;

	private byte[] nameBuf = new byte[256];
	private int nameLen;
	private int type;

	byte type() {
		return blockType;
	}

	long blockEndPosition() {
		return blockEndPosition;
	}

	boolean next() {
		return ptr < keysEnd;
	}

	void parseEntryName() {
		int pfx = readVarint32();
		type = readVarint32();
		int sfx = type >>> 2;
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
			len -= 5;
		}
		return RawParseUtils.decode(UTF_8
	}

	boolean checkNameMatches(byte[] match) {
		int len = nameLen;
		if (blockType == LOG_BLOCK_TYPE) {
			len -= 5;
		}
		if (len < match.length) {
			return false;
		}

		boolean isPrefixMatch = match[match.length - 1] == '/';
		if (isPrefixMatch) {
			return compare(match
		}
		return len == match.length && compare(match
	}

	long readIndex() throws IOException {
		if (blockType != INDEX_BLOCK_TYPE) {
			throw invalidBlock();
		}

		int n = readVarint32() >>> 2;
		return readVarint64();
	}

	Ref readRef() throws IOException {
		String name = RawParseUtils.decode(UTF_8
		switch (type & 0x03) {
			return newRef(name);

			return new ObjectIdRef.PeeledNonTag(PACKED

			ObjectId id1 = readValueId();
			ObjectId id2 = readValueId();
			return new ObjectIdRef.PeeledTag(PACKED
		}

			return new SymbolicRef(name

		default:
			throw invalidBlock();
		}
	}

	ReflogEntry readLog() {
		int when = 0xffffffff - NB.decodeInt32(nameBuf
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

	void readFrom(BlockSource src
			throws IOException {
		blockStartPosition = pos;
		blockEndPosition = pos + estBlockSize;
		loadBlockIntoBuf(src
		parseBlockStart();
	}

	private void loadBlockIntoBuf(BlockSource src
			throws IOException {
		ByteBuffer b = src.read(blockStartPosition
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
	}

	private void parseBlockStart() throws IOException {
		ptr = 0;
		if (blockStartPosition == 0) {
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
			blockLen = typeAndSize & 0x7fffffff;
		} else {
			blockLen = typeAndSize & 0xffffff;
		}
		if (blockType == LOG_BLOCK_TYPE) {
			inflateBuf(blockLen);
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

	private void inflateBuf(int blockLen) throws IOException {
		byte[] tmp = new byte[4 + blockLen];
		System.arraycopy(buf

		Inflater inf = InflaterCache.get();
		try {
			inf.setInput(buf
			for (int o = 4;;) {
				int n = inf.inflate(tmp
				o += n;
				if (inf.finished()) {
					long blockSize = 4 + inf.getBytesRead();
					blockEndPosition = blockStartPosition + blockSize;
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

		buf = tmp;
		bufLen = tmp.length;
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

	int seek(byte[] name) {
		int low = 0;
		int end = restartCount;
		for (;;) {
			int mid = (low + end) >>> 1;
			int p = NB.decodeInt32(buf
			int len = readVarint32() >>> 2;
			int cmp = compare(name
			if (cmp < 0) {
				end = mid;
			} else if (cmp == 0) {
				ptr = p;
				return 0;
				low = mid + 1;
			}
			if (low >= end) {
				return seekToKey(name
			}
		}
	}

	private int seekToKey(byte[] name
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
			parseEntryName();
			cmp = compare(name
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
			switch (type & 0x03) {
				return;
			case 0x01:
				ptr += OBJECT_ID_LENGTH;
				return;
			case 0x02:
				ptr += 2 * OBJECT_ID_LENGTH;
				return;
			case 0x03:
				skipString();
				return;
			}
			break;

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

	private static int compare(byte[] a
		int bEnd = bi + bLen;
		for (int ai = 0; ai < a.length && bi < bEnd; ai++
			int c = (a[ai] & 0xff) - (b[bi] & 0xff);
			if (c != 0) {
				return c;
			}
		}
		return a.length - bLen;
	}

	private static IOException invalidBlock() {
		return invalidBlock(null);
	}

	private static IOException invalidBlock(Throwable cause) {
		return new IOException(JGitText.get().invalidReftableBlock
	}
}
