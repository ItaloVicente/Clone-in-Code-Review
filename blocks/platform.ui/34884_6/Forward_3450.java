package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

public final class DefencePositionKind extends AbstractEnumerator {
	public static final int LEFT_DEFENCE = 0;

	public static final int RIGHT_DEFENCE = 1;

	public static final DefencePositionKind LEFT_DEFENCE_LITERAL = new DefencePositionKind(LEFT_DEFENCE, "left_defence", "left_defence"); //$NON-NLS-1$ //$NON-NLS-2$

	public static final DefencePositionKind RIGHT_DEFENCE_LITERAL = new DefencePositionKind(RIGHT_DEFENCE, "right_defence", "right_defence"); //$NON-NLS-1$ //$NON-NLS-2$

	private static final DefencePositionKind[] VALUES_ARRAY =
		new DefencePositionKind[] {
			LEFT_DEFENCE_LITERAL,
			RIGHT_DEFENCE_LITERAL,
		};

	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	public static DefencePositionKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DefencePositionKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static DefencePositionKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DefencePositionKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static DefencePositionKind get(int value) {
		switch (value) {
			case LEFT_DEFENCE: return LEFT_DEFENCE_LITERAL;
			case RIGHT_DEFENCE: return RIGHT_DEFENCE_LITERAL;
		}
		return null;
	}

	private DefencePositionKind(int value, String name, String literal) {
		super(value, name, literal);
	}

} //DefencePositionKind
