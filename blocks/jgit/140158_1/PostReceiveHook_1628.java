
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.RawParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketLineOut {
	private static final Logger log = LoggerFactory.getLogger(PacketLineOut.class);

	private final OutputStream out;

	private final byte[] lenbuffer;

	private boolean flushOnEnd;

	public PacketLineOut(OutputStream outputStream) {
		out = outputStream;
		lenbuffer = new byte[5];
		flushOnEnd = true;
	}

	public void setFlushOnEnd(boolean flushOnEnd) {
		this.flushOnEnd = flushOnEnd;
	}

	public void writeString(String s) throws IOException {
		writePacket(Constants.encode(s));
	}

	public void writePacket(byte[] packet) throws IOException {
		writePacket(packet
	}

	public void writePacket(byte[] buf
		formatLength(len + 4);
		out.write(lenbuffer
		out.write(buf
		if (log.isDebugEnabled()) {
			String s = RawParseUtils.decode(UTF_8
		}
	}

	public void writeDelim() throws IOException {
		formatLength(1);
		out.write(lenbuffer
	}

	public void end() throws IOException {
		formatLength(0);
		out.write(lenbuffer
		if (flushOnEnd)
			flush();
	}

	public void flush() throws IOException {
		out.flush();
	}

	private static final byte[] hexchar = { '0'
			'7'

	private void formatLength(int w) {
		formatLength(lenbuffer
	}

	static void formatLength(byte[] lenbuffer
		int o = 3;
		while (o >= 0 && w != 0) {
			lenbuffer[o--] = hexchar[w & 0xf];
			w >>>= 4;
		}
		while (o >= 0)
			lenbuffer[o--] = '0';
	}
}
