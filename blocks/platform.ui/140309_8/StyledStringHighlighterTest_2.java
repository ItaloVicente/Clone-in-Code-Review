package org.eclipse.ui.dialogs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;

public class StyledStringHighlighter implements IStyledStringHighlighter {

	private static final String QMARK = "?"; //$NON-NLS-1$
	private static final String ASTERISK = "*"; //$NON-NLS-1$
	private static final char TERMINATOR = '<';

	@Override
	public StyledString highlight(String text, String filterPattern, Styler boldStyler) {
		if (text == null || text.isEmpty()) {
			return new StyledString();
		}
		StyledString styledString = new StyledString(text);

		if (filterPattern == null || filterPattern.isEmpty() //
				|| ASTERISK.equals(filterPattern)
				|| QMARK.equals(filterPattern)) {
			return styledString;
		}

		filterPattern = removeEndTerminator(filterPattern);
		filterPattern = replaceConsecutiveAsterisks(filterPattern);
		filterPattern = escapeSpecialCharacters(filterPattern);

		try {
			highlight(text, filterPattern, styledString, boldStyler);
		} catch (Exception e) {
		}

		return styledString;
	}

	private String removeEndTerminator(String filterPattern) {
		int numEndTerminators = 0;
		for (int i = filterPattern.length() - 1; i >= 0; i--) {
			if (filterPattern.charAt(i) == TERMINATOR) {
				numEndTerminators++;
			} else {
				break;
			}
		}
		filterPattern = filterPattern.substring(0, filterPattern.length() - numEndTerminators);
		return filterPattern;
	}

	private String replaceConsecutiveAsterisks(String filterPattern) {
		return filterPattern.replaceAll("(\\*)\\1+", ASTERISK); //$NON-NLS-1$
	}

	private String escapeSpecialCharacters(String filterPattern) {
		boolean startsWithSpecialChar = filterPattern.startsWith(ASTERISK) || filterPattern.startsWith(QMARK);
		boolean endsWithSpecialChar = filterPattern.endsWith(ASTERISK) || filterPattern.endsWith(QMARK);
		filterPattern = filterPattern.replace(ASTERISK, "\\E).*(\\Q"); //$NON-NLS-1$
		filterPattern = filterPattern.replace(QMARK, "\\E).(\\Q"); //$NON-NLS-1$

		if (!startsWithSpecialChar) {
			filterPattern = "(\\Q" + filterPattern; //$NON-NLS-1$
		}
		if (!endsWithSpecialChar) {
			filterPattern = filterPattern + "\\E)"; //$NON-NLS-1$
		}

		int start = (filterPattern.startsWith("\\E)") ? 3 : 0); //$NON-NLS-1$
		int end = filterPattern.length() - (filterPattern.endsWith("(\\Q") ? 3 : 0); //$NON-NLS-1$

		return filterPattern.substring(start, end);
	}

	private void highlight(String text, String filterPattern, StyledString styledString, Styler boldStyler) {
		Pattern pattern = Pattern.compile(filterPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			int groupCount = matcher.groupCount();
			if (groupCount == 0) {
				styledString.setStyle(matcher.start(), matcher.end() - matcher.start(), boldStyler);
			} else {
				for (int i = 1; i <= groupCount; i++) {
					styledString.setStyle(matcher.start(i), matcher.end(i) - matcher.start(i), boldStyler);
				}
			}
		}
	}

}
