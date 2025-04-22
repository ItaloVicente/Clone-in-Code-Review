
package org.eclipse.jgit.revwalk;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.util.MutableInteger;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.StringUtils;

public class RevTag extends RevObject {
	public static RevTag parse(byte[] raw) throws CorruptObjectException {
		return parse(new RevWalk((ObjectReader) null)
	}

	public static RevTag parse(RevWalk rw
			throws CorruptObjectException {
		try (ObjectInserter.Formatter fmt = new ObjectInserter.Formatter()) {
			RevTag r = rw.lookupTag(fmt.idFor(Constants.OBJ_TAG
			r.parseCanonical(rw
			r.buffer = raw;
			return r;
		}
	}

	private RevObject object;

	private byte[] buffer;

	private String tagName;

	protected RevTag(AnyObjectId id) {
		super(id);
	}

	@Override
	void parseHeaders(RevWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		parseCanonical(walk
	}

	@Override
	void parseBody(RevWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		if (buffer == null) {
			buffer = walk.getCachedBytes(this);
			if ((flags & PARSED) == 0)
				parseCanonical(walk
		}
	}

	void parseCanonical(RevWalk walk
			throws CorruptObjectException {
		final MutableInteger pos = new MutableInteger();
		final int oType;

		oType = Constants.decodeTypeString(this
		walk.idBuffer.fromString(rawTag
		object = walk.lookupAny(walk.idBuffer

		final int nameEnd = RawParseUtils.nextLF(rawTag
		tagName = RawParseUtils.decode(UTF_8

		if (walk.isRetainBody())
			buffer = rawTag;
		flags |= PARSED;
	}

	@Override
	public final int getType() {
		return Constants.OBJ_TAG;
	}

	public final PersonIdent getTaggerIdent() {
		final byte[] raw = buffer;
		final int nameB = RawParseUtils.tagger(raw
		if (nameB < 0)
			return null;
		return RawParseUtils.parsePersonIdent(raw
	}

	public final String getFullMessage() {
		byte[] raw = buffer;
		int msgB = RawParseUtils.tagMessage(raw
		if (msgB < 0) {
		}
		return RawParseUtils.decode(guessEncoding()
	}

	public final String getShortMessage() {
		byte[] raw = buffer;
		int msgB = RawParseUtils.tagMessage(raw
		if (msgB < 0) {
		}

		int msgE = RawParseUtils.endOfParagraph(raw
		String str = RawParseUtils.decode(guessEncoding()
		if (RevCommit.hasLF(raw
			str = StringUtils.replaceLineBreaksWithSpace(str);
		}
		return str;
	}

	private Charset guessEncoding() {
		try {
			return RawParseUtils.parseEncoding(buffer);
		} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
			return UTF_8;
		}
	}

	public final RevObject getObject() {
		return object;
	}

	public final String getTagName() {
		return tagName;
	}

	public final void disposeBody() {
		buffer = null;
	}
}
