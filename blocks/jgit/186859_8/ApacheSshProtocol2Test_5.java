package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.LastErrorException;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinUser;

public final class WindowsLibrary {

	private static final Logger LOG = LoggerFactory
			.getLogger(WindowsLibrary.class);


	private static final int PAGEANT_ID = 0x804e_50ba;

	private static class LibraryHolder {

		User32 user;

		Kernel32 kernel;

		LibraryHolder() {
			user = User32.INSTANCE;
			kernel = Kernel32.INSTANCE;
		}
	}

	private static LibraryHolder libraries;

	private static boolean libraryLoaded = false;

	private static synchronized LibraryHolder getLibrary() {
		if (!libraryLoaded) {
			libraryLoaded = true;
			try {
				libraries = new LibraryHolder();
			} catch (Exception | UnsatisfiedLinkError
					| NoClassDefFoundError e) {
				LOG.error(Texts.get().logErrorLoadLibrary
			}
		}
		return libraries;
	}

	boolean isPageantAvailable() {
		LibraryHolder libs = getLibrary();
		if (libs == null) {
			return false;
		}
		HWND window = libs.user.FindWindow(PAGEANT
		return window != null && window != WinBase.INVALID_HANDLE_VALUE;
	}

	interface Pipe extends Closeable {

		void send(byte[] message) throws IOException;

		void receive(byte[] data) throws IOException;
	}

	public static class CopyStruct extends Structure {

		public int dwData = PAGEANT_ID;

		public long cbData;

		public Pointer lpData;

		@Override
		protected List<String> getFieldOrder() {
			return List.of("dwData"
		}
	}

	private static class PipeImpl implements Pipe {

		private final LibraryHolder libs;

		private final HWND window;

		private final byte[] name;

		private final HANDLE file;

		private final Pointer memory;

		private long readPos = 0;

		PipeImpl(LibraryHolder libs
				Pointer memory) {
			this.libs = libs;
			this.window = window;
			this.name = name.getBytes(StandardCharsets.US_ASCII);
			this.file = file;
			this.memory = memory;
		}

		@Override
		public void close() throws IOException {
			WindowsLibrary.close(libs
		}

		private Pointer init(CopyStruct c) {
			c.cbData = name.length + 1;
			c.lpData = new Memory(c.cbData);
			c.lpData.write(0
			c.lpData.setByte(name.length
			c.write();
			return c.getPointer();
		}

		@Override
		public void send(byte[] message) throws IOException {
			memory.write(0
			CopyStruct c = new CopyStruct();
			Pointer p = init(c);
			LRESULT result = libs.user.SendMessage(window
					null
			if (result == null || result.longValue() == 0) {
				throw new IOException(
						systemError(libs
			}
		}

		@Override
		public void receive(byte[] data) throws IOException {
			memory.read(readPos
			readPos += data.length;
		}
	}

	Pipe createPipe(String name
		LibraryHolder libs = getLibrary();
		if (libs == null) {
		}
		HWND window = libs.user.FindWindow(PAGEANT
		if (window == null || window == WinBase.INVALID_HANDLE_VALUE) {
			throw new IOException(Texts.get().msgPageantUnavailable);
		}
		String fileName = name + libs.kernel.GetCurrentThreadId();
		HANDLE file = null;
		Pointer sharedMemory = null;
		try {
			file = libs.kernel.CreateFileMapping(WinBase.INVALID_HANDLE_VALUE
					null
			if (file == null || file == WinBase.INVALID_HANDLE_VALUE) {
				throw new IOException(
						systemError(libs
			}
			sharedMemory = libs.kernel.MapViewOfFile(file
					WinNT.SECTION_MAP_WRITE
			if (sharedMemory == null) {
				throw new IOException(
						systemError(libs
			}
			return new PipeImpl(libs
		} catch (IOException e) {
			close(libs
			throw e;
		} catch (Throwable e) {
			close(libs
			throw new IOException(Texts.get().msgSharedMemoryFailed
		}
	}

	private static void close(LibraryHolder libs
			boolean silent) throws IOException {
		if (memory != null) {
			if (!libs.kernel.UnmapViewOfFile(memory)) {
				String msg = systemError(libs
						Texts.get().errReleaseSharedMemory);
				if (silent) {
					LOG.error(msg);
				} else {
					throw new IOException(msg);
				}
			}
		}
		if (file != null) {
			if (!libs.kernel.CloseHandle(file)) {
				String msg = systemError(libs
				if (silent) {
					LOG.error(msg);
				} else {
					throw new IOException(msg);
				}
			}
		}
	}

	private static String systemError(LibraryHolder libs
		int lastError = libs.kernel.GetLastError();
		String msg;
		try {
			msg = Kernel32Util.formatMessageFromLastErrorCode(lastError);
		} catch (Exception e) {
			String err = e instanceof LastErrorException
					? Integer.toString(((LastErrorException) e).getErrorCode())
					: Texts.get().errUnknown;
			msg = MessageFormat.format(Texts.get().errLastError
					Integer.toString(lastError)
			LOG.error(msg
		}
		return MessageFormat.format(pattern
	}
}
