
package org.eclipse.jgit.storage.file;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.util.RawParseUtils;

public class ReflogEntry {
	private ObjectId oldId;

	private ObjectId newId;

	private PersonIdent who;

	private String comment;

	ReflogEntry(byte[] raw
		oldId = ObjectId.fromString(raw
		pos += Constants.OBJECT_ID_STRING_LENGTH;
		if (raw[pos++] != ' ')
			throw new IllegalArgumentException(
					JGitText.get().rawLogMessageDoesNotParseAsLogEntry);
		newId = ObjectId.fromString(raw
		pos += Constants.OBJECT_ID_STRING_LENGTH;
		if (raw[pos++] != ' ') {
			throw new IllegalArgumentException(
					JGitText.get().rawLogMessageDoesNotParseAsLogEntry);
		}
		who = RawParseUtils.parsePersonIdentOnly(raw
		int p0 = RawParseUtils.next(raw
		if (p0 >= raw.length)
		else {
			int p1 = RawParseUtils.nextLF(raw
			comment = p1 > p0 ? RawParseUtils.decode(raw
		}
	}

	public ObjectId getOldId() {
		return oldId;
	}

	public ObjectId getNewId() {
		return newId;
	}

	public PersonIdent getWho() {
		return who;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public String toString() {
		return "Entry[" + oldId.name() + "
				+ getComment() + "]";
	}
}
