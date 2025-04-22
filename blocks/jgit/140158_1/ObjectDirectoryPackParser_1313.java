
package org.eclipse.jgit.internal.storage.file;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.text.MessageFormat;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.sha1.SHA1;

class ObjectDirectoryInserter extends ObjectInserter {
	private final FileObjectDatabase db;

	private final WriteConfig config;

	private Deflater deflate;

	ObjectDirectoryInserter(FileObjectDatabase dest
		db = dest;
		config = cfg.get(WriteConfig.KEY);
	}

	@Override
	public ObjectId insert(int type
			throws IOException {
		return insert(type
	}

	private ObjectId insert(
			int type
			throws IOException {
		ObjectId id = idFor(type
		if (!createDuplicate && db.has(id)) {
			return id;
		} else {
			File tmp = toTemp(type
			return insertOneObject(tmp
		}
	}

	@Override
	public ObjectId insert(int type
			throws IOException {
		return insert(type
	}

	ObjectId insert(int type
			throws IOException {
		if (len <= buffer().length) {
			byte[] buf = buffer();
			int actLen = IO.readFully(is
			return insert(type

		} else {
			SHA1 md = digest();
			File tmp = toTemp(md
			ObjectId id = md.toObjectId();
			return insertOneObject(tmp
		}
	}

	private ObjectId insertOneObject(
			File tmp
			throws IOException
		switch (db.insertUnpackedObject(tmp
		case INSERTED:
		case EXISTS_PACKED:
		case EXISTS_LOOSE:
			return id;

		case FAILURE:
		default:
			break;
		}

		final File dst = db.fileFor(id);
		throw new ObjectWritingException(MessageFormat
				.format(JGitText.get().unableToCreateNewObject
	}

	@Override
	public PackParser newPackParser(InputStream in) throws IOException {
		return new ObjectDirectoryPackParser(db
	}

	@Override
	public ObjectReader newReader() {
		return new WindowCursor(db
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void close() {
		if (deflate != null) {
			try {
				deflate.end();
			} finally {
				deflate = null;
			}
		}
	}

	private File toTemp(final SHA1 md
			final InputStream is) throws IOException
			Error {
		boolean delete = true;
		File tmp = newTempFile();
		try {
			FileOutputStream fOut = new FileOutputStream(tmp);
			try {
				OutputStream out = fOut;
				if (config.getFSyncObjectFiles())
					out = Channels.newOutputStream(fOut.getChannel());
				DeflaterOutputStream cOut = compress(out);
				SHA1OutputStream dOut = new SHA1OutputStream(cOut
				writeHeader(dOut

				final byte[] buf = buffer();
				while (len > 0) {
					int n = is.read(buf
					if (n <= 0)
						throw shortInput(len);
					dOut.write(buf
					len -= n;
				}
				dOut.flush();
				cOut.finish();
			} finally {
				if (config.getFSyncObjectFiles())
					fOut.getChannel().force(true);
				fOut.close();
			}

			delete = false;
			return tmp;
		} finally {
			if (delete)
				FileUtils.delete(tmp
		}
	}

	private File toTemp(final int type
			final int len) throws IOException
		boolean delete = true;
		File tmp = newTempFile();
		try {
			FileOutputStream fOut = new FileOutputStream(tmp);
			try {
				OutputStream out = fOut;
				if (config.getFSyncObjectFiles())
					out = Channels.newOutputStream(fOut.getChannel());
				DeflaterOutputStream cOut = compress(out);
				writeHeader(cOut
				cOut.write(buf
				cOut.finish();
			} finally {
				if (config.getFSyncObjectFiles())
					fOut.getChannel().force(true);
				fOut.close();
			}

			delete = false;
			return tmp;
		} finally {
			if (delete)
				FileUtils.delete(tmp
		}
	}

	void writeHeader(OutputStream out
			throws IOException {
		out.write(Constants.encodedTypeString(type));
		out.write((byte) ' ');
		out.write(Constants.encodeASCII(len));
		out.write((byte) 0);
	}

	File newTempFile() throws IOException {
		return File.createTempFile("noz"
	}

	DeflaterOutputStream compress(OutputStream out) {
		if (deflate == null)
			deflate = new Deflater(config.getCompression());
		else
			deflate.reset();
		return new DeflaterOutputStream(out
	}

	private static EOFException shortInput(long missing) {
		return new EOFException(MessageFormat.format(
				JGitText.get().inputDidntMatchLength
	}

	private static class SHA1OutputStream extends FilterOutputStream {
		private final SHA1 md;

		SHA1OutputStream(OutputStream out
			super(out);
			this.md = md;
		}

		@Override
		public void write(int b) throws IOException {
			md.update((byte) b);
			out.write(b);
		}

		@Override
		public void write(byte[] in
			md.update(in
			out.write(in
		}
	}
}
