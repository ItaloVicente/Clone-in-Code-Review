package org.eclipse.ui.commands;

import org.eclipse.core.expressions.Expression;
import org.eclipse.ui.ISources;
import org.eclipse.ui.internal.util.Util;

@Deprecated
@SuppressWarnings("all")
public final class Priority implements Comparable {

	public final static Priority LEGACY = new Priority(ISources.LEGACY_LEGACY);

	public final static Priority LOW = new Priority(ISources.LEGACY_LOW);

	public final static Priority MEDIUM = new Priority(ISources.LEGACY_MEDIUM);

	private transient String string = null;

	private int value;

	private Priority(int value) {
		this.value = value;
	}

	@Override
	@Deprecated
	public int compareTo(Object object) {
		Priority castedObject = (Priority) object;
		int compareTo = Util.compare(value, castedObject.value);
		return compareTo;
	}

	@Deprecated
	int getValue() {
		return value;
	}

	@Override
	@Deprecated
	public String toString() {
		if (string == null) {
			final StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("[value="); //$NON-NLS-1$
			stringBuffer.append(value);
			stringBuffer.append(']');
			string = stringBuffer.toString();
		}

		return string;
	}
}
