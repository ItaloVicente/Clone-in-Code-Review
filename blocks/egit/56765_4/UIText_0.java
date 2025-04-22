package org.eclipse.egit.ui.internal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.core.runtime.Assert;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.util.GitDateFormatter;

public class PreferenceBasedDateFormatter extends GitDateFormatter {

	private final SimpleDateFormat customFormat;

	public static @NonNull PreferenceBasedDateFormatter create() {
		String choice = Activator.getDefault().getPreferenceStore()
				.getString(UIPreferences.DATE_FORMAT_CHOICE);
		GitDateFormatter.Format format = null;
		try {
			format = GitDateFormatter.Format.valueOf(choice);
		} catch (IllegalArgumentException | NullPointerException e) {
		}
		return new PreferenceBasedDateFormatter(format);
	}

	private PreferenceBasedDateFormatter(GitDateFormatter.Format gitFormat) {
		super(gitFormat != null ? gitFormat : GitDateFormatter.Format.DEFAULT);
		SimpleDateFormat format = null;
		if (gitFormat == null) {
			String pattern = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.DATE_FORMAT);
			try {
				format = new SimpleDateFormat(pattern);
			} catch (IllegalArgumentException | NullPointerException e1) {
				Activator.logError("org.eclipse.egit.ui preference " //$NON-NLS-1$
						+ UIPreferences.DATE_FORMAT + " is invalid; ignoring", //$NON-NLS-1$
						e1);
				format = new SimpleDateFormat(
						Activator.getDefault().getPreferenceStore()
								.getDefaultString(UIPreferences.DATE_FORMAT));
			}
		}
		this.customFormat = format;
	}

	public String formatDate(Date date) {
		if (date == null) {
			return ""; //$NON-NLS-1$
		}
		TimeZone timeZone = TimeZone.getDefault();
		Assert.isNotNull(timeZone);
		return formatDate(date, timeZone);
	}

	public String formatDate(@NonNull Date date, @NonNull TimeZone timeZone) {
		return formatDate(new PersonIdent("", "", date, timeZone)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public String formatDate(PersonIdent ident) {
		if (ident == null) {
			return ""; //$NON-NLS-1$
		}
		if (customFormat == null) {
			return super.formatDate(ident);
		}
		return customFormat.format(ident.getWhen());
	}

}
