package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.text.html.InlineView;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.util.IntList;

public class IdentOutputStream extends OutputStream {

	static final int BUFFER_SIZE = 8096;

	private static final byte[] BLOB_NAME_PREFIX = new byte[] { ':' };

	private static final byte[] IDENT_PATTERN = new byte[] { '$'

	private final OutputStream out;

	private final byte[] binbuf;

	private int binbufcnt = 0;

	private final byte[] onebytebuf = new byte[1];

	private int patternCounter = 0;

	private boolean isBinary;

	private final byte[] bloblPattern;

	public IdentOutputStream(OutputStream out
		this.out = out;
		byte[] pattern = new byte[blobName.length + 2];
		System.arraycopy(blobName
		this.bloblPattern = pattern;
		binbuf = new byte[BUFFER_SIZE];
	}

	@Override
	public void write(int b) throws IOException {
		onebytebuf[0] = (byte) b;
		write(onebytebuf
	}

	@Override
	public void write(byte[] b) throws IOException {
		int overflow = buffer(b
		if (overflow > 0)
			write(b
	}

	@Override
	public void write(byte[] b
			throws IOException {
		final int overflow = buffer(b
		if (overflow < 0)
			return;
		final int off = startOff + startLen - overflow;
		final int len = overflow;
		if (len == 0)
			return;
		if (isBinary) {
			out.write(b
			return;
		}
		IntList matchedPatternIndexes = matchPattern(b

		if (matchedPatternIndexes.size() == 0) {
			out.write(b
		} else {
			int start = off;
			int remaining = len;
			for (int matchedPatterNumber = 0; matchedPatterNumber < matchedPatternIndexes
					.size(); matchedPatterNumber++) {
				int matchedPatternIndex = matchedPatternIndexes
						.get(matchedPatterNumber);
				int firstPartLength = matchedPatternIndex - start;
				out.write(b
				out.write(BLOB_NAME_PREFIX
				out.write(bloblPattern
				start = matchedPatternIndex;
				remaining -= firstPartLength;
			}
			if (remaining > 0) {
				out.write(b
			}

		}

	}

	private IntList matchPattern(byte[] b
		IntList matchedPatternIndexes = new IntList();
		for (int i = off; i < off + len; ++i) {
			final byte currentByte = b[i];
			if (currentByte == IDENT_PATTERN[patternCounter]) {
				if (patternCounter == IDENT_PATTERN.length - 1) {
					matchedPatternIndexes.add(i);
					patternCounter = 0;
				} else {
					patternCounter++;
				}
			} else {
				patternCounter = 0;
			}
		}
		return matchedPatternIndexes;
	}

	private int buffer(byte[] b
		if (binbufcnt > binbuf.length)
			return len;
		int copy = Math.min(binbuf.length - binbufcnt
		System.arraycopy(b
		binbufcnt += copy;
		int remaining = len - copy;
		if (remaining > 0)
			decideMode();
		return remaining;
	}

	private void decideMode() throws IOException {
		isBinary = RawText.isBinary(binbuf
		int cachedLen = binbufcnt;
		write(binbuf
	}

	@Override
	public void flush() throws IOException {
		if (binbufcnt <= binbuf.length)
			decideMode();
		out.flush();
	}

	@Override
	public void close() throws IOException {
		flush();
		out.close();
	}

}
