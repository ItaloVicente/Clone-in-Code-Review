
package org.eclipse.jgit.fnmatch;

final class RestrictedWildCardHead extends AbstractHead {
	private final char excludedCharacter;

	RestrictedWildCardHead(char excludedCharacter
		super(star);
		this.excludedCharacter = excludedCharacter;
	}

	@Override
	protected final boolean matches(char c) {
		return c != excludedCharacter;
	}

	@Override
	public String toString() {
	}
}
