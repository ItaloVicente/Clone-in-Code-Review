package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.util.io.StreamCopyThread;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

class JschSession implements RemoteSession {
	private final Session sock;
	private final URIish uri;

	public JschSession(final Session session
		sock = session;
		this.uri = uri;
	}

	public Process exec(String command
		return new JschProcess(command
	}

	public void disconnect() {
		if (sock.isConnected()) {
			sock.disconnect();
		}
	}

	public Channel getSftpChannel() throws JSchException {
		return sock.openChannel("sftp");
	}

	private class JschProcess extends Process {
		private ChannelExec channel;

		private final int timeout;

		private InputStream inputStream;

		private OutputStream outputStream;

		private InputStream errStream;

		private JschProcess(final String commandName
				throws TransportException
			timeout = tms;
			try {
				channel = (ChannelExec) sock.openChannel("exec");
				channel.setCommand(commandName);

				inputStream = channel.getInputStream();

				final OutputStream out = channel.getOutputStream();
				if (timeout <= 0) {
					outputStream = out;
				} else {
					final PipedInputStream pipeIn = new PipedInputStream();
					final StreamCopyThread copier = new StreamCopyThread(
							pipeIn
					final PipedOutputStream pipeOut = new PipedOutputStream(
							pipeIn) {
						@Override
						public void flush() throws IOException {
							super.flush();
							copier.flush();
						}

						@Override
						public void close() throws IOException {
							super.close();
							try {
								copier.join(timeout * 1000);
							} catch (final InterruptedException e) {
							}
						}
					};
					copier.start();
					outputStream = pipeOut;
				}

				errStream = channel.getErrStream();

				channel.connect(timeout > 0 ? timeout * 1000 : 0);
				if (!channel.isConnected())
					throw new TransportException(uri
			} catch (final JSchException e) {
				throw new TransportException(uri
			}
		}

		@Override
		public InputStream getInputStream() {
			return inputStream;
		}

		@Override
		public OutputStream getOutputStream() {
			return outputStream;
		}

		@Override
		public InputStream getErrorStream() {
			return errStream;
		}

		@Override
		public int exitValue() {
			if (isRunning())
				throw new IllegalStateException();
			return channel.getExitStatus();
		}

		private boolean isRunning() {
			return channel.getExitStatus() < 0 && channel.isConnected();
		}

		@Override
		public void destroy() {
			if (channel.isConnected()) {
				channel.disconnect();
			}
		}

		@Override
		public int waitFor() throws InterruptedException {
			while (isRunning())
				Thread.sleep(100);
			return exitValue();
		}
	}
}
