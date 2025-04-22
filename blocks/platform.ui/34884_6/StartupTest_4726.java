package org.eclipse.ui.tests.api;

import junit.framework.Assert;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;

public class StartupClass implements IStartup {

    private static boolean earlyStartupCalled = false;

    private static boolean earlyStartupCompleted = false;

    @Override
	public void earlyStartup() {
        earlyStartupCalled = true;
        Assert.assertNull("IStartup should run in non-UI thread", Display.getCurrent());
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		earlyStartupCompleted = true;
    }

    public static boolean getEarlyStartupCalled() {
        return earlyStartupCalled;
    }

    public static boolean getEarlyStartupCompleted() {
        return earlyStartupCompleted;
    }

    public static void reset() {
        earlyStartupCalled = false;
        earlyStartupCompleted = false;
    }
}
