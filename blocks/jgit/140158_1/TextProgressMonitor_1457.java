
package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.eclipse.jgit.revwalk.RevObject;

public class TagBuilder {
	private ObjectId object;

	private int type = Constants.OBJ_BAD;

	private String tag;

	private PersonIdent tagger;

	private String message;

	public int getObjectType() {
		return type;
	}

	public ObjectId getObjectId() {
		return object;
	}

	public void setObjectId(AnyObjectId obj
		object = obj.copy();
		type = objType;
	}

	public void setObjectId(RevObject obj) {
		setObjectId(obj
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String shortName) {
		this.tag = shortName;
	}

	public PersonIdent getTagger() {
		return tagger;
	}

	public void setTagger(PersonIdent taggerIdent) {
		tagger = taggerIdent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String newMessage) {
		message = newMessage;
	}

	public byte[] build() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try (OutputStreamWriter w = new OutputStreamWriter(os
				UTF_8)) {
			getObjectId().copyTo(w);
			w.write('\n');

			w.write(Constants.typeString(getObjectType()));

			w.write(getTag());

			if (getTagger() != null) {
				w.write(getTagger().toExternalString());
				w.write('\n');
			}

			w.write('\n');
			if (getMessage() != null)
				w.write(getMessage());
		} catch (IOException err) {
			throw new RuntimeException(err);
		}
		return os.toByteArray();
	}

	public byte[] toByteArray() {
		return build();
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("Tag");
		r.append("={\n");

		r.append("object ");
		r.append(object != null ? object.name() : "NOT_SET");
		r.append("\n");

		r.append("type ");
		r.append(object != null ? Constants.typeString(type) : "NOT_SET");
		r.append("\n");

		r.append("tag ");
		r.append(tag != null ? tag : "NOT_SET");
		r.append("\n");

		if (tagger != null) {
			r.append("tagger ");
			r.append(tagger);
			r.append("\n");
		}

		r.append("\n");
		r.append(message != null ? message : "");
		r.append("}");
		return r.toString();
	}
}
