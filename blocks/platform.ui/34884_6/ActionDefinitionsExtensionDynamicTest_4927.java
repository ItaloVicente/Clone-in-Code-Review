package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.core.commands.common.NamedHandleObject;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public final class AcceleratorScopesExtensionDynamicTest extends
		DynamicTestCase {

	public AcceleratorScopesExtensionDynamicTest(final String testName) {
		super(testName);
	}

	@Override
	protected final String getExtensionId() {
		return "acceleratorScopesExtensionDynamicTest.testDynamicAcceleratorScopeAddition";
	}

	@Override
	protected final String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_ACCELERATOR_SCOPES;
	}

	@Override
	protected final String getInstallLocation() {
		return "data/org.eclipse.acceleratorScopesExtensionDynamicTest";
	}

	public final void testAcceleratorScopes() {
		final IContextService service = (IContextService) getWorkbench()
				.getAdapter(IContextService.class);
		NamedHandleObject namedHandleObject;

		namedHandleObject = service.getContext("monkey");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}

		getBundle();

		namedHandleObject = service.getContext("monkey");
		try {
			assertTrue("Monkey".equals(namedHandleObject.getName()));
		} catch (final NotDefinedException e) {
			fail();
		}

		removeBundle();

		namedHandleObject = service.getContext("monkey");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
	}
}
