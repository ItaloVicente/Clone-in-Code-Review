
package org.eclipse.jgit.patch;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Locale;

import org.eclipse.jgit.util.RawParseUtils;

public class FormatError {
	public static enum Severity {
		WARNING

		ERROR;
	}

	private final byte[] buf;

	private final int offset;

	private final Severity severity;

	private final String message;

	FormatError(final byte[] buffer
			final String msg) {
		buf = buffer;
		offset = ptr;
		severity = sev;
		message = msg;
	}

	public Severity getSeverity() {
		return severity;
	}

	public String getMessage() {
		return message;
	}

	public byte[] getBuffer() {
		return buf;
	}

	public int getOffset() {
		return offset;
	}

	public String getLineText() {
		final int eol = RawParseUtils.nextLF(buf
		return RawParseUtils.decode(UTF_8
	}

	@Override
	public String toString() {
		final StringBuilder r = new StringBuilder();
		r.append(getSeverity().name().toLowerCase(Locale.ROOT));
		r.append(getOffset());
		r.append(getMessage());
		r.append(getLineText());
		return r.toString();
	}
}
