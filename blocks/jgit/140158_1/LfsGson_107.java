package org.eclipse.jgit.lfs.server.fs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.eclipse.jgit.lfs.errors.CorruptLongObjectException;
import org.eclipse.jgit.lfs.internal.AtomicObjectOutputStream;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.Constants;

public class ObjectUploadListener implements ReadListener {

	private static Logger LOG = Logger
			.getLogger(ObjectUploadListener.class.getName());

	private final AsyncContext context;

	private final HttpServletResponse response;

	private final ServletInputStream in;

	private final ReadableByteChannel inChannel;

	private final AtomicObjectOutputStream out;

	private WritableByteChannel channel;

	private final ByteBuffer buffer = ByteBuffer.allocateDirect(8192);

	public ObjectUploadListener(FileLfsRepository repository
			AsyncContext context
			HttpServletResponse response
					throws FileNotFoundException
		this.context = context;
		this.response = response;
		this.in = request.getInputStream();
		this.inChannel = Channels.newChannel(in);
		this.out = repository.getOutputStream(id);
		this.channel = Channels.newChannel(out);
		response.setContentType(Constants.CONTENT_TYPE_GIT_LFS_JSON);
	}

	@Override
	public void onDataAvailable() throws IOException {
		while (in.isReady()) {
			if (inChannel.read(buffer) > 0) {
				buffer.flip();
				channel.write(buffer);
				buffer.compact();
			} else {
				buffer.flip();
				while (buffer.hasRemaining()) {
					channel.write(buffer);
				}
				close();
				return;
			}
		}
	}

	@Override
	public void onAllDataRead() throws IOException {
		close();
	}

	protected void close() throws IOException {
		try {
			inChannel.close();
			channel.close();
			if (!response.isCommitted()) {
				response.setStatus(HttpServletResponse.SC_OK);
			}
		} finally {
			context.complete();
		}
	}

	@Override
	public void onError(Throwable e) {
		try {
			out.abort();
			inChannel.close();
			channel.close();
			int status;
			if (e instanceof CorruptLongObjectException) {
				status = HttpStatus.SC_BAD_REQUEST;
				LOG.log(Level.WARNING
			} else {
				status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
				LOG.log(Level.SEVERE
			}
			FileLfsServlet.sendError(response
		} catch (IOException ex) {
			LOG.log(Level.SEVERE
		}
	}
}
