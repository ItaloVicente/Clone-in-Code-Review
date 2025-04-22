
package org.eclipse.jgit.lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.ObjectWritingException;

public abstract class ObjectInserter {
	private static final byte[] htree = Constants.encodeASCII("tree");

	private static final byte[] hparent = Constants.encodeASCII("parent");

	private static final byte[] hauthor = Constants.encodeASCII("author");

	private static final byte[] hcommitter = Constants.encodeASCII("committer");

	private static final byte[] hencoding = Constants.encodeASCII("encoding");

	public static class Formatter extends ObjectInserter {
		@Override
		public ObjectId insert(int objectType
				throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void flush() throws IOException {
		}

		@Override
		public void release() {
		}
	}

	private final MessageDigest digest;

	private byte[] tempBuffer;

	protected ObjectInserter() {
		digest = Constants.newMessageDigest();
	}

	protected byte[] buffer() {
		if (tempBuffer == null)
			tempBuffer = new byte[8192];
		return tempBuffer;
	}

	protected MessageDigest digest() {
		digest.reset();
		return digest;
	}

	public ObjectId idFor(int type
		return idFor(type
	}

	public ObjectId idFor(int type
		MessageDigest md = digest();
		md.update(Constants.encodedTypeString(type));
		md.update((byte) ' ');
		md.update(Constants.encodeASCII(len));
		md.update((byte) 0);
		md.update(data
		return ObjectId.fromRaw(md.digest());
	}

	public ObjectId idFor(int objectType
			throws IOException {
		MessageDigest md = digest();
		md.update(Constants.encodedTypeString(objectType));
		md.update((byte) ' ');
		md.update(Constants.encodeASCII(length));
		md.update((byte) 0);
		byte[] buf = buffer();
		while (length > 0) {
			int n = in.read(buf
			if (n < 0)
				throw new EOFException("Unexpected end of input");
			md.update(buf
			length -= n;
		}
		return ObjectId.fromRaw(md.digest());
	}

	public ObjectId insert(final int type
			throws IOException {
		return insert(type
	}

	public ObjectId insert(int type
			throws IOException {
		return insert(type
	}

	public abstract ObjectId insert(int objectType
			throws IOException;

	public abstract void flush() throws IOException;

	public abstract void release();

	public final byte[] format(Tree tree) throws IOException {
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		for (TreeEntry e : tree.members()) {
			ObjectId id = e.getId();
			if (id == null)
				throw new ObjectWritingException(MessageFormat.format(JGitText
						.get().objectAtPathDoesNotHaveId

			e.getMode().copyTo(o);
			o.write(' ');
			o.write(e.getNameUTF8());
			o.write(0);
			id.copyRawTo(o);
		}
		return o.toByteArray();
	}

	public final byte[] format(Commit commit)
			throws UnsupportedEncodingException {
		String encoding = commit.getEncoding();
		if (encoding == null)
			encoding = Constants.CHARACTER_ENCODING;

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamWriter w = new OutputStreamWriter(os
		try {
			os.write(htree);
			os.write(' ');
			commit.getTreeId().copyTo(os);
			os.write('\n');

			ObjectId[] ps = commit.getParentIds();
			for (int i = 0; i < ps.length; ++i) {
				os.write(hparent);
				os.write(' ');
				ps[i].copyTo(os);
				os.write('\n');
			}

			os.write(hauthor);
			os.write(' ');
			w.write(commit.getAuthor().toExternalString());
			w.flush();
			os.write('\n');

			os.write(hcommitter);
			os.write(' ');
			w.write(commit.getCommitter().toExternalString());
			w.flush();
			os.write('\n');

			if (!encoding.equals(Constants.CHARACTER_ENCODING)) {
				os.write(hencoding);
				os.write(' ');
				os.write(Constants.encodeASCII(encoding));
				os.write('\n');
			}

			os.write('\n');
			w.write(commit.getMessage());
			w.flush();
		} catch (IOException err) {
			throw new RuntimeException(err);
		}
		return os.toByteArray();
	}

	public final byte[] format(Tag tag) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamWriter w = new OutputStreamWriter(os
		try {
			w.write("object ");
			tag.getObjId().copyTo(w);
			w.write('\n');

			w.write("type ");
			w.write(tag.getType());
			w.write("\n");

			w.write("tag ");
			w.write(tag.getTag());
			w.write("\n");

			w.write("tagger ");
			w.write(tag.getAuthor().toExternalString());
			w.write('\n');

			w.write('\n');
			w.write(tag.getMessage());
			w.close();
		} catch (IOException err) {
			throw new RuntimeException(err);
		}
		return os.toByteArray();
	}
}
