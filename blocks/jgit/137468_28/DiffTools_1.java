
package org.eclipse.jgit.diffmergetool;

import java.util.Optional;

public enum BooleanOption {
	DEFAULT_FALSE()

	DEFAULT_TRUE()

	FALSE()

	TRUE();

	BooleanOption() {
	}

	public boolean toBoolean() {
		switch (this) {
		case TRUE:
		case DEFAULT_TRUE:
			return true;
		default:
			return false;
		}
	}

	public boolean isConfigured() {
		switch (this) {
		case TRUE:
		case FALSE:
			return true;
		default:
			return false;
		}
	}

	public boolean isDefault() {
		return !isConfigured();
	}

	public static BooleanOption toConfigured(boolean value) {
		return value ? BooleanOption.TRUE : BooleanOption.FALSE;
	}

	public static BooleanOption toDefault(boolean value) {
		return value ? BooleanOption.DEFAULT_TRUE : BooleanOption.DEFAULT_FALSE;
	}

	public static BooleanOption from(Optional<Boolean> orig) {
		if (!orig.isPresent())
			return DEFAULT_FALSE;
		return toConfigured(orig.get().booleanValue());
	}

}
