package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.core.commands.common.NamedHandleObject;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.keys.IBindingService;

public final class AcceleratorConfigurationsExtensionDynamicTest extends
		DynamicTestCase {

	public AcceleratorConfigurationsExtensionDynamicTest(final String testName) {
		super(testName);
	}

	@Override
	protected final String getExtensionId() {
		return "acceleratorConfigurationsExtensionDynamicTest.testDynamicAcceleratorConfigurationAddition";
	}

	@Override
	protected final String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_ACCELERATOR_CONFIGURATIONS;
	}

	@Override
	protected final String getInstallLocation() {
		return "data/org.eclipse.acceleratorConfigurationsExtensionDynamicTest";
	}

	public final void testAcceleratorConfigurations() {
		final IBindingService service = (IBindingService) getWorkbench()
				.getAdapter(IBindingService.class);
		NamedHandleObject namedHandleObject;

		namedHandleObject = service.getScheme("monkey");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}

		getBundle();

		namedHandleObject = service.getScheme("monkey");
		try {
			assertTrue("Monkey".equals(namedHandleObject.getName()));
		} catch (final NotDefinedException e) {
			fail();
		}

		removeBundle();

		namedHandleObject = service.getScheme("monkey");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
	}
}
