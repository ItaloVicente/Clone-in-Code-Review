
package org.eclipse.jgit.lib;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.SystemReader;
import org.eclipse.jgit.util.time.ProposedTimestamp;

public class PersonIdent implements Serializable {
	private static final long serialVersionUID = 1L;

	public static TimeZone getTimeZone(int tzOffset) {
		StringBuilder tzId = new StringBuilder(8);
		appendTimezone(tzId
		return TimeZone.getTimeZone(tzId.toString());
	}

	public static void appendTimezone(StringBuilder r
		final char sign;
		final int offsetHours;
		final int offsetMins;

		if (offset < 0) {
			sign = '-';
			offset = -offset;
		} else {
			sign = '+';
		}

		offsetHours = offset / 60;
		offsetMins = offset % 60;

		r.append(sign);
		if (offsetHours < 10) {
			r.append('0');
		}
		r.append(offsetHours);
		if (offsetMins < 10) {
			r.append('0');
		}
		r.append(offsetMins);
	}

	public static void appendSanitized(StringBuilder r
		int i = 0;
		while (i < str.length() && str.charAt(i) <= ' ') {
			i++;
		}
		int end = str.length();
		while (end > i && str.charAt(end - 1) <= ' ') {
			end--;
		}

		for (; i < end; i++) {
			char c = str.charAt(i);
			switch (c) {
				case '\n':
				case '<':
				case '>':
					continue;
				default:
					r.append(c);
					break;
			}
		}
	}

	private final String name;

	private final String emailAddress;

	private final long when;

	private final int tzOffset;

	public PersonIdent(Repository repo) {
		this(repo.getConfig().get(UserConfig.KEY));
	}

	public PersonIdent(PersonIdent pi) {
		this(pi.getName()
	}

	public PersonIdent(String aName
		this(aName
	}

	public PersonIdent(String aName
			ProposedTimestamp when) {
		this(aName
	}

	public PersonIdent(PersonIdent pi
		this(pi.getName()
	}

	public PersonIdent(PersonIdent pi
		this(pi.getName()
	}

	public PersonIdent(final String aName
			final Date aWhen
		this(aName
				.getTime()) / (60 * 1000));
	}

	public PersonIdent(PersonIdent pi
		this(pi.getName()
	}

	private PersonIdent(final String aName
			long when) {
		this(aName
				.getTimezone(when));
	}

	private PersonIdent(UserConfig config) {
		this(config.getCommitterName()
	}

	public PersonIdent(final String aName
			final long aWhen
		if (aName == null)
			throw new IllegalArgumentException(
					JGitText.get().personIdentNameNonNull);
		if (aEmailAddress == null)
			throw new IllegalArgumentException(
					JGitText.get().personIdentEmailNonNull);
		name = aName;
		emailAddress = aEmailAddress;
		when = aWhen;
		tzOffset = aTZ;
	}

	public String getName() {
		return name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public Date getWhen() {
		return new Date(when);
	}

	public TimeZone getTimeZone() {
		return getTimeZone(tzOffset);
	}

	public int getTimeZoneOffset() {
		return tzOffset;
	}

	@Override
	public int hashCode() {
		int hc = getEmailAddress().hashCode();
		hc *= 31;
		hc += (int) (when / 1000L);
		return hc;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PersonIdent) {
			final PersonIdent p = (PersonIdent) o;
			return getName().equals(p.getName())
					&& getEmailAddress().equals(p.getEmailAddress())
					&& when / 1000L == p.when / 1000L;
		}
		return false;
	}

	public String toExternalString() {
		final StringBuilder r = new StringBuilder();
		appendSanitized(r
		appendSanitized(r
		r.append(when / 1000);
		r.append(' ');
		appendTimezone(r
		return r.toString();
	}

	@Override
	@SuppressWarnings("nls")
	public String toString() {
		final StringBuilder r = new StringBuilder();
		final SimpleDateFormat dtfmt;
		dtfmt = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z"
		dtfmt.setTimeZone(getTimeZone());

		r.append("PersonIdent[");
		r.append(getName());
		r.append("
		r.append(getEmailAddress());
		r.append("
		r.append(dtfmt.format(Long.valueOf(when)));
		r.append("]");

		return r.toString();
	}
}

