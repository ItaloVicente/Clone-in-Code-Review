
package org.eclipse.jgit.lib;

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
}
