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

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsBlueSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsGreenSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsRedSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.views.DynamicTestsTypeMapper;
import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;

/**
 * A tab descriptor for the dynamic tests view.
 *
 * @author Anthony Hunter
 */
public class DynamicTestsColorTabDescriptor extends AbstractTabDescriptor {

	public DynamicTestsColorTabDescriptor() {
		super();
		getSectionDescriptors().add(
				new DynamicTestsRedSectionDescriptor(
						new DynamicTestsTypeMapper()));
		getSectionDescriptors().add(
				new DynamicTestsGreenSectionDescriptor(
						new DynamicTestsTypeMapper()));
		getSectionDescriptors().add(
				new DynamicTestsBlueSectionDescriptor(
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
	public String getLabel() {
	}

}
