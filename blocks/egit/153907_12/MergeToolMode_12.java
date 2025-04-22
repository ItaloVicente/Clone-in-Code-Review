package org.eclipse.egit.ui.internal.diffmerge;

public enum MergeMode implements DiffMergeMode {

	PROMPT(0),

	WORKSPACE(1),

	LAST_HEAD(2),

	OURS(3);

	private int value;

	MergeMode(int i) {
		this.value = i;
	}

	@Override
	public int getValue() {
		return value;
	}

	public MergeMode fromInt(int i) {
		for (MergeMode b : MergeMode.values()) {
			if (b.getValue() == i) {
				return b;
			}
		}
		return null;
	}
}
