package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.sshd.common.SshException;
import org.eclipse.jgit.transport.sshd.agent.AbstractConnector;
import org.eclipse.jgit.transport.sshd.agent.ConnectorFactory.ConnectorDescriptor;
import org.eclipse.jgit.util.StringUtils;

import com.sun.jna.LastErrorException;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinError;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

public class WinPipeConnector extends AbstractConnector {


	public static final ConnectorDescriptor DESCRIPTOR = new ConnectorDescriptor() {

		@Override
		public String getIdentityAgent() {
			return CANONICAL_PIPE_NAME;
		}

		@Override
		public String getDisplayName() {
			return Texts.get().winOpenSsh;
		}
	};

	private static final int FILE_SHARE_NONE = 0;

	private static final int FILE_ATTRIBUTE_NONE = 0;

	private final String pipeName;

	private final AtomicBoolean connected = new AtomicBoolean();

	private volatile HANDLE fileHandle;

	public WinPipeConnector(String pipeName) {
		this.pipeName = pipeName.replace('/'
	}

	@Override
	public boolean connect() throws IOException {
		if (StringUtils.isEmptyOrNull(pipeName)) {
			return false;
		}
		HANDLE file = fileHandle;
		synchronized (this) {
			if (connected.get()) {
				return true;
			}
			LibraryHolder libs = LibraryHolder.getLibrary();
			if (libs == null) {
				return false;
			}
			file = libs.kernel.CreateFile(pipeName
					WinNT.GENERIC_READ | WinNT.GENERIC_WRITE
					null
			if (file == null || WinBase.INVALID_HANDLE_VALUE.equals(file)) {
				int errorCode = libs.kernel.GetLastError();
				if (errorCode == WinError.ERROR_FILE_NOT_FOUND
						&& CANONICAL_PIPE_NAME.equalsIgnoreCase(pipeName)) {
					return false;
				}
				LastErrorException cause = new LastErrorException(
						libs.systemError());
				throw new IOException(MessageFormat
						.format(Texts.get().msgConnectPipeFailed
						cause);
			}
			connected.set(true);
		}
		fileHandle = file;
		return connected.get();
	}

	@Override
	public synchronized void close() throws IOException {
		HANDLE file = fileHandle;
		if (connected.getAndSet(false) && fileHandle != null) {
			fileHandle = null;
			LibraryHolder libs = LibraryHolder.getLibrary();
			boolean success = libs.kernel.CloseHandle(file);
			if (!success) {
				LastErrorException cause = new LastErrorException(
						libs.systemError());
				throw new IOException(MessageFormat
						.format(Texts.get().msgCloseFailed
			}
		}
	}

	@Override
	public byte[] rpc(byte command
		prepareMessage(command
		HANDLE file = fileHandle;
		if (!connected.get() || file == null) {
		}
		LibraryHolder libs = LibraryHolder.getLibrary();
		writeFully(libs
		byte[] lengthBuf = new byte[4];
		readFully(libs
		int length = toLength(command
		byte[] payload = new byte[length];
		readFully(libs
		return payload;
	}

	private void writeFully(LibraryHolder libs
			throws IOException {
		byte[] buf = message;
		int toWrite = buf.length;
		try {
			while (toWrite > 0) {
				IntByReference written = new IntByReference();
				boolean success = libs.kernel.WriteFile(file
						written
				if (!success) {
					throw new LastErrorException(libs.systemError());
				}
				int actuallyWritten = written.getValue();
				toWrite -= actuallyWritten;
				if (actuallyWritten > 0 && toWrite > 0) {
					buf = Arrays.copyOfRange(buf
				}
			}
		} catch (LastErrorException e) {
			throw new IOException(MessageFormat.format(
					Texts.get().msgSendFailed
					Integer.toString(toWrite))
		}
	}

	private void readFully(LibraryHolder libs
			throws IOException {
		int n = 0;
		int offset = 0;
		while (offset < data.length && (n = read(libs
				data.length - offset)) > 0) {
			offset += n;
		}
		if (offset < data.length) {
			throw new SshException(MessageFormat.format(
					Texts.get().msgShortRead
					Integer.toString(offset)
		}
	}

	private int read(LibraryHolder libs
			int length) throws IOException {
		try {
			int toRead = length;
			IntByReference read = new IntByReference();
			if (offset == 0) {
				boolean success = libs.kernel.ReadFile(file
						read
				if (!success) {
					throw new LastErrorException(libs.systemError());
				}
				return read.getValue();
			}
			byte[] data = new byte[length];
			boolean success = libs.kernel.ReadFile(file
					null);
			if (!success) {
				throw new LastErrorException(libs.systemError());
			}
			int actuallyRead = read.getValue();
			if (actuallyRead > 0) {
				System.arraycopy(data
			}
			return actuallyRead;
		} catch (LastErrorException e) {
			throw new IOException(MessageFormat.format(
					Texts.get().msgReadFailed
		}
	}
}
