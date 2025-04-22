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

/**
 * ContentGeneratorPropertyTester is the property tester for what content
 * generator is being shown.
 *
 * @since 3.4
 *
 */
public class MarkersViewPropertyTester extends PropertyTester {






	/**
	 * Create a new instance of the receiver.
	 */
	public MarkersViewPropertyTester() {
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {

		if (!(receiver instanceof ExtendedMarkersView))
			return false;

		ExtendedMarkersView view = (ExtendedMarkersView) receiver;

		if (property.equals(ATTRIBUTE_CONTENT_GENERATOR))
			return testContentGenerator(view, args);
		if (property.equals(ATTRIBUTE_HAS_FILTERS))
			return view.getAllFilters().size() > 0;
		if (property.equals(ATTRIBUTE_HAS_GROUPS))
			return view.getBuilder().getGenerator().getMarkerGroups().size() > 0;

		return false;
	}

	/**
	 * Test if the content generator in the args match the receiver.
	 *
	 * @param view
	 * @param args
	 * @return boolean
	 */
	private boolean testContentGenerator(ExtendedMarkersView view, Object[] args) {

		String currentGenerator = view.getBuilder().getGenerator().getId();
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(currentGenerator))
				return true;

			if (args[i].equals(ANY_CONTENT_GENERATOR))
				return true;
		}
		return false;
	}
}
