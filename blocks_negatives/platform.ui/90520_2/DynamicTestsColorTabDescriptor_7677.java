/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.views.properties.tabbed.dynamic.tab.descriptors;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.tests.views.properties.tabbed.Activator;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsBlackSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.views.DynamicTestsTypeMapper;
import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;

/**
 * A tab descriptor for the dynamic tests view.
 *
 * @author Anthony Hunter
 */
public class DynamicTestsBlackTabDescriptor extends AbstractTabDescriptor {
	private Image image;

	public DynamicTestsBlackTabDescriptor() {
		super();
		getSectionDescriptors().add(
				new DynamicTestsBlackSectionDescriptor(
						new DynamicTestsTypeMapper()));
	}

	@Override
	public String getAfterTab() {
	}

	@Override
	public String getCategory() {
	}

	@Override
	public String getId() {
	}

	@Override
	public Image getImage() {
		if (image == null) {
			image = Activator
		}
		return image;
	}

	@Override
	public String getLabel() {
	}

	@Override
	public boolean isIndented() {
		return true;
	}

}
