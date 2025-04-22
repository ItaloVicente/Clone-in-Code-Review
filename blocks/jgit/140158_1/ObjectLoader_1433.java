
package org.eclipse.jgit.lib;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.util.sha1.SHA1;

public abstract class ObjectInserter implements AutoCloseable {
	public static class Formatter extends ObjectInserter {
		@Override
		public ObjectId insert(int objectType
				throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public PackParser newPackParser(InputStream in) throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public ObjectReader newReader() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void flush() throws IOException {
		}

		@Override
		public void close() {
		}
	}

	public static abstract class Filter extends ObjectInserter {
		protected abstract ObjectInserter delegate();

		@Override
		protected byte[] buffer() {
			return delegate().buffer();
		}

		@Override
		public ObjectId idFor(int type
			return delegate().idFor(type
		}

		@Override
		public ObjectId idFor(int type
			return delegate().idFor(type
		}

		@Override
		public ObjectId idFor(int objectType
				throws IOException {
			return delegate().idFor(objectType
		}

		@Override
		public ObjectId idFor(TreeFormatter formatter) {
			return delegate().idFor(formatter);
		}

		@Override
		public ObjectId insert(int type
			return delegate().insert(type
		}

		@Override
		public ObjectId insert(int type
				throws IOException {
			return delegate().insert(type
		}

		@Override
		public ObjectId insert(int objectType
				throws IOException {
			return delegate().insert(objectType
		}

		@Override
		public PackParser newPackParser(InputStream in) throws IOException {
			return delegate().newPackParser(in);
		}

		@Override
		public ObjectReader newReader() {
			final ObjectReader dr = delegate().newReader();
			return new ObjectReader.Filter() {
				@Override
				protected ObjectReader delegate() {
					return dr;
				}

				@Override
				public ObjectInserter getCreatedFromInserter() {
					return ObjectInserter.Filter.this;
				}
			};
		}

		@Override
		public void flush() throws IOException {
			delegate().flush();
		}

		@Override
		public void close() {
			delegate().close();
		}
	}

	private final SHA1 hasher = SHA1.newInstance();

	private byte[] tempBuffer;

	protected ObjectInserter() {
	}

	protected byte[] buffer() {
		byte[] b = tempBuffer;
		if (b == null)
			tempBuffer = b = new byte[8192];
		return b;
	}

	protected SHA1 digest() {
		return hasher.reset();
	}

	public ObjectId idFor(int type
		return idFor(type
	}

	public ObjectId idFor(int type
		SHA1 md = SHA1.newInstance();
		md.update(Constants.encodedTypeString(type));
		md.update((byte) ' ');
		md.update(Constants.encodeASCII(len));
		md.update((byte) 0);
		md.update(data
		return md.toObjectId();
	}

	public ObjectId idFor(int objectType
			throws IOException {
		SHA1 md = SHA1.newInstance();
		md.update(Constants.encodedTypeString(objectType));
		md.update((byte) ' ');
		md.update(Constants.encodeASCII(length));
		md.update((byte) 0);
		byte[] buf = buffer();
		while (length > 0) {
			int n = in.read(buf
			if (n < 0)
				throw new EOFException(JGitText.get().unexpectedEndOfInput);
			md.update(buf
			length -= n;
		}
		return md.toObjectId();
	}

	public ObjectId idFor(TreeFormatter formatter) {
		return formatter.computeId(this);
	}

	public final ObjectId insert(TreeFormatter formatter) throws IOException {
		return formatter.insertTo(this);
	}

	public final ObjectId insert(CommitBuilder builder) throws IOException {
		return insert(Constants.OBJ_COMMIT
	}

	public final ObjectId insert(TagBuilder builder) throws IOException {
		return insert(Constants.OBJ_TAG
	}

	public ObjectId insert(int type
			throws IOException {
		return insert(type
	}

	public ObjectId insert(int type
			throws IOException {
		return insert(type
	}

	public abstract ObjectId insert(int objectType
			throws IOException;

	public abstract PackParser newPackParser(InputStream in) throws IOException;

	public abstract ObjectReader newReader();

	public abstract void flush() throws IOException;

	@Override
	public abstract void close();
}
