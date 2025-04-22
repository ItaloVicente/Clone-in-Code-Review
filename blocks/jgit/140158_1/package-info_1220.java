package org.eclipse.jgit.ignore.internal;

public final class WildMatcher extends AbstractMatcher {



	WildMatcher(boolean dirOnly) {
		super(WILDMATCH
	}

	@Override
	public final boolean matches(String path
			boolean pathMatch) {
		return !dirOnly || assumeDirectory
				|| !pathMatch && isSubdirectory(path);
	}

	@Override
	public final boolean matches(String segment
		return true;
	}

	private static boolean isSubdirectory(String path) {
		final int slashIndex = path.indexOf('/');
		return slashIndex >= 0 && slashIndex < path.length() - 1;
	}
}
