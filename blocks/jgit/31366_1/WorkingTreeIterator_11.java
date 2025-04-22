package org.eclipse.jgit.ignore2;

import static org.eclipse.jgit.fnmatch2.Strings.stripTrailing;

import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.fnmatch2.*;
import org.eclipse.jgit.ignore.IgnoreRule;

public class FastIgnoreRule {

	private static final char PATH_SEPARATOR = '/';
	private static final NoResultMatcher NO_MATCH = new NoResultMatcher();

	private final IMatcher matcher;
	private final boolean inverse;
	private final boolean dirOnly;

	public FastIgnoreRule(String pattern) {
		if(pattern == null){
			throw new IllegalArgumentException("Pattern must be not null!");
		}
		pattern = pattern.trim();
		if (pattern.length() == 0) {
			dirOnly = false;
			inverse = false;
			this.matcher = NO_MATCH;
			return;
		}
		inverse = pattern.charAt(0) == '!';
		if(inverse){
			pattern = pattern.substring(1);
			if (pattern.length() == 0) {
				dirOnly = false;
				this.matcher = NO_MATCH;
				return;
			}
		}
		if(pattern.charAt(0) == '#'){
			this.matcher = NO_MATCH;
			dirOnly = false;
		} else {
			dirOnly = pattern.charAt(pattern.length() - 1) == PATH_SEPARATOR;
			if(dirOnly) {
				pattern = stripTrailing(pattern
				if (pattern.length() == 0) {
					this.matcher = NO_MATCH;
					return;
				}
			}
			IMatcher m;
			try {
				m = PathMatcher.createPathMatcher(pattern
			} catch (InvalidPatternException e) {
				m = NO_MATCH;
			}
			this.matcher = m;
		}
	}

	public boolean isMatch(String path
		if(path == null){
			return false;
		}
		path = path.trim();
		if (path.length() == 0) {
			return false;
		}
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (inverse) {
			sb.append('!');
		}
		sb.append(matcher);
		if (dirOnly) {
			sb.append(PATH_SEPARATOR);
		}
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
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof FastIgnoreRule)) {
			return false;
		}

		FastIgnoreRule other = (FastIgnoreRule) obj;
		if(inverse != other.inverse) {
			return false;
		}
		if(dirOnly != other.dirOnly) {
			return false;
		}
		return matcher.equals(other.matcher);
	}

	static final class NoResultMatcher implements IMatcher {

		public boolean matches(String path
			return false;
		}

		public boolean matches(String segment
				boolean assumeDirectory) {
			return false;
		}
	}
}
