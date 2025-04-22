/*******************************************************************************
 * Copyright (c) 2019 Red Hat Inc. and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.ui.tests.quickaccess;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.quickaccess.IQuickAccessComputer;
import org.eclipse.ui.quickaccess.QuickAccessElement;

public class TestQuickAccessComputer implements IQuickAccessComputer {

	public static final String TEST_QUICK_ACCESS_PROPOSAL_LABEL = "Test Quick Access Proposal";

	public static boolean isContributedItem(String text) {
		return text != null
				&& text.toLowerCase().contains(TestQuickAccessComputer.TEST_QUICK_ACCESS_PROPOSAL_LABEL.toLowerCase());
	}

	private static final QuickAccessElement TEST_ELEMENT = new QuickAccessElement() {

		@Override
		public String getLabel() {
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return null;
		}

		@Override
		public String getId() {
		}

		@Override
		public void execute() {
		}
	};

	@Override
	public QuickAccessElement[] computeElements() {
		return new QuickAccessElement[] { TEST_ELEMENT };
	}

	@Override
	public void resetState() {
	}

	@Override
	public boolean needsRefresh() {
		return false;
	}

}
