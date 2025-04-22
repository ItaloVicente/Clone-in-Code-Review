
package org.eclipse.jgit.internal.storage.file;

import java.io.Serializable;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.CheckoutEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.util.RawParseUtils;

public class ReflogEntryImpl implements Serializable
	private static final long serialVersionUID = 1L;

	private ObjectId oldId;

	private ObjectId newId;

	private PersonIdent who;

	private String comment;

	ReflogEntryImpl(byte[] raw
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

	@Override
	public ObjectId getOldId() {
		return oldId;
	}

	@Override
	public ObjectId getNewId() {
		return newId;
	}

	@Override
	public PersonIdent getWho() {
		return who;
	}

	@Override
	public String getComment() {
		return comment;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "Entry[" + oldId.name() + "
				+ "
	}

	@Override
	public CheckoutEntry parseCheckout() {
		if (getComment().startsWith(CheckoutEntryImpl.CHECKOUT_MOVING_FROM))
			return new CheckoutEntryImpl(this);
		else
			return null;
	}
}
