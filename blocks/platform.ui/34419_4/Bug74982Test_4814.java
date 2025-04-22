package org.eclipse.ui.tests.commands;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.ExternalActionManager;
import org.eclipse.jface.util.Util;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.eclipse.ui.tests.statushandlers.TestStatusHandler;

public final class Bug73756Test extends UITestCase {

	private static String CMD_ID = "a command that is not defined";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(ExternalActionManager.class.getName());

	private static int SEVERITY = IStatus.ERROR;

	private static String MESSAGE = MessageFormat.format(Util.translateString(
			RESOURCE_BUNDLE, "undefinedCommand.WarningMessage", null), //$NON-NLS-1$
			(Object[]) new String[] { CMD_ID });

	private static String PLUGIN_ID = "org.eclipse.jface";

	public Bug73756Test(final String name) {
		super(name);
	}

	public final void testUndefinedCommandIsActiveLogged() {
		ExternalActionManager.getInstance().getCallback().isActive(CMD_ID);

		assertEquals(TestStatusHandler.getLastHandledStyle(), StatusManager.LOG);
		assertStatusAdapter(TestStatusHandler.getLastHandledStatusAdapter());
	}

	private void assertStatusAdapter(StatusAdapter statusAdapter) {
		assertNotNull("A warning should have been logged.", statusAdapter);
		IStatus status = statusAdapter.getStatus();
		assertEquals(status.getSeverity(), SEVERITY);
		assertEquals(status.getPlugin(), PLUGIN_ID);
		assertEquals(status.getMessage(), MESSAGE);
	}
}
