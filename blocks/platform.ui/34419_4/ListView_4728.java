package org.eclipse.ui.tests.api;

import org.eclipse.core.expressions.PropertyTester;

public class ListElementPropertyTester extends PropertyTester {

    public static final String ATTR_NAME = "name";

    @Override
	public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        ListElement le = (ListElement) receiver;
        if (property.equals(ATTR_NAME)) {
            return expectedValue.equals(le.getName());
        }
        return false;
    }
}

