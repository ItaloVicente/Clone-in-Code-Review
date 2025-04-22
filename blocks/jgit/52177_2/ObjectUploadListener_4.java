package org.eclipse.jgit.lfs.server;

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
import org.eclipse.jgit.lfs.lib.LargeObjectRepository;
import org.eclipse.jgit.util.HttpSupport;

class ObjectDownloadListener implements WriteListener {

	private static Logger LOG = Logger
			.getLogger(ObjectDownloadListener.class.getName());

	private final AsyncContext context;

	private final HttpServletResponse response;

	private final ServletOutputStream out;

	private final ReadableByteChannel in;

	private final WritableByteChannel outChannel;

	private final ByteBuffer buffer = ByteBuffer.allocateDirect(8192);

	private boolean eofReached;

	public ObjectDownloadListener(LargeObjectRepository repository
			AsyncContext context
			AnyLongObjectId id) throws IOException {
		this.context = context;
		this.response = response;
		long len = repository.getLength(id);
		this.in = repository.getReadChannel(id);
		this.out = response.getOutputStream();
		this.outChannel = Channels.newChannel(out);

		response.addHeader(HttpSupport.HDR_CONTENT_LENGTH
		response.setContentType(Constants.HDR_APPLICATION_OCTET_STREAM);
	}

	@Override
	public void onWritePossible() throws IOException {
		while (out.isReady()) {
			if (!eofReached && (in.read(buffer)) != -1) {
				buffer.flip();
				outChannel.write(buffer);
				buffer.compact();
			} else {
				eofReached = true;
				in.close();
				buffer.flip();
				while (out.isReady()) {
					if (buffer.hasRemaining()) {
						outChannel.write(buffer);
					} else {
						context.complete();
					}
				}
			}
		}
	}

	@Override
	public void onError(Throwable e) {
		try {
			response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR
					e.getMessage());
			in.close();
			out.close();
		} catch (IOException ex) {
			LOG.log(Level.SEVERE
		}
	}
}
