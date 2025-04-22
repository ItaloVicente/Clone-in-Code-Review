
package org.eclipse.jgit.internal.storage.reftable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_HEADER_LEN;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.INDEX_BLOCK_TYPE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.LOG_BLOCK_TYPE;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.io.CountingOutputStream;

class ReftableOutputStream extends OutputStream {
	private final byte[] tmp = new byte[10];
	private final CountingOutputStream out;
	private final Deflater deflater;
	private final DeflaterOutputStream compressor;

	private int blockType;
	private int blockSize;
	private int blockStart;
	private byte[] blockBuf;
	private int cur;

	private int blockCount;
	private long paddingUsed;

	ReftableOutputStream(OutputStream os
		blockSize = bs;
		blockBuf = new byte[bs];

		out = new CountingOutputStream(os);
		deflater = new Deflater(Deflater.BEST_COMPRESSION);
		compressor = new DeflaterOutputStream(out
	}

	void setBlockSize(int bs) {
		blockSize = bs;
	}

	@Override
	public void write(int b) {
		ensureBytesAvailableInBlockBuf(1);
		blockBuf[cur++] = (byte) b;
	}

	@Override
	public void write(byte[] b
		ensureBytesAvailableInBlockBuf(cnt);
		System.arraycopy(b
		cur += cnt;
	}

	int bytesWrittenInBlock() {
		return cur;
	}

	int bytesAvailableInBlock() {
		return blockSize - cur;
	}

	long paddingUsed() {
		return paddingUsed;
	}

	int blockCount() {
		return blockCount;
	}

	long size() {
		return out.getCount();
	}

	static int computeVarintSize(long val) {
		int n = 1;
		for (; (val >>>= 7) != 0; n++) {
			val--;
		}
		return n;
	}

	void writeVarint(long val) {
		int n = tmp.length;
		tmp[--n] = (byte) (val & 0x7f);
		while ((val >>>= 7) != 0) {
			tmp[--n] = (byte) (0x80 | (--val & 0x7F));
		}
		write(tmp
	}

	void writeInt16(int val) {
		ensureBytesAvailableInBlockBuf(2);
		NB.encodeInt16(blockBuf
		cur += 2;
	}

	void reserve(int cnt) {
		ensureBytesAvailableInBlockBuf(cnt);
		cur += cnt;
	}

	void patchInt24(int ptr
		NB.encodeInt24(blockBuf
	}

	void writeId(ObjectId id) {
		ensureBytesAvailableInBlockBuf(OBJECT_ID_LENGTH);
		id.copyRawTo(blockBuf
		cur += OBJECT_ID_LENGTH;
	}

	void writeVarintString(String s) {
		writeVarintString(s.getBytes(UTF_8));
	}

	void writeVarintString(byte[] msg) {
		writeVarint(msg.length);
		write(msg
	}

	private void ensureBytesAvailableInBlockBuf(int cnt) {
		if (cur + cnt > blockBuf.length) {
			int n = Math.max(cur + cnt
			blockBuf = Arrays.copyOf(blockBuf
		}
	}

	void flushFileHeader() throws IOException {
		if (cur == FILE_HEADER_LEN && out.getCount() == 0) {
			out.write(blockBuf
			cur = 0;
		}
	}

	void beginBlock(byte id) {
		blockType = id;
		blockStart = cur;
	}

	void flushBlock() throws IOException {
		if (cur > blockSize && !isIndexBlock()) {
			throw new IOException(JGitText.get().overflowedReftableBlock);
		}
		NB.encodeInt32(blockBuf

		if (blockType == LOG_BLOCK_TYPE) {
			deflater.reset();
			out.write(blockBuf
			compressor.write(blockBuf
			compressor.finish();
		} else {
			out.write(blockBuf
		}

		cur = 0;
		blockType = 0;
		blockStart = 0;
		blockCount++;
	}

	void padBetweenBlocksToNextBlock() throws IOException {
		long m = size() % blockSize;
		if (m > 0) {
			int pad = blockSize - (int) m;
			ensureBytesAvailableInBlockBuf(pad);
			Arrays.fill(blockBuf
			out.write(blockBuf
			paddingUsed += pad;
		}
	}

	int estimatePadBetweenBlocks(int currentBlockSize) {
		long m = (size() + currentBlockSize) % blockSize;
		return m > 0 ? blockSize - (int) m : 0;
	}

	private boolean isIndexBlock() {
		return (blockType & INDEX_BLOCK_TYPE) == INDEX_BLOCK_TYPE;
	}

	void finishFile() throws IOException {
		out.write(blockBuf
		cur = 0;
		deflater.end();
	}
}
