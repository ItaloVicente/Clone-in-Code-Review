
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.eclipse.jgit.util.RawParseUtils.lastIndexOfTrim;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.util.MutableInteger;
import org.eclipse.jgit.util.RawParseUtils;

public class PushCertificateIdent {
	public static PushCertificateIdent parse(String str) {
		MutableInteger p = new MutableInteger();
		byte[] raw = str.getBytes(UTF_8);
		if (raw[raw.length - 1] == '\n') {
			str = str.substring(0
		}
		int tzBegin = raw.length - 1;
		if (raw[tzBegin] == '\n') {
			tzBegin--;
		}
		tzBegin = lastIndexOfTrim(raw
		if (tzBegin < 0 || raw[tzBegin] != ' ') {
			return new PushCertificateIdent(str
		}
		int whenBegin = tzBegin++;
		int tz = RawParseUtils.parseTimeZoneOffset(raw
		boolean hasTz = p.value != tzBegin;

		whenBegin = lastIndexOfTrim(raw
		if (whenBegin < 0 || raw[whenBegin] != ' ') {
			return new PushCertificateIdent(str
		}
		int idEnd = whenBegin++;
		long when = RawParseUtils.parseLongBase10(raw
		boolean hasWhen = p.value != whenBegin;

		if (hasTz && hasWhen) {
			idEnd = whenBegin - 1;
		} else {
			tz = 0;
			when = 0;
			if (hasTz && !hasWhen) {
				idEnd = tzBegin - 1;
			} else {
				idEnd = raw.length;
			}
		}
		String id = new String(raw

		return new PushCertificateIdent(str
	}

	private final String raw;
	private final String userId;
	private final long when;
	private final int tzOffset;

	public PushCertificateIdent(String userId
		this.userId = userId;
		this.when = when;
		this.tzOffset = tzOffset;
		StringBuilder sb = new StringBuilder(userId).append(' ').append(when / 1000)
				.append(' ');
		PersonIdent.appendTimezone(sb
		raw = sb.toString();
	}

	private PushCertificateIdent(String raw
			int tzOffset) {
		this.raw = raw;
		this.userId = userId;
		this.when = when;
		this.tzOffset = tzOffset;
	}

	public String getRaw() {
		return raw;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		int nameEnd = userId.indexOf('<');
		if (nameEnd < 0 || userId.indexOf('>'
			nameEnd = userId.length();
		}
		nameEnd--;
		while (nameEnd >= 0 && userId.charAt(nameEnd) == ' ') {
			nameEnd--;
		}
		int nameBegin = 0;
		while (nameBegin < nameEnd && userId.charAt(nameBegin) == ' ') {
			nameBegin++;
		}
		return userId.substring(nameBegin
	}

	public String getEmailAddress() {
		int emailBegin = userId.indexOf('<');
		if (emailBegin < 0) {
			return null;
		}
		int emailEnd = userId.indexOf('>'
		if (emailEnd < 0) {
			return null;
		}
		return userId.substring(emailBegin + 1
	}

	public Date getWhen() {
		return new Date(when);
	}

	public TimeZone getTimeZone() {
		return PersonIdent.getTimeZone(tzOffset);
	}

	public int getTimeZoneOffset() {
		return tzOffset;
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof PushCertificateIdent)
			&& raw.equals(((PushCertificateIdent) o).raw);
	}

	@Override
	public int hashCode() {
		return raw.hashCode();
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		SimpleDateFormat fmt;
		fmt = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z"
		fmt.setTimeZone(getTimeZone());
		return getClass().getSimpleName()
			+ "[raw=\"" + raw + "\"
	}
}
