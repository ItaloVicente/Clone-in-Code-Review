
package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.RawCharSequence;
import org.eclipse.jgit.util.RawSubStringPattern;

public abstract class SubStringRevFilter extends RevFilter {
	public static boolean safe(String pattern) {
		for (int i = 0; i < pattern.length(); i++) {
			final char c = pattern.charAt(i);
			switch (c) {
			case '.':
			case '?':
			case '*':
			case '+':
			case '{':
			case '}':
			case '(':
			case ')':
			case '[':
			case ']':
			case '\\':
				return false;
			}
		}
		return true;
	}

	private final RawSubStringPattern pattern;

	protected SubStringRevFilter(String patternText) {
		pattern = new RawSubStringPattern(patternText);
	}

	@Override
	public boolean include(RevWalk walker
			throws MissingObjectException
			IOException {
		return pattern.match(text(cmit)) >= 0;
	}

	@Override
	public boolean requiresCommitBody() {
		return true;
	}

	protected abstract RawCharSequence text(RevCommit cmit);

	@Override
	public RevFilter clone() {
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return super.toString() + "(\"" + pattern.pattern() + "\")";
	}
}
