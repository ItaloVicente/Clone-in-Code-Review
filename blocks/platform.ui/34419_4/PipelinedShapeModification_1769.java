package org.eclipse.ui.navigator;

public final class OverridePolicy {

	public static final int InvokeOnlyIfSuppressedExtAlsoVisibleAndActive_VALUE = -1;

	public static final int InvokeAlwaysRegardlessOfSuppressedExt_VALUE = 1;

	public static final String InvokeOnlyIfSuppressedExtAlsoVisibleAndActive_LITERAL = "InvokeOnlyIfSuppressedExtAlsoVisibleAndActive"; //$NON-NLS-1$

	public static final String InvokeAlwaysRegardlessOfSuppressedExt_LITERAL = "InvokeAlwaysRegardlessOfSuppressedExt"; //$NON-NLS-1$

	public static final OverridePolicy InvokeOnlyIfSuppressedExtAlsoVisibleAndActive = new OverridePolicy(
			InvokeOnlyIfSuppressedExtAlsoVisibleAndActive_VALUE,
			InvokeOnlyIfSuppressedExtAlsoVisibleAndActive_LITERAL);

	public static final OverridePolicy InvokeAlwaysRegardlessOfSuppressedExt = new OverridePolicy(
			InvokeAlwaysRegardlessOfSuppressedExt_VALUE,
			InvokeAlwaysRegardlessOfSuppressedExt_LITERAL);

	public static final OverridePolicy[] ENUM_ARRAY = new OverridePolicy[] {
			InvokeOnlyIfSuppressedExtAlsoVisibleAndActive,
			InvokeAlwaysRegardlessOfSuppressedExt };

	public static OverridePolicy get(String aLiteral) {
		for (int i = 0; i < ENUM_ARRAY.length; i++) {
			if (ENUM_ARRAY[i].getLiteral().equals(aLiteral)) {
				return ENUM_ARRAY[i];
			}
		}
		return InvokeAlwaysRegardlessOfSuppressedExt;
	}

	public static OverridePolicy get(int aValue) {

		switch (aValue) {
		case InvokeOnlyIfSuppressedExtAlsoVisibleAndActive_VALUE:
			return InvokeOnlyIfSuppressedExtAlsoVisibleAndActive;
		case InvokeAlwaysRegardlessOfSuppressedExt_VALUE:
		default:
			return InvokeAlwaysRegardlessOfSuppressedExt;

		}
	}

	private final int value;

	private final String literal;

	protected OverridePolicy(int aValue, String aLiteral) {
		value = aValue;
		literal = aLiteral;
	}

	public String getLiteral() {
		return literal;
	}

	public int getValue() {
		return value;
	}
}
