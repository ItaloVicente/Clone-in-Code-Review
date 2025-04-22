package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

public final class HeightKind extends AbstractEnumerator {
	public static final int INCHES = 0;

	public static final int CENTIMETERS = 1;

	public static final HeightKind INCHES_LITERAL = new HeightKind(INCHES, "inches", "inches"); //$NON-NLS-1$ //$NON-NLS-2$

	public static final HeightKind CENTIMETERS_LITERAL = new HeightKind(CENTIMETERS, "centimeters", "centimeters"); //$NON-NLS-1$ //$NON-NLS-2$

	private static final HeightKind[] VALUES_ARRAY =
		new HeightKind[] {
			INCHES_LITERAL,
			CENTIMETERS_LITERAL,
		};

	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	public static HeightKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			HeightKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static HeightKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			HeightKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static HeightKind get(int value) {
		switch (value) {
			case INCHES: return INCHES_LITERAL;
			case CENTIMETERS: return CENTIMETERS_LITERAL;
		}
		return null;
	}

	private HeightKind(int value, String name, String literal) {
		super(value, name, literal);
	}

} //HeightKind
