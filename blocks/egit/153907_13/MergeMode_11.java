package org.eclipse.egit.ui.internal.diffmerge;

public enum DiffToolMode implements DiffMergeMode {

	INTERNAL(0),
	EXTERNAL_FOR_TYPE(1),
	GIT_CONFIG(2),
	EXTERNAL(3);

	private int value;

	DiffToolMode(int i) {
		this.value = i;
	}

	@Override
	public int getValue() {
		return value;
	}

	public static DiffToolMode fromInt(int i) {
		for (DiffToolMode b : DiffToolMode.values()) {
			if (b.getValue() == i) {
				return b;
			}
		}
		return null;
	}
}
