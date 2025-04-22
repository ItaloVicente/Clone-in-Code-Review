package org.eclipse.e4.ui.css.core.utils;

public class SharedStringBuilder extends ThreadLocal<StringBuilder> {

	@Override
	protected StringBuilder initialValue() {
		return new StringBuilder();
	}

	@Override
	public StringBuilder get() {
		StringBuilder sb = super.get();
		sb.setLength(0);
		return sb;
	}
}
