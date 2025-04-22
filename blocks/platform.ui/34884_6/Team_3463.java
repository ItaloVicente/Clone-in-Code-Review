package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

public final class ShotKind extends AbstractEnumerator {
	public static final int LEFT = 0;

	public static final int RIGHT = 1;

	public static final ShotKind LEFT_LITERAL = new ShotKind(LEFT, "left", "left"); //$NON-NLS-1$ //$NON-NLS-2$

	public static final ShotKind RIGHT_LITERAL = new ShotKind(RIGHT, "right", "right"); //$NON-NLS-1$ //$NON-NLS-2$

	private static final ShotKind[] VALUES_ARRAY =
		new ShotKind[] {
			LEFT_LITERAL,
			RIGHT_LITERAL,
		};

	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	public static ShotKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ShotKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static ShotKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ShotKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static ShotKind get(int value) {
		switch (value) {
			case LEFT: return LEFT_LITERAL;
			case RIGHT: return RIGHT_LITERAL;
		}
		return null;
	}

	private ShotKind(int value, String name, String literal) {
		super(value, name, literal);
	}

} //ShotKind
