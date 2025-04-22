
package org.eclipse.jgit.internal.storage.reftable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_HEADER_LEN;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.INDEX_MAGIC;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.RAW_IDLEN;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.isFileHeaderMagic;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.isIndexMagic;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;

class BlockReader {
	enum BlockType {
		REF
	}

	private long position;
	private BlockType blockType;
	private byte[] buf;
	private int bufLen;

	private int keysStart;
	private int keysEnd;
	private int restartIdx;
	private int restartCount;

	private byte[] nameBuf = new byte[256];
	private int ptr;

	long position() {
		return position;
	}

	boolean isRefBlock() {
		return blockType == BlockType.REF;
	}

	boolean next() {
		return ptr < keysEnd;
	}

	int readIndexBlock() {
		int nameLen = readVarint32() >>> 2;
		return readVarint32();
	}

	Ref readRef() throws IOException {
		int pfx = readVarint32();
		int sfxType = readVarint32();
		int nameLen = readRefName(pfx
		String name = RawParseUtils.decode(UTF_8

		switch (sfxType & 0x03) {
			return newRef(name);

			return new ObjectIdRef.PeeledNonTag(PACKED

			ObjectId id1 = readIdValue();
			ObjectId id2 = readIdValue();
			return new ObjectIdRef.PeeledTag(PACKED
		}

			return new SymbolicRef(name

		default:
			throw invalidBlock();
		}
	}

	private int readRefName(int pfx
		if (pfx + len > nameBuf.length) {
			int newsz = Math.max(pfx + len
			nameBuf = Arrays.copyOf(nameBuf
		}
		System.arraycopy(buf
		ptr += len;
		return pfx + len;
	}

	private ObjectId readIdValue() {
		ObjectId id = ObjectId.fromRaw(buf
		ptr += RAW_IDLEN;
		return id;
	}

	private String readValueString() {
		int len = readVarint32();
		int end = ptr + len;
		String s = RawParseUtils.decode(UTF_8
		ptr = end;
		return s;
	}

	private void skipValueString() {
		ptr += readVarint32();
	}

	void readFrom(BlockSource src
			throws IOException {
		ByteBuffer b = src.read(pos
		bufLen = b.position();
		if (b.hasArray() && b.arrayOffset() == 0) {
			buf = b.array();
		} else {
			buf = new byte[bufLen];
			b.flip();
			b.get(buf);
		}
		if (bufLen <= 0) {
			throw invalidBlock();
		}
		position = pos;
		parseBlockStart();
	}

	private void parseBlockStart() throws IOException {
		ptr = position == 0 ? FILE_HEADER_LEN : 0;

		keysStart = ptr;
		if (isIndexMagic(buf
			blockType = BlockType.INDEX;
			keysStart += INDEX_MAGIC.length;
		} else if (ptr == bufLen || isFileHeaderMagic(buf
			blockType = BlockType.FILE_FOOTER;
		} else if (buf[ptr] == 0) {
			blockType = BlockType.REF;
		} else {
			throw invalidBlock();
		}

		if (blockType == BlockType.REF || blockType == BlockType.INDEX) {
			keysEnd = NB.decodeInt32(buf
			restartCount = NB.decodeInt32(buf
			restartIdx = bufLen - (restartCount * 4 + 8);
		}
	}

	void verifyIndex() throws IOException {
		if (blockType != BlockType.INDEX) {
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
			ptr = p;
		}

		for (;;) {
			int savePtr = ptr;
			int pfx = readVarint32();
			int sfxType = readVarint32();
			int nameLen = readRefName(pfx
			int cmp = compare(name
			if (cmp <= 0) {
				ptr = savePtr;
				return cmp < 0 && savePtr == keysStart ? -1 : 0;
			}
			skipValue(sfxType);
			if (ptr == keysEnd) {
				return cmp;
			}
		}
	}

	private void skipValue(int sfxType) {
		if (blockType == BlockType.REF) {
			switch (sfxType & 0x03) {
				break;
			case 0x01:
				ptr += RAW_IDLEN;
				break;
			case 0x02:
				ptr += RAW_IDLEN * 2;
				break;
			case 0x03:
				skipValueString();
				break;
			default:
				throw new IllegalStateException();
			}
		} else if (blockType == BlockType.INDEX) {
			readVarint32();
		}
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
		return new IOException(JGitText.get().invalidReftableBlock);
	}
}
