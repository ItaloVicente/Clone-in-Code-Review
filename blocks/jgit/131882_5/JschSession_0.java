package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public interface FtpChannel {

	static class FtpException extends IOException {

		private static final long serialVersionUID = 7176525179280330876L;

		public static final int OK = 0;

		public static final int EOF = 1;

		public static final int NO_SUCH_FILE = 2;

		public static final int NO_PERMISSION = 3;

		public static final int UNSPECIFIED_FAILURE = 4;

		public static final int PROTOCOL_ERROR = 5;

		public static final int UNSUPPORTED = 8;

		private final int status;

		public FtpException(String message
			super(message);
			this.status = status;
		}

		public FtpException(String message
			super(message
			this.status = status;
		}

		public int getStatus() {
			return status;
		}
	}

	void connect(int timeout

	void disconnect();

	boolean isConnected();

	void cd(String path) throws IOException;

	String pwd() throws IOException;

	interface DirEntry {
		String getFilename();

		long getModifiedTime();

		boolean isDirectory();
	}

	Collection<DirEntry> ls(String path) throws IOException;

	void rmdir(String path) throws IOException;

	void mkdir(String path) throws IOException;

	InputStream get(String path) throws IOException;

	OutputStream put(String path) throws IOException;

	void rm(String path) throws IOException;

	void rename(String from

}
