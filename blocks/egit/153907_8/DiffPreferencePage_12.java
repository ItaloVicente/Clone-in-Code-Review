package org.eclipse.egit.ui.internal.diffmerge;

public enum MergeToolMode implements DiffMergeMode {

	INTERNAL(0),
	EXTERNAL_FOR_TYPE(1),
	GIT_CONFIG(2),
	EXTERNAL(3);

	private int value;

	MergeToolMode(int i) {
		this.value = i;
	}

	@Override
	public int getValue() {
		return value;
	}

	public static MergeToolMode fromInt(int i) {
		for (MergeToolMode b : MergeToolMode.values()) {
			if (b.getValue() == i) {
				return b;
			}
		}
		return null;
	}
}
