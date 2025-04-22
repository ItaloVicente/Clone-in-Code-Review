
package org.eclipse.jgit.fnmatch;

final class CharacterHead extends AbstractHead {
	private final char expectedCharacter;

	protected CharacterHead(char expectedCharacter) {
		super(false);
		this.expectedCharacter = expectedCharacter;
	}

	@Override
	protected final boolean matches(char c) {
		return c == expectedCharacter;
	}

	@Override
	public String toString() {
		return String.valueOf(expectedCharacter);
	}

}
