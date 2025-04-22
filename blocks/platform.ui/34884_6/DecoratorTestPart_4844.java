package org.eclipse.ui.tests.decorators;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.decorators.DecoratorDefinition;
import org.eclipse.ui.internal.decorators.DecoratorManager;
import org.eclipse.ui.tests.navigator.AbstractNavigatorTest;

public class DecoratorTestCase extends AbstractNavigatorTest implements
		ILabelProviderListener {

	private DecoratorDefinition definition;

	private boolean updated = false;

	public DecoratorTestCase(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		createTestFile();
		showNav();

		WorkbenchPlugin.getDefault().getDecoratorManager().addListener(this);

		DecoratorDefinition[] definitions = WorkbenchPlugin.getDefault()
				.getDecoratorManager().getAllDecoratorDefinitions();
		for (int i = 0; i < definitions.length; i++) {
			if (definitions[i].getId().equals(
					"org.eclipse.ui.tests.adaptable.decorator"))
				definition = definitions[i];
		}
	}

	private DecoratorManager getDecoratorManager() {
		return WorkbenchPlugin.getDefault().getDecoratorManager();
	}

	@Override
	protected void doTearDown() throws Exception {
		super.doTearDown();
		getDecoratorManager().removeListener(this);
	}

	public void testEnableDecorator() {
		getDecoratorManager().clearCaches();
		definition.setEnabled(true);
		getDecoratorManager().updateForEnablementChange();

	}

	public void testDisableDecorator() {
		getDecoratorManager().clearCaches();
		definition.setEnabled(false);
		getDecoratorManager().updateForEnablementChange();
	}

	public void testRefreshContributor() {

		updated = false;
		getDecoratorManager().clearCaches();
		definition.setEnabled(true);
		getDecoratorManager().updateForEnablementChange();

		assertTrue("Got an update", updated);
		updated = false;

	}

	@Override
	public void labelProviderChanged(LabelProviderChangedEvent event) {
		updated = true;
	}

}
