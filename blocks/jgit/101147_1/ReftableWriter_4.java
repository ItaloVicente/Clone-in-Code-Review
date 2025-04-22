
package org.eclipse.jgit.internal.storage.reftable;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.NB;

class ReftableOutputStream extends FilterOutputStream {
	private final byte[] tmp = new byte[OBJECT_ID_LENGTH];
	private final int blockSize;

	private int cur;
	private long size;
	private long paddingUsed;
	private int blockCount;

	ReftableOutputStream(OutputStream os
		super(os);
		this.blockSize = blockSize;
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
		cur++;
		size++;
	}

	@Override
	public void write(byte[] b
		out.write(b
		cur += cnt;
		size += cnt;
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
		return size;
	}

	static int computeVarintSize(int val) {
		int n = 1;
		for (; (val >>>= 7) != 0; n++) {
			val--;
		}
		return n;
	}

	void writeVarint(int val) throws IOException {
		int n = tmp.length;
		tmp[--n] = (byte) (val & 0x7f);
		while ((val >>>= 7) != 0) {
			tmp[--n] = (byte) (0x80 | (--val & 0x7F));
		}
		write(tmp
	}

	void writeInt32(int val) throws IOException {
		NB.encodeInt32(tmp
		write(tmp
	}

	void write(ObjectId id) throws IOException {
		id.copyRawTo(tmp
		write(tmp
	}

	void pad(int len) throws IOException {
		paddingUsed += len;
		Arrays.fill(tmp
		while (len > 0) {
			int n = Math.min(len
			write(tmp
			len -= n;
		}
	}

	void finishBlock(boolean mustFillBlock) throws IOException {
		if (cur < blockSize && mustFillBlock) {
			throw new IOException(JGitText.get().underflowedReftableBlock);
		} else if (cur > blockSize) {
			throw new IOException(JGitText.get().overflowedReftableBlock);
		}
		cur = 0;
		blockCount++;
	}
}
