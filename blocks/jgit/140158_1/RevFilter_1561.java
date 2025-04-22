
package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public abstract class PatternMatchRevFilter extends RevFilter {
	protected static final String forceToRaw(String patternText) {
		final byte[] b = Constants.encode(patternText);
		final StringBuilder needle = new StringBuilder(b.length);
		for (int i = 0; i < b.length; i++)
			needle.append((char) (b[i] & 0xff));
		return needle.toString();
	}

	private final String patternText;

	private final Matcher compiledPattern;

	protected PatternMatchRevFilter(String pattern
			final boolean rawEncoding
		if (pattern.length() == 0)
			throw new IllegalArgumentException(JGitText.get().cannotMatchOnEmptyString);
		patternText = pattern;

		if (innerString) {
		}
		final String p = rawEncoding ? forceToRaw(pattern) : pattern;
		compiledPattern = Pattern.compile(p
	}

	public String pattern() {
		return patternText;
	}

	@Override
	public boolean include(RevWalk walker
			throws MissingObjectException
			IOException {
		return compiledPattern.reset(text(cmit)).matches();
	}

	@Override
	public boolean requiresCommitBody() {
		return true;
	}

	protected abstract CharSequence text(RevCommit cmit);

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return super.toString() + "(\"" + patternText + "\")";
	}
}
