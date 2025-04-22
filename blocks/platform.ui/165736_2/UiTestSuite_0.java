package org.eclipse.ui.internal.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import org.eclipse.core.text.StringMatcher;

public final class TextMatcher {

	private static final Pattern NON_WORD = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS); //$NON-NLS-1$

	private final StringMatcher full;

	private final List<StringMatcher> parts;

	public TextMatcher(String pattern, boolean ignoreCase, boolean ignoreWildCards) {
		full = new StringMatcher(pattern, ignoreCase, ignoreWildCards);
		parts = splitPattern(pattern, ignoreCase, ignoreWildCards);
	}

	private List<StringMatcher> splitPattern(String pattern,
			boolean ignoreCase, boolean ignoreWildCards) {
		String pat = pattern.trim();
		if (pat.isEmpty()) {
			return Collections.emptyList();
		}
		String[] subPatterns = pattern.split("\\s+"); //$NON-NLS-1$
		if (subPatterns.length <= 1) {
			return Collections.emptyList();
		}
		List<StringMatcher> matchers = new ArrayList<>();
		for (String s : subPatterns) {
			if (s == null || s.isEmpty()) {
				continue;
			}
			StringMatcher m = new StringMatcher(s, ignoreCase, ignoreWildCards);
			m.usePrefixMatch();
			matchers.add(m);
		}
		return matchers;
	}

	public boolean match(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		return match(text, 0, text.length());
	}

	public boolean match(String text, int start, int end) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		if (start > end) {
			return false;
		}
		int tlen = text.length();
		start = Math.max(0, start);
		end = Math.min(end, tlen);
		if (full.match(text, start, end)) {
			return true;
		}
		String[] words = getWords(text.substring(start, end));
		if (match(full, words)) {
			return true;
		}
		if (parts.isEmpty()) {
			return false;
		}
		for (StringMatcher subMatcher : parts) {
			if (!subMatcher.match(text, start, end) && !match(subMatcher, words)) {
				return false;
			}
		}
		return true;
	}

	private boolean match(StringMatcher matcher, String[] words) {
		return Arrays.stream(words).filter(Objects::nonNull).anyMatch(matcher::match);
	}

	public static String[] getWords(String text) {
		return NON_WORD.split(text);
	}

	@Override
	public String toString() {
		return '[' + full.toString() + ',' + parts + ']';
	}
}
