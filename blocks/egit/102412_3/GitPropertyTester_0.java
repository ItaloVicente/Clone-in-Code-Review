package org.eclipse.egit.ui.internal.expressions;

import org.eclipse.core.expressions.PropertyTester;

public abstract class AbstractPropertyTester extends PropertyTester {

	protected boolean computeResult(Object expectedValue, boolean result) {
		if (expectedValue instanceof Boolean) {
			return ((Boolean) expectedValue).booleanValue() == result;
		}
		return result;
	}

}
