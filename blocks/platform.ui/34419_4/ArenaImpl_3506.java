package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

public final class WeightKind extends AbstractEnumerator {
	public static final int POUNDS = 0;

	public static final int KILOGRAMS = 1;

	public static final WeightKind POUNDS_LITERAL = new WeightKind(POUNDS, "pounds", "pounds"); //$NON-NLS-1$ //$NON-NLS-2$

	public static final WeightKind KILOGRAMS_LITERAL = new WeightKind(KILOGRAMS, "kilograms", "kilograms"); //$NON-NLS-1$ //$NON-NLS-2$

	private static final WeightKind[] VALUES_ARRAY =
		new WeightKind[] {
			POUNDS_LITERAL,
			KILOGRAMS_LITERAL,
		};

	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	public static WeightKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			WeightKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static WeightKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			WeightKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static WeightKind get(int value) {
		switch (value) {
			case POUNDS: return POUNDS_LITERAL;
			case KILOGRAMS: return KILOGRAMS_LITERAL;
		}
		return null;
	}

	private WeightKind(int value, String name, String literal) {
		super(value, name, literal);
	}

} //WeightKind
