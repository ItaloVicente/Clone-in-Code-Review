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

	private final GitDateFormatter gitFormat;

	private final SimpleDateFormat customFormat;

	public PreferenceBasedDateFormatter() {
		super(GitDateFormatter.Format.DEFAULT);
		String choice = Activator.getDefault().getPreferenceStore()
				.getString(UIPreferences.DATE_FORMAT_CHOICE);
		GitDateFormatter git = null;
		try {
			git = new GitDateFormatter(GitDateFormatter.Format.valueOf(choice));
		} catch (IllegalArgumentException | NullPointerException e) {
		}
		this.gitFormat = git;
		String pattern = Activator.getDefault().getPreferenceStore()
				.getString(UIPreferences.DATE_FORMAT);
		SimpleDateFormat format;
		try {
			format = new SimpleDateFormat(pattern);
		} catch (IllegalArgumentException | NullPointerException e1) {
			Activator.logError("org.eclipse.egit.ui preference " //$NON-NLS-1$
					+ UIPreferences.DATE_FORMAT + " is invalid; ignoring", e1); //$NON-NLS-1$
			format = new SimpleDateFormat(
					Activator.getDefault().getPreferenceStore()
							.getDefaultString(UIPreferences.DATE_FORMAT));
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
		if (gitFormat != null) {
			return gitFormat.formatDate(ident);
		}
		return customFormat.format(ident.getWhen());
	}

}
