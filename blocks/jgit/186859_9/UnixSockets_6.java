package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import static org.eclipse.jgit.internal.transport.sshd.agent.connector.Sockets.AF_UNIX;
import static org.eclipse.jgit.internal.transport.sshd.agent.connector.Sockets.DEFAULT_PROTOCOL;
import static org.eclipse.jgit.internal.transport.sshd.agent.connector.Sockets.ENV_SSH_AUTH_SOCK;
import static org.eclipse.jgit.internal.transport.sshd.agent.connector.Sockets.SOCK_STREAM;
import static org.eclipse.jgit.internal.transport.sshd.agent.connector.UnixSockets.FD_CLOEXEC;
import static org.eclipse.jgit.internal.transport.sshd.agent.connector.UnixSockets.F_SETFD;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.sshd.common.SshException;
import org.eclipse.jgit.transport.sshd.agent.AbstractConnector;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.SystemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.platform.unix.LibCAPI;

public class UnixDomainSocketConnector extends AbstractConnector {

	private static final Logger LOG = LoggerFactory
			.getLogger(UnixDomainSocketConnector.class);

	private static UnixSockets library;

	private static boolean libraryLoaded = false;

	private static synchronized UnixSockets getLibrary() {
		if (!libraryLoaded) {
			libraryLoaded = true;
			try {
				library = Native.load(UnixSockets.LIBRARY_NAME
			} catch (Exception | UnsatisfiedLinkError
					| NoClassDefFoundError e) {
				LOG.error(Texts.get().logErrorLoadLibrary
			}
		}
		return library;
	}

	private final String socketFile;

	private AtomicBoolean connected = new AtomicBoolean();

	private volatile int socketFd = -1;

	public UnixDomainSocketConnector(String socketFile) {
		super();
		String file = socketFile;
		if (StringUtils.isEmptyOrNull(file)) {
			file = SystemReader.getInstance().getenv(ENV_SSH_AUTH_SOCK);
		}
		this.socketFile = file;
	}

	@Override
	public boolean connect() throws IOException {
		if (StringUtils.isEmptyOrNull(socketFile)) {
			return false;
		}
		int fd = socketFd;
		synchronized (this) {
			if (connected.get()) {
				return true;
			}
			UnixSockets sockets = getLibrary();
			if (sockets == null) {
				return false;
			}
			try {
				fd = sockets.socket(AF_UNIX
				sockets.fcntl(fd
				Sockets.SockAddr sockAddr = new Sockets.SockAddr(socketFile
						StandardCharsets.UTF_8);
				sockets.connect(fd
				connected.set(true);
			} catch (LastErrorException e) {
				if (fd >= 0) {
					try {
						sockets.close(fd);
					} catch (LastErrorException e1) {
						e.addSuppressed(e1);
					}
				}
				throw new IOException(MessageFormat
						.format(Texts.get().msgConnectFailed
			}
		}
		socketFd = fd;
		return connected.get();
	}

	@Override
	public synchronized void close() throws IOException {
		int fd = socketFd;
		if (connected.getAndSet(false) && fd >= 0) {
			socketFd = -1;
			try {
				getLibrary().close(fd);
			} catch (LastErrorException e) {
				throw new IOException(MessageFormat.format(
						Texts.get().msgCloseFailed
			}
		}
	}

	@Override
	public byte[] rpc(byte command
		prepareMessage(command
		int fd = socketFd;
		if (!connected.get() || fd < 0) {
		}
		writeFully(fd
		byte[] lengthBuf = new byte[4];
		readFully(fd
		int length = toLength(command
		byte[] payload = new byte[length];
		readFully(fd
		return payload;
	}

	private void writeFully(int fd
		int toWrite = message.length;
		try {
			byte[] buf = message;
			while (toWrite > 0) {
				int written = getLibrary()
						.write(fd
						.intValue();
				if (written < 0) {
					throw new IOException(
							MessageFormat.format(Texts.get().msgSendFailed
									Integer.toString(message.length)
									Integer.toString(toWrite)));
				}
				toWrite -= written;
				if (written > 0 && toWrite > 0) {
					buf = Arrays.copyOfRange(buf
				}
			}
		} catch (LastErrorException e) {
			throw new IOException(
					MessageFormat.format(Texts.get().msgSendFailed
							Integer.toString(message.length)
							Integer.toString(toWrite))
					e);
		}
	}

	private void readFully(int fd
		int n = 0;
		int offset = 0;
		while (offset < data.length
				&& (n = read(fd
			offset += n;
		}
		if (offset < data.length) {
			throw new SshException(
					MessageFormat.format(Texts.get().msgShortRead
							Integer.toString(data.length)
							Integer.toString(offset)
		}
	}

	private int read(int fd
			throws IOException {
		try {
			LibCAPI.size_t toRead = new LibCAPI.size_t(length);
			if (offset == 0) {
				return getLibrary().read(fd
			}
			byte[] data = new byte[length];
			int read = getLibrary().read(fd
			if (read > 0) {
				System.arraycopy(data
			}
			return read;
		} catch (LastErrorException e) {
			throw new IOException(
					MessageFormat.format(Texts.get().msgReadFailed
							Integer.toString(length))
					e);
		}
	}
}
