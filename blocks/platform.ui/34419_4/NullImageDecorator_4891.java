package org.eclipse.ui.tests.decorators;

import org.eclipse.jface.viewers.ILabelProviderListener;

public class LightweightDecoratorTestCase extends DecoratorEnablementTestCase
		implements ILabelProviderListener {

	public LightweightDecoratorTestCase(String testName) {
		super(testName);
	}

	public void testRefreshContributor() {
		updated = false;
		getDecoratorManager().clearCaches();
		definition.setEnabled(true);
		getDecoratorManager().updateForEnablementChange();

		assertTrue("Got an update", updated);
		updated = false;

	}

}
