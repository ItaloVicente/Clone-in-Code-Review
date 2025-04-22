package org.eclipse.jgit.ignore.internal;

public abstract class AbstractMatcher implements IMatcher {

	final boolean dirOnly;
	final String pattern;

	AbstractMatcher(String pattern
		this.pattern = pattern;
		this.dirOnly = dirOnly;
	}

	@Override
	public String toString() {
		return pattern;
	}

	@Override
	public int hashCode() {
		return pattern.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AbstractMatcher)) {
			return false;
		}
		return pattern.equals(((AbstractMatcher) obj).pattern);
	}
}
