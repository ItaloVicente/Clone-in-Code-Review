package org.eclipse.jgit.fnmatch2;

public final class WildMatcher extends AbstractMatcher {

	static final String WILDMATCH = "**";
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
