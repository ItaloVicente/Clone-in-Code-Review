
package org.eclipse.jgit.transport;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.io.IsolatedOutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class JschSession implements RemoteSession {
	final Session sock;
	final URIish uri;

	public JschSession(Session session
		sock = session;
		this.uri = uri;
	}

	@Override
	public Process exec(String command
		return new JschProcess(command
	}

	@Override
	public void disconnect() {
		if (sock.isConnected())
			sock.disconnect();
	}

	@Deprecated
	public Channel getSftpChannel() throws JSchException {
	}

	@Override
	public FtpChannel getFtpChannel() {
		return new JschFtpChannel();
	}

	private class JschProcess extends Process {
		private ChannelExec channel;

		final int timeout;

		private InputStream inputStream;

		private OutputStream outputStream;

		private InputStream errStream;

		JschProcess(String commandName
				throws TransportException
			timeout = tms;
			try {
				channel.setCommand(commandName);
				setupStreams();
				channel.connect(timeout > 0 ? timeout * 1000 : 0);
				if (!channel.isConnected()) {
					closeOutputStream();
					throw new TransportException(uri
							JGitText.get().connectionFailed);
				}
			} catch (JSchException e) {
				closeOutputStream();
				throw new TransportException(uri
			}
		}

		private void closeOutputStream() {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ioe) {
				}
			}
		}

		private void setupStreams() throws IOException {
			inputStream = channel.getInputStream();

			OutputStream out = channel.getOutputStream();
			if (timeout <= 0) {
				outputStream = out;
			} else {
				IsolatedOutputStream i = new IsolatedOutputStream(out);
				outputStream = new BufferedOutputStream(i
			}

			errStream = channel.getErrStream();
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
			if (channel.isConnected())
				channel.disconnect();
			closeOutputStream();
		}

		@Override
		public int waitFor() throws InterruptedException {
			while (isRunning())
				Thread.sleep(100);
			return exitValue();
		}
	}

	private class JschFtpChannel implements FtpChannel {

		private ChannelSftp ftp;

		@Override
		public void connect(int timeout
			try {
				ftp.connect((int) unit.toMillis(timeout));
			} catch (JSchException e) {
				ftp = null;
				throw new IOException(e.getLocalizedMessage()
			}
		}

		@Override
		public void disconnect() {
			ftp.disconnect();
			ftp = null;
		}

		private <T> T map(Callable<T> op) throws IOException {
			try {
				return op.call();
			} catch (Exception e) {
				if (e instanceof SftpException) {
					throw new FtpChannel.FtpException(e.getLocalizedMessage()
							((SftpException) e).id
				}
				throw new IOException(e.getLocalizedMessage()
			}
		}

		@Override
		public boolean isConnected() {
			return ftp != null && sock.isConnected();
		}

		@Override
		public void cd(String path) throws IOException {
			map(() -> {
				ftp.cd(path);
				return null;
			});
		}

		@Override
		public String pwd() throws IOException {
			return map(() -> ftp.pwd());
		}

		@Override
		public Collection<DirEntry> ls(String path) throws IOException {
			return map(() -> {
				List<DirEntry> result = new ArrayList<>();
				for (Object e : ftp.ls(path)) {
					ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) e;
					result.add(new DirEntry() {

						@Override
						public String getFilename() {
							return entry.getFilename();
						}

						@Override
						public long getModifiedTime() {
							return entry.getAttrs().getMTime();
						}

						@Override
						public boolean isDirectory() {
							return entry.getAttrs().isDir();
						}
					});
				}
				return result;
			});
		}

		@Override
		public void rmdir(String path) throws IOException {
			map(() -> {
				ftp.rm(path);
				return null;
			});
		}

		@Override
		public void mkdir(String path) throws IOException {
			map(() -> {
				ftp.mkdir(path);
				return null;
			});
		}

		@Override
		public InputStream get(String path) throws IOException {
			return map(() -> ftp.get(path));
		}

		@Override
		public OutputStream put(String path) throws IOException {
			return map(() -> ftp.put(path));
		}

		@Override
		public void rm(String path) throws IOException {
			map(() -> {
				ftp.rm(path);
				return null;
			});
		}

		@Override
		public void rename(String from
			map(() -> {
				if (hasPosixRename()) {
					ftp.rename(from
				} else if (!to.equals(from)) {
					delete(to);
					ftp.rename(from
				}
				return null;
			});
		}

		private boolean hasPosixRename() {
		}
	}
}
