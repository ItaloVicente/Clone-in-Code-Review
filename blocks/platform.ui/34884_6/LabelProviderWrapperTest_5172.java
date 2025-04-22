package org.eclipse.ui.tests.statushandlers;

import org.eclipse.ui.statushandlers.AbstractStatusHandler;
import org.eclipse.ui.statushandlers.StatusAdapter;

public class FreeStatusHandler extends AbstractStatusHandler {

	private static AbstractStatusHandler tester;

	@Override
	public synchronized void handle(StatusAdapter statusAdapter, int style) {
		if (tester != null) {
			tester.handle(statusAdapter, style);
		}
	}

	public static synchronized void setTester(AbstractStatusHandler tester) {
		FreeStatusHandler.tester = tester;
	}
}
