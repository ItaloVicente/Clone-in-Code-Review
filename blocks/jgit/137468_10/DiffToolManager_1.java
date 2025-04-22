
package org.eclipse.jgit.diffmergetool;

public enum BooleanOption {
	notDefinedFalse()

	notDefinedTrue()

	False()

	True();

	BooleanOption() {
	}

	public boolean toBoolean() {
		switch (this) {
		case True:
		case notDefinedTrue:
			return true;
		default:
			return false;
		}
	}

	public boolean isDefined() {
		switch (this) {
		case True:
		case False:
			return true;
		default:
			return false;
		}
	}

	static public BooleanOption defined(boolean value) {
		return value ? BooleanOption.True : BooleanOption.False;
	}

	static public BooleanOption notDefined(boolean value) {
		return value ? BooleanOption.notDefinedTrue
				: BooleanOption.notDefinedFalse;
	}

}
