/*******************************************************************************
 * Copyright (c) 2007, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.views.markers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.ui.ide.IDE;

/**
 * QuickFixPropertyTester is the property tester for the quick fix object.
 *
 * @since 3.4
 *
 */
public class QuickFixPropertyTester extends PropertyTester {


	/**
	 * Create a new instance of the receiver.
	 */
	public QuickFixPropertyTester() {
		super();
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		if (property.equals(QUICK_FIX))
			return IDE.getMarkerHelpRegistry().hasResolutions(
					((MarkerEntry) receiver).getMarker());
		return false;
	}

}
