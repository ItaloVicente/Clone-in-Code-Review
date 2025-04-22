
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class SideBandOutputStream extends OutputStream {
	public static final int CH_DATA = SideBandInputStream.CH_DATA;

	public static final int CH_PROGRESS = SideBandInputStream.CH_PROGRESS;

	public static final int CH_ERROR = SideBandInputStream.CH_ERROR;

	public static final int SMALL_BUF = 1000;

	public static final int MAX_BUF = 65520;

	static final int HDR_SIZE = 5;

	private final OutputStream out;

	private final byte[] buffer;

	private int cnt;

	public SideBandOutputStream(int chan
		if (chan <= 0 || chan > 255)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().channelMustBeInRange1_255
					Integer.valueOf(chan)));
		if (sz <= HDR_SIZE)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().packetSizeMustBeAtLeast
					Integer.valueOf(sz)
		else if (MAX_BUF < sz)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().packetSizeMustBeAtMost
					Integer.valueOf(MAX_BUF)));

		out = os;
		buffer = new byte[sz];
		buffer[4] = (byte) chan;
		cnt = HDR_SIZE;
	}

	void flushBuffer() throws IOException {
		if (HDR_SIZE < cnt)
			writeBuffer();
	}

	@Override
	public void flush() throws IOException {
		flushBuffer();
		out.flush();
	}

	@Override
	public void write(byte[] b
		while (0 < len) {
			int capacity = buffer.length - cnt;
			if (cnt == HDR_SIZE && capacity < len) {
				PacketLineOut.formatLength(buffer
				out.write(buffer
				out.write(b
				off += capacity;
				len -= capacity;

			} else {
				if (capacity == 0)
					writeBuffer();

				int n = Math.min(len
				System.arraycopy(b
				cnt += n;
				off += n;
				len -= n;
			}
		}
	}

	@Override
	public void write(int b) throws IOException {
		if (cnt == buffer.length)
			writeBuffer();
		buffer[cnt++] = (byte) b;
	}

	private void writeBuffer() throws IOException {
		PacketLineOut.formatLength(buffer
		out.write(buffer
		cnt = HDR_SIZE;
	}
}
