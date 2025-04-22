
package org.eclipse.jgit.http.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.NB;

abstract class Sender {
	abstract void close();

	abstract int getContentLength() throws IOException;

	abstract void sendBody(HttpServletResponse out) throws IOException;

	static class FileSender extends Sender {
		private final RandomAccessFile source;

		FileSender(final File path) throws FileNotFoundException {
			source = new RandomAccessFile(path
		}

		void close() {
			try {
				source.close();
			} catch (IOException e) {
			}
		}

		@Override
		int getContentLength() throws IOException {
			return (int) source.length();
		}

		String getTailChecksum() throws IOException {
			final int n = 20;
			final byte[] buf = new byte[n];
			NB.readFully(source.getChannel()
			return ObjectId.fromRaw(buf).getName();
		}

		@Override
		void sendBody(final HttpServletResponse rsp) throws IOException {


			final byte[] buf = new byte[4096];
			final OutputStream out = rsp.getOutputStream();
			try {
				int n;
				while ((n = source.read(buf)) > 0)
					out.write(buf
			} finally {
				out.close();
			}
		}
	}
}
