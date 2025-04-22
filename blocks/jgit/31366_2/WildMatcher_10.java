package org.eclipse.jgit.ignore.internal;

import static org.eclipse.jgit.ignore.internal.Strings.convertGlob;

import java.util.regex.Pattern;

import org.eclipse.jgit.errors.InvalidPatternException;

public class WildCardMatcher extends NameMatcher {

	final Pattern p;

	WildCardMatcher(String pattern
		super(pattern
		p = convertGlob(subPattern);
	}

	@Override
	public boolean matches(String segment
			boolean assumeDirectory) {
		return p.matcher(new SubString(segment
	}

	final static class SubString implements CharSequence {
		private final String parent;
		private final int startIncl;
		private final int length;

		public SubString(String parent
			this.parent = parent;
			this.startIncl = startIncl;
			this.length = endExcl - startIncl;
			if (startIncl < 0 || endExcl < 0 || length < 0 || startIncl + length > parent.length()) {
				throw new IndexOutOfBoundsException(
			}
		}

		public final int length() {
			return length;
		}

		public final char charAt(int index) {
			return parent.charAt(startIncl + index);
		}

		public final CharSequence subSequence(int start
			return new SubString(parent
		}

		@Override
		public final String toString() {
			return parent.substring(startIncl
		}
	}
}
