
package org.eclipse.jgit.diffmergetool;

public enum BooleanOption {
	NOT_DEFINED_FALSE()

	NOT_DEFINED_TRUE()

	FALSE()

	TRUE();

	BooleanOption() {
	}

	public boolean toBoolean() {
		switch (this) {
		case TRUE:
		case NOT_DEFINED_TRUE:
			return true;
		default:
			return false;
		}
	}

	public boolean isDefined() {
		switch (this) {
		case TRUE:
		case FALSE:
			return true;
		default:
			return false;
		}
	}

	public static BooleanOption defined(boolean value) {
		return value ? BooleanOption.TRUE : BooleanOption.FALSE;
	}

	public static BooleanOption notDefined(boolean value) {
		return value ? BooleanOption.NOT_DEFINED_TRUE
				: BooleanOption.NOT_DEFINED_FALSE;
	}

}
