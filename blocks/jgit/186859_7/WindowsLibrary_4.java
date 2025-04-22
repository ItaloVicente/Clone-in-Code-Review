package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import static org.eclipse.jgit.internal.transport.sshd.agent.connector.Sockets.AF_UNIX;
import static org.eclipse.jgit.internal.transport.sshd.agent.connector.Sockets.DEFAULT_PROTOCOL;
import static org.eclipse.jgit.internal.transport.sshd.agent.connector.Sockets.F_SETFD;
import static org.eclipse.jgit.internal.transport.sshd.agent.connector.Sockets.FD_CLOEXEC;
import static org.eclipse.jgit.internal.transport.sshd.agent.connector.Sockets.SOCK_STREAM;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.sshd.common.SshException;
import org.apache.sshd.common.util.buffer.BufferUtils;
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


	private static Sockets library;

	private static boolean libraryLoaded = false;

	private static synchronized Sockets getLibrary() {
		if (!libraryLoaded) {
			libraryLoaded = true;
			try {
				library = Native.load(Sockets.LIBRARY_NAME
			} catch (Exception | UnsatisfiedLinkError
					| NoClassDefFoundError e) {
				LOG.error(Texts.get().logErrorLoadLibrary
			}
		}
		return library;
	}

	private AtomicBoolean connected = new AtomicBoolean();

	private volatile int socketFd = -1;

	public UnixDomainSocketConnector() {
		super();
	}

	@Override
	public boolean connect() throws IOException {
		String socketFile = SystemReader.getInstance()
				.getenv(ENV_SSH_AUTH_SOCK);
		if (StringUtils.isEmptyOrNull(socketFile)) {
			return false;
		}
		int fd = socketFd;
		synchronized (this) {
			if (connected.get()) {
				return true;
			}
			Sockets sockets = getLibrary();
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
		BufferUtils.putUInt(message.length - 4
		message[4] = command;
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
		byte[] lengthBuf = new byte[4];
		readFully(lengthBuf);
		int length = toLength(command
		byte[] payload = new byte[length];
		readFully(payload);
		return payload;
	}

	private void readFully(byte[] data) throws IOException {
		int n = 0;
		int offset = 0;
		while (offset < data.length
				&& (n = read(data
			offset += n;
		}
		if (offset < data.length) {
			throw new SshException(
					MessageFormat.format(Texts.get().msgShortRead
							Integer.toString(data.length)
							Integer.toString(offset)
		}
	}

	private int read(byte[] buffer
			throws IOException {
		int fd = socketFd;
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
