
package org.eclipse.jgit.util.io;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.eclipse.jgit.util.RawParseUtils;

public class MessageWriter extends Writer {
	private final ByteArrayOutputStream buf;

	private final OutputStreamWriter enc;

	public MessageWriter() {
		buf = new ByteArrayOutputStream();
		enc = new OutputStreamWriter(getRawStream()
	}

	@Override
	public void write(char[] cbuf
		synchronized (buf) {
			enc.write(cbuf
			enc.flush();
		}
	}

	public OutputStream getRawStream() {
		return buf;
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public String toString() {
		return RawParseUtils.decode(buf.toByteArray());
	}
}
