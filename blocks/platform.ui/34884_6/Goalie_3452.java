package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

public final class ForwardPositionKind extends AbstractEnumerator {
	public static final int LEFT_WING = 0;

	public static final int RIGHT_WING = 1;

	public static final int CENTER = 2;

	public static final ForwardPositionKind LEFT_WING_LITERAL = new ForwardPositionKind(LEFT_WING, "left_wing", "left_wing"); //$NON-NLS-1$ //$NON-NLS-2$

	public static final ForwardPositionKind RIGHT_WING_LITERAL = new ForwardPositionKind(RIGHT_WING, "right_wing", "right_wing"); //$NON-NLS-1$ //$NON-NLS-2$

	public static final ForwardPositionKind CENTER_LITERAL = new ForwardPositionKind(CENTER, "center", "center"); //$NON-NLS-1$ //$NON-NLS-2$

	private static final ForwardPositionKind[] VALUES_ARRAY =
		new ForwardPositionKind[] {
			LEFT_WING_LITERAL,
			RIGHT_WING_LITERAL,
			CENTER_LITERAL,
		};

	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	public static ForwardPositionKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ForwardPositionKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static ForwardPositionKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ForwardPositionKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static ForwardPositionKind get(int value) {
		switch (value) {
			case LEFT_WING: return LEFT_WING_LITERAL;
			case RIGHT_WING: return RIGHT_WING_LITERAL;
			case CENTER: return CENTER_LITERAL;
		}
		return null;
	}

	private ForwardPositionKind(int value, String name, String literal) {
		super(value, name, literal);
	}

} //ForwardPositionKind
