package org.eclipse.jgit.ignore.internal;

import static org.eclipse.jgit.ignore.internal.Strings.convertGlob;

import java.util.regex.Pattern;

import org.eclipse.jgit.errors.InvalidPatternException;

public class WildCardMatcher extends NameMatcher {

	final Pattern p;

	WildCardMatcher(String pattern
			throws InvalidPatternException {
		super(pattern
		p = convertGlob(subPattern);
	}

	@Override
	public boolean matches(String segment
			boolean assumeDirectory) {
		return p.matcher(segment.substring(startIncl
	}
}
