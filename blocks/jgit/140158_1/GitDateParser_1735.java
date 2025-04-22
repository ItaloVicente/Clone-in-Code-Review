
package org.eclipse.jgit.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.eclipse.jgit.lib.PersonIdent;

public class GitDateFormatter {

	private DateFormat dateTimeInstance;

	private DateFormat dateTimeInstance2;

	private final Format format;

	static public enum Format {

		DEFAULT

		RELATIVE

		LOCAL

		ISO

		RFC

		SHORT

		RAW

		LOCALE

		LOCALELOCAL
	}

	public GitDateFormatter(Format format) {
		this.format = format;
		switch (format) {
		default:
			break;
			dateTimeInstance = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss yyyy Z"
			break;
		case ISO:
			dateTimeInstance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z"
					Locale.US);
			break;
		case LOCAL:
			dateTimeInstance = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy"
					Locale.US);
			break;
		case RFC:
			dateTimeInstance = new SimpleDateFormat(
					"EEE
			break;
		case SHORT:
			dateTimeInstance = new SimpleDateFormat("yyyy-MM-dd"
			break;
		case LOCALE:
		case LOCALELOCAL:
			SystemReader systemReader = SystemReader.getInstance();
			dateTimeInstance = systemReader.getDateTimeInstance(
					DateFormat.DEFAULT
			break;
		}
	}

	@SuppressWarnings("boxing")
	public String formatDate(PersonIdent ident) {
		switch (format) {
		case RAW:
			int offset = ident.getTimeZoneOffset();
			int offset2;
			if (offset < 0)
				offset2 = -offset;
			else
				offset2 = offset;
			int hours = offset2 / 60;
			int minutes = offset2 % 60;
			return String.format("%d %s%02d%02d"
					ident.getWhen().getTime() / 1000
		case RELATIVE:
			return RelativeDateFormatter.format(ident.getWhen());
		case LOCALELOCAL:
		case LOCAL:
			dateTimeInstance.setTimeZone(SystemReader.getInstance()
					.getTimeZone());
			return dateTimeInstance.format(ident.getWhen());
		case LOCALE:
			TimeZone tz = ident.getTimeZone();
			if (tz == null)
				tz = SystemReader.getInstance().getTimeZone();
			dateTimeInstance.setTimeZone(tz);
			dateTimeInstance2.setTimeZone(tz);
					+ dateTimeInstance2.format(ident.getWhen());
		default:
			tz = ident.getTimeZone();
			if (tz == null)
				tz = SystemReader.getInstance().getTimeZone();
			dateTimeInstance.setTimeZone(ident.getTimeZone());
			return dateTimeInstance.format(ident.getWhen());
		}
	}
}
