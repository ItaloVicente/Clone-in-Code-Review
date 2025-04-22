package org.eclipse.jgit.ignore.internal;

public final class WildMatcher extends AbstractMatcher {

	static final WildMatcher INSTANCE = new WildMatcher();

	private WildMatcher() {
		super(WILDMATCH
	}

	public final boolean matches(String path
		return true;
	}

	public final boolean matches(String segment
			boolean assumeDirectory) {
		return true;
	}

}
