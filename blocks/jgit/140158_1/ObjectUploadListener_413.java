package org.eclipse.jgit.lfs.server.fs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.util.HttpSupport;

public class ObjectDownloadListener implements WriteListener {

	private static Logger LOG = Logger
			.getLogger(ObjectDownloadListener.class.getName());

	private final AsyncContext context;

	private final HttpServletResponse response;

	private final ServletOutputStream out;

	private final ReadableByteChannel in;

	private final WritableByteChannel outChannel;

	private ByteBuffer buffer = ByteBuffer.allocateDirect(8192);

	public ObjectDownloadListener(FileLfsRepository repository
			AsyncContext context
			AnyLongObjectId id) throws IOException {
		this.context = context;
		this.response = response;
		this.in = repository.getReadChannel(id);
		this.out = response.getOutputStream();
		this.outChannel = Channels.newChannel(out);

		response.addHeader(HttpSupport.HDR_CONTENT_LENGTH
				String.valueOf(repository.getSize(id)));
		response.setContentType(Constants.HDR_APPLICATION_OCTET_STREAM);
	}

	@Override
	public void onWritePossible() throws IOException {
		while (out.isReady()) {
			try {
				buffer.clear();
				if (in.read(buffer) < 0) {
					buffer = null;
				} else {
					buffer.flip();
				}
			} catch (Throwable t) {
				LOG.log(Level.SEVERE
				buffer = null;
			} finally {
				if (buffer != null) {
					outChannel.write(buffer);
				} else {
					try {
						in.close();
					} catch (IOException e) {
						LOG.log(Level.SEVERE
					}
					try {
						out.close();
					} finally {
						context.complete();
					}
					return;
				}
			}
		}
	}

	@Override
	public void onError(Throwable e) {
		try {
			FileLfsServlet.sendError(response
					HttpStatus.SC_INTERNAL_SERVER_ERROR
			context.complete();
			in.close();
		} catch (IOException ex) {
			LOG.log(Level.SEVERE
		}
	}
}
