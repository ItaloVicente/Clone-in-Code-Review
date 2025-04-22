
package org.eclipse.jgit.storage.file;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;

class ObjectDirectoryInserter extends ObjectInserter {
	private final ObjectDirectory db;

	private final Config config;

	private Deflater deflate;

	ObjectDirectoryInserter(final ObjectDirectory dest
		db = dest;
		config = cfg;
	}

	@Override
	public ObjectId insert(final int type
			throws IOException {
		final MessageDigest md = digest();
		final File tmp = toTemp(md
		final ObjectId id = ObjectId.fromRaw(md.digest());
		if (db.has(id)) {
			tmp.delete();
			return id;
		}

		final File dst = db.fileFor(id);
		if (tmp.renameTo(dst))
			return id;

		dst.getParentFile().mkdir();
		if (tmp.renameTo(dst))
			return id;

		if (db.has(id)) {
			tmp.delete();
			return id;
		}

		tmp.delete();
		throw new ObjectWritingException("Unable to create new object: " + dst);
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void release() {
		if (deflate != null) {
			try {
				deflate.end();
			} finally {
				deflate = null;
			}
		}
	}

	private File toTemp(final MessageDigest md
			final InputStream is) throws IOException
			Error {
		boolean delete = true;
		File tmp = File.createTempFile("noz"
		try {
			DigestOutputStream dOut = new DigestOutputStream(
					compress(new FileOutputStream(tmp))
			try {
				dOut.write(Constants.encodedTypeString(type));
				dOut.write((byte) ' ');
				dOut.write(Constants.encodeASCII(len));
				dOut.write((byte) 0);

				final byte[] buf = buffer();
				while (len > 0) {
					int n = is.read(buf
					if (n <= 0)
						throw shortInput(len);
					dOut.write(buf
					len -= n;
				}
			} finally {
				dOut.close();
			}

			tmp.setReadOnly();
			delete = false;
			return tmp;
		} finally {
			if (delete)
				tmp.delete();
		}
	}

	private DeflaterOutputStream compress(final OutputStream out) {
		if (deflate == null)
			deflate = new Deflater(config.get(CoreConfig.KEY).getCompression());
		else
			deflate.reset();
		return new DeflaterOutputStream(out
	}

	private static EOFException shortInput(long missing) {
		return new EOFException("Input did not match supplied length. "
				+ missing + " bytes are missing.");
	}
}
