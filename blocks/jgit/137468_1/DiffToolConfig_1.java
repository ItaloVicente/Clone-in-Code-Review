
package org.eclipse.jgit.diffmergetool;

public enum BooleanOption {
	notDefined()

	True()

	False();

	private boolean defaultValue;

	BooleanOption() {
		defaultValue = false;
    }

	public void setDefault(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean getDefaultValue() {
		return defaultValue;
	}

	static public boolean toBoolean(BooleanOption option) {
		switch (option) {
		case True:
			return true;
		case False:
			return false;
		case notDefined:
		default:
			return option.getDefaultValue();
		}
	}

}
