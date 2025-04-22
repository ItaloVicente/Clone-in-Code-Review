package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.core.commands.common.NamedHandleObject;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public final class ContextsExtensionDynamicTest extends DynamicTestCase {

	public ContextsExtensionDynamicTest(final String testName) {
		super(testName);
	}

	@Override
	protected final String getExtensionId() {
		return "contextsExtensionDynamicTest.testDynamicContextAddition";
	}

	@Override
	protected final String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_CONTEXTS;
	}

	@Override
	protected final String getInstallLocation() {
		return "data/org.eclipse.contextsExtensionDynamicTest";
	}

	public final void testContexts() {
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
