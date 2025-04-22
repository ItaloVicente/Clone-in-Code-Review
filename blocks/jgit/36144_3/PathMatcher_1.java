package org.eclipse.jgit.ignore.internal;

public class LeadingAsteriskMatcher extends NameMatcher {

	LeadingAsteriskMatcher(String pattern
		super(pattern

		if (subPattern.charAt(0) != '*')
			throw new IllegalArgumentException(
	}

	public boolean matches(String segment
			boolean assumeDirectory) {
		String s = subPattern;

		int subLength = s.length() - 1;
		if (subLength == 0)
			return true;

		if (subLength > (endExcl - startIncl))
			return false;

		for (int i = subLength
			char c1 = s.charAt(i);
			char c2 = segment.charAt(j);
			if (c1 != c2)
				return false;
		}
		return true;
	}

}
