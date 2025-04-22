package org.eclipse.jgit.ignore.internal;

import static org.eclipse.jgit.ignore.internal.Strings.getPathSeparator;

public class NameMatcher extends AbstractMatcher {

	final boolean beginning;

	final char slash;

	final String subPattern;

	NameMatcher(String pattern
		super(pattern
		slash = getPathSeparator(pathSeparator);
		beginning = pattern.length() == 0 ? false : pattern.charAt(0) == slash;
		if (!beginning)
			this.subPattern = pattern;
		else
			this.subPattern = pattern.substring(1);
	}

	public boolean matches(String path
		int end = 0;
		int firstChar = 0;
		do {
			firstChar = getFirstNotSlash(path
			end = getFirstSlash(path
			boolean match = matches(path
			if (match)
				return !dirOnly ? true : (end > 0 && end != path.length())
						|| assumeDirectory;
		} while (!beginning && end != path.length());
		return false;
	}

	public boolean matches(String segment
			boolean assumeDirectory) {
		String s = subPattern;
		if (s.length() != (endExcl - startIncl))
			return false;
		for (int i = 0; i < s.length(); i++) {
			char c1 = s.charAt(i);
			char c2 = segment.charAt(i + startIncl);
			if (c1 != c2)
				return false;
		}
		return true;
	}

	private int getFirstNotSlash(String s
		int slashIdx = s.indexOf(slash
		return slashIdx == start ? start + 1 : start;
	}

	private int getFirstSlash(String s
		int slashIdx = s.indexOf(slash
		return slashIdx == -1 ? s.length() : slashIdx;
	}

}
