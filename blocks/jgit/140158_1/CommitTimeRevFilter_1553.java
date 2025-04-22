
package org.eclipse.jgit.revwalk.filter;

import java.util.regex.Pattern;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.RawCharSequence;
import org.eclipse.jgit.util.RawParseUtils;

public class AuthorRevFilter {
	public static RevFilter create(String pattern) {
		if (pattern.length() == 0)
			throw new IllegalArgumentException(JGitText.get().cannotMatchOnEmptyString);
		if (SubStringRevFilter.safe(pattern))
			return new SubStringSearch(pattern);
		return new PatternSearch(pattern);
	}

	private AuthorRevFilter() {
	}

	static RawCharSequence textFor(RevCommit cmit) {
		final byte[] raw = cmit.getRawBuffer();
		final int b = RawParseUtils.author(raw
		if (b < 0)
			return RawCharSequence.EMPTY;
		final int e = RawParseUtils.nextLF(raw
		return new RawCharSequence(raw
	}

	private static class PatternSearch extends PatternMatchRevFilter {
		PatternSearch(String patternText) {
			super(patternText
		}

		@Override
		protected CharSequence text(RevCommit cmit) {
			return textFor(cmit);
		}

		@Override
		public RevFilter clone() {
			return new PatternSearch(pattern());
		}
	}

	private static class SubStringSearch extends SubStringRevFilter {
		SubStringSearch(String patternText) {
			super(patternText);
		}

		@Override
		protected RawCharSequence text(RevCommit cmit) {
			return textFor(cmit);
		}
	}
}
