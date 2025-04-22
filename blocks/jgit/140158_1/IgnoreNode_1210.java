package org.eclipse.jgit.ignore;

import static org.eclipse.jgit.ignore.internal.IMatcher.NO_MATCH;
import static org.eclipse.jgit.ignore.internal.Strings.isDirectoryPattern;
import static org.eclipse.jgit.ignore.internal.Strings.stripTrailing;
import static org.eclipse.jgit.ignore.internal.Strings.stripTrailingWhitespace;

import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.ignore.internal.IMatcher;
import org.eclipse.jgit.ignore.internal.PathMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastIgnoreRule {
	private final static Logger LOG = LoggerFactory
			.getLogger(FastIgnoreRule.class);

	public static final char PATH_SEPARATOR = '/';

	private final IMatcher matcher;

	private final boolean inverse;

	private final boolean dirOnly;

	public FastIgnoreRule(String pattern) {
		if (pattern == null)
		if (pattern.length() == 0) {
			dirOnly = false;
			inverse = false;
			this.matcher = NO_MATCH;
			return;
		}
		inverse = pattern.charAt(0) == '!';
		if (inverse) {
			pattern = pattern.substring(1);
			if (pattern.length() == 0) {
				dirOnly = false;
				this.matcher = NO_MATCH;
				return;
			}
		}
		if (pattern.charAt(0) == '#') {
			this.matcher = NO_MATCH;
			dirOnly = false;
			return;
		}
		if (pattern.charAt(0) == '\\' && pattern.length() > 1) {
			char next = pattern.charAt(1);
			if (next == '!' || next == '#') {
				pattern = pattern.substring(1);
			}
		}
		dirOnly = isDirectoryPattern(pattern);
		if (dirOnly) {
			pattern = stripTrailingWhitespace(pattern);
			pattern = stripTrailing(pattern
			if (pattern.length() == 0) {
				this.matcher = NO_MATCH;
				return;
			}
		}
		IMatcher m;
		try {
			m = PathMatcher.createPathMatcher(pattern
					Character.valueOf(PATH_SEPARATOR)
		} catch (InvalidPatternException e) {
			m = NO_MATCH;
			LOG.error(e.getMessage()
		}
		this.matcher = m;
	}

	public boolean isMatch(String path
		return isMatch(path
	}

	public boolean isMatch(String path
		if (path == null)
			return false;
		if (path.length() == 0)
			return false;
		boolean match = matcher.matches(path
		return match;
	}

	public boolean getNameOnly() {
		return !(matcher instanceof PathMatcher);
	}

	public boolean dirOnly() {
		return dirOnly;
	}

	public boolean getNegation() {
		return inverse;
	}

	public boolean getResult() {
		return !inverse;
	}

	public boolean isEmpty() {
		return matcher == NO_MATCH;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (inverse)
			sb.append('!');
		sb.append(matcher);
		if (dirOnly)
			sb.append(PATH_SEPARATOR);
		return sb.toString();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (inverse ? 1231 : 1237);
		result = prime * result + (dirOnly ? 1231 : 1237);
		result = prime * result + ((matcher == null) ? 0 : matcher.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FastIgnoreRule))
			return false;

		FastIgnoreRule other = (FastIgnoreRule) obj;
		if (inverse != other.inverse)
			return false;
		if (dirOnly != other.dirOnly)
			return false;
		return matcher.equals(other.matcher);
	}
}
