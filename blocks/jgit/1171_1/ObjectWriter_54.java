
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.io.InputStream;

public abstract class ObjectStream extends InputStream {
	public abstract int getType();

	public abstract long getSize();

	public static class SmallStream extends ObjectStream {
		private final int type;

		private final byte[] data;

		private int ptr;

		private int mark;

		public SmallStream(ObjectLoader loader) {
			this.type = loader.getType();
			this.data = loader.getCachedBytes();
		}

		@Override
		public int getType() {
			return type;
		}

		@Override
		public long getSize() {
			return data.length;
		}

		@Override
		public int available() {
			return data.length - ptr;
		}

		@Override
		public long skip(long n) {
			int s = (int) Math.min(available()
			ptr += s;
			return s;
		}

		@Override
		public int read() {
			if (ptr == data.length)
				return -1;
			return data[ptr++] & 0xff;
		}

		@Override
		public int read(byte[] b
			if (ptr == data.length)
				return -1;
			int n = Math.min(available()
			System.arraycopy(data
			ptr += n;
			return n;
		}

		@Override
		public boolean markSupported() {
			return true;
		}

		@Override
		public void mark(int readlimit) {
			mark = ptr;
		}

		@Override
		public void reset() {
			ptr = mark;
		}
	}

	public static class Filter extends ObjectStream {
		private final int type;

		private final long size;

		private final InputStream in;

		public Filter(int type
			this.type = type;
			this.size = size;
			this.in = in;
		}

		@Override
		public int getType() {
			return type;
		}

		@Override
		public long getSize() {
			return size;
		}

		@Override
		public int available() throws IOException {
			return in.available();
		}

		@Override
		public long skip(long n) throws IOException {
			return in.skip(n);
		}

		@Override
		public int read() throws IOException {
			return in.read();
		}

		@Override
		public int read(byte[] b
			return in.read(b
		}

		@Override
		public boolean markSupported() {
			return in.markSupported();
		}

		@Override
		public void mark(int readlimit) {
			in.mark(readlimit);
		}

		@Override
		public void reset() throws IOException {
			in.reset();
		}

		@Override
		public void close() throws IOException {
			in.close();
		}
	}
}
