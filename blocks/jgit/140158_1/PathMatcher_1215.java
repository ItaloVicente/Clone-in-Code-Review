package org.eclipse.jgit.ignore.internal;

import static org.eclipse.jgit.ignore.internal.Strings.getPathSeparator;

public class NameMatcher extends AbstractMatcher {

	final boolean beginning;

	final char slash;

	final String subPattern;

	NameMatcher(String pattern
			boolean deleteBackslash) {
		super(pattern
		slash = getPathSeparator(pathSeparator);
		if (deleteBackslash) {
			pattern = Strings.deleteBackslash(pattern);
		}
		beginning = pattern.length() == 0 ? false : pattern.charAt(0) == slash;
		if (!beginning) {
			this.subPattern = pattern;
		} else {
			this.subPattern = pattern.substring(1);
		}
	}

	@Override
	public boolean matches(String path
			boolean pathMatch) {
		int start = 0;
		int stop = path.length();
		if (stop > 0 && path.charAt(0) == slash) {
			start++;
		}
		if (pathMatch) {
			int lastSlash = path.lastIndexOf(slash
			if (lastSlash == stop - 1) {
				lastSlash = path.lastIndexOf(slash
				stop--;
			}
			boolean match;
			if (lastSlash < start) {
				match = matches(path
			} else {
				match = !beginning
						&& matches(path
			}
			if (match && dirOnly) {
				match = assumeDirectory;
			}
			return match;
		}
		while (start < stop) {
			int end = path.indexOf(slash
			if (end < 0) {
				end = stop;
			}
			if (end > start && matches(path
				return !dirOnly || assumeDirectory || end < stop;
			}
			if (beginning) {
				break;
			}
			start = end + 1;
		}
		return false;
	}

	@Override
	public boolean matches(String segment
		String s = subPattern;
		int length = s.length();
		if (length != (endExcl - startIncl)) {
			return false;
		}
		for (int i = 0; i < length; i++) {
			char c1 = s.charAt(i);
			char c2 = segment.charAt(i + startIncl);
			if (c1 != c2) {
				return false;
			}
		}
		return true;
	}

}
