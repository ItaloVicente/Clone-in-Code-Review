
package org.eclipse.ui.tests.statushandlers;

import org.eclipse.ui.internal.WorkbenchErrorHandlerProxy;
import org.eclipse.ui.statushandlers.AbstractStatusHandler;
import org.eclipse.ui.statushandlers.StatusAdapter;

public class TestStatusHandler extends AbstractStatusHandler {

	private static StatusAdapter lastHandledStatusAdapter;

	private static int lastHandledStyle;

	private static AbstractStatusHandler workbenchHandler;

	@Override
	public void handle(StatusAdapter statusAdapter, int style) {
		lastHandledStatusAdapter = statusAdapter;
		lastHandledStyle = style;

		if (workbenchHandler == null) {
			workbenchHandler = new WorkbenchErrorHandlerProxy();
		}

		workbenchHandler.handle(statusAdapter, style);
	}

	public static StatusAdapter getLastHandledStatusAdapter() {
		return lastHandledStatusAdapter;
	}

	public static int getLastHandledStyle() {
		return lastHandledStyle;
	}
}
