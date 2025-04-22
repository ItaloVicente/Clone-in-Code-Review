package org.eclipse.jgit.transport.http.apache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.entity.AbstractHttpEntity;
import org.eclipse.jgit.util.TemporaryBuffer;

public class TemporaryBufferEntity extends AbstractHttpEntity
		implements AutoCloseable {
	private TemporaryBuffer buffer;

	private Integer contentLength;

	public TemporaryBufferEntity(TemporaryBuffer buffer) {
		this.buffer = buffer;
	}

	public TemporaryBuffer getBuffer() {
		return buffer;
	}

	@Override
	public boolean isRepeatable() {
		return true;
	}

	@Override
	public long getContentLength() {
		if (contentLength != null)
			return contentLength.intValue();
		return buffer.length();
	}

	@Override
	public InputStream getContent() throws IOException
		return buffer.openInputStream();
	}

	@Override
	public void writeTo(OutputStream outstream) throws IOException {
		buffer.writeTo(outstream
	}

	@Override
	public boolean isStreaming() {
		return false;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = Integer.valueOf(contentLength);
	}

	@Override
	public void close() {
		if (buffer != null) {
			buffer.destroy();
		}
	}
}
