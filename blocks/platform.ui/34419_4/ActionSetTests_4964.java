package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.core.commands.common.NamedHandleObject;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public final class ActionDefinitionsExtensionDynamicTest extends
		DynamicTestCase {

	public ActionDefinitionsExtensionDynamicTest(final String testName) {
		super(testName);
	}

	@Override
	protected final String getExtensionId() {
		return "actionDefinitionsExtensionDynamicTest.testDynamicActionDefinitionAddition";
	}

	@Override
	protected final String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_ACTION_DEFINITIONS;
	}

	@Override
	protected final String getInstallLocation() {
		return "data/org.eclipse.actionDefinitionsExtensionDynamicTest";
	}

	public final void testActionDefinitions() {
		final ICommandService service = (ICommandService) getWorkbench()
				.getAdapter(ICommandService.class);
		NamedHandleObject namedHandleObject;

		namedHandleObject = service.getCommand("monkey");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}

		getBundle();

		namedHandleObject = service.getCommand("monkey");
		try {
			assertTrue("Monkey".equals(namedHandleObject.getName()));
		} catch (final NotDefinedException e) {
			fail();
		}

		removeBundle();

		namedHandleObject = service.getCommand("monkey");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
	}
}
