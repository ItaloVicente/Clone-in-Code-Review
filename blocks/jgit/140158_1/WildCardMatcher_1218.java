package org.eclipse.jgit.ignore.internal;

public class TrailingAsteriskMatcher extends NameMatcher {

	TrailingAsteriskMatcher(String pattern
		super(pattern

		if (subPattern.charAt(subPattern.length() - 1) != '*')
			throw new IllegalArgumentException(
	}

	@Override
	public boolean matches(String segment
		String s = subPattern;
		int subLenth = s.length() - 1;
		if (subLenth == 0)
			return true;

		if (subLenth > (endExcl - startIncl))
			return false;

		for (int i = 0; i < subLenth; i++) {
			char c1 = s.charAt(i);
			char c2 = segment.charAt(i + startIncl);
			if (c1 != c2)
				return false;
		}
		return true;
	}

}
