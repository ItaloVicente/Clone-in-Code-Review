
package org.eclipse.e4.ui.model.fragment.impl;

import org.apache.commons.jxpath.ExpressionContext;
import org.apache.commons.jxpath.Pointer;


public class EclipseExtensionFunctions {

	public static boolean instanceOf(ExpressionContext context, String name) {
		Pointer pointer = context.getContextNodePointer();
		if (pointer == null) {
			return false;
		}
		try {
			Class<?> interfaceToTest = Class.forName(name);
			if (interfaceToTest != null) {
				Object object = pointer.getValue();
				if (object != null) {
					Class<? extends Object> objectClass = object.getClass();
					return interfaceToTest.isAssignableFrom(objectClass);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
