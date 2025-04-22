
package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;

public class CommitBuilder {
	private static final ObjectId[] EMPTY_OBJECTID_LIST = new ObjectId[0];







	private ObjectId treeId;

	private ObjectId[] parentIds;

	private PersonIdent author;

	private PersonIdent committer;

	private GpgSignature gpgSignature;

	private String message;

	private Charset encoding;

	public CommitBuilder() {
		parentIds = EMPTY_OBJECTID_LIST;
		encoding = UTF_8;
	}

	public ObjectId getTreeId() {
		return treeId;
	}

	public void setTreeId(AnyObjectId id) {
		treeId = id.copy();
	}

	public PersonIdent getAuthor() {
		return author;
	}

	public void setAuthor(PersonIdent newAuthor) {
		author = newAuthor;
	}

	public PersonIdent getCommitter() {
		return committer;
	}

	public void setCommitter(PersonIdent newCommitter) {
		committer = newCommitter;
	}

	public void setGpgSignature(GpgSignature newSignature) {
		gpgSignature = newSignature;
	}

	public GpgSignature getGpgSignature() {
		return gpgSignature;
	}

	public ObjectId[] getParentIds() {
		return parentIds;
	}

	public void setParentId(AnyObjectId newParent) {
		parentIds = new ObjectId[] { newParent.copy() };
	}

	public void setParentIds(AnyObjectId parent1
		parentIds = new ObjectId[] { parent1.copy()
	}

	public void setParentIds(ObjectId... newParents) {
		parentIds = new ObjectId[newParents.length];
		for (int i = 0; i < newParents.length; i++)
			parentIds[i] = newParents[i].copy();
	}

	public void setParentIds(List<? extends AnyObjectId> newParents) {
		parentIds = new ObjectId[newParents.size()];
		for (int i = 0; i < newParents.size(); i++)
			parentIds[i] = newParents.get(i).copy();
	}

	public void addParentId(AnyObjectId additionalParent) {
		if (parentIds.length == 0) {
			setParentId(additionalParent);
		} else {
			ObjectId[] newParents = new ObjectId[parentIds.length + 1];
			System.arraycopy(parentIds
			newParents[parentIds.length] = additionalParent.copy();
			parentIds = newParents;
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String newMessage) {
		message = newMessage;
	}

	@Deprecated
	public void setEncoding(String encodingName) {
		encoding = Charset.forName(encodingName);
	}

	public void setEncoding(Charset enc) {
		encoding = enc;
	}

	public Charset getEncoding() {
		return encoding;
	}

	public byte[] build() throws UnsupportedEncodingException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStreamWriter w = new OutputStreamWriter(os
		try {
			os.write(htree);
			os.write(' ');
			getTreeId().copyTo(os);
			os.write('\n');

			for (ObjectId p : getParentIds()) {
				os.write(hparent);
				os.write(' ');
				p.copyTo(os);
				os.write('\n');
			}

			os.write(hauthor);
			os.write(' ');
			w.write(getAuthor().toExternalString());
			w.flush();
			os.write('\n');

			os.write(hcommitter);
			os.write(' ');
			w.write(getCommitter().toExternalString());
			w.flush();
			os.write('\n');

			if (getGpgSignature() != null) {
				os.write(hgpgsig);
				os.write(' ');
				writeGpgSignatureString(getGpgSignature().toExternalString()
				os.write('\n');
			}

			if (getEncoding() != UTF_8) {
				os.write(hencoding);
				os.write(' ');
				os.write(Constants.encodeASCII(getEncoding().name()));
				os.write('\n');
			}

			os.write('\n');

			if (getMessage() != null) {
				w.write(getMessage());
				w.flush();
			}
		} catch (IOException err) {
			throw new RuntimeException(err);
		}
		return os.toByteArray();
	}

	static void writeGpgSignatureString(String in
			throws IOException
		for (int i = 0; i < in.length(); ++i) {
			char ch = in.charAt(i);
			if (ch == '\r') {
				if (i + 1 < in.length() && in.charAt(i + 1) == '\n') {
					out.write('\n');
					out.write(' ');
					++i;
				} else {
					out.write('\n');
					out.write(' ');
				}
			} else if (ch == '\n') {
				out.write('\n');
				out.write(' ');
			} else {
				if (ch > 127)
					throw new IllegalArgumentException(MessageFormat
							.format(JGitText.get().notASCIIString
				out.write(ch);
			}
		}
	}

	public byte[] toByteArray() throws UnsupportedEncodingException {
		return build();
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("Commit");
		r.append("={\n");

		r.append("tree ");
		r.append(treeId != null ? treeId.name() : "NOT_SET");
		r.append("\n");

		for (ObjectId p : parentIds) {
			r.append("parent ");
			r.append(p.name());
			r.append("\n");
		}

		r.append("author ");
		r.append(author != null ? author.toString() : "NOT_SET");
		r.append("\n");

		r.append("committer ");
		r.append(committer != null ? committer.toString() : "NOT_SET");
		r.append("\n");

		r.append("gpgSignature ");
		r.append(gpgSignature != null ? gpgSignature.toString() : "NOT_SET");
		r.append("\n");

		if (encoding != null && encoding != UTF_8) {
			r.append("encoding ");
			r.append(encoding.name());
			r.append("\n");
		}

		r.append("\n");
		r.append(message != null ? message : "");
		r.append("}");
		return r.toString();
	}
}
