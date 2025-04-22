package org.eclipse.ui.tests.harness.util;

import org.eclipse.swt.widgets.Display;

public abstract class DisplayHelper {
	protected DisplayHelper() {
	}

	public final boolean waitForCondition(Display display, long timeout) {
		if (condition())
			return true;

		if (timeout < 0)
			return false;

		driveEventQueue(display);
		if (condition())
			return true;

		if (timeout == 0)
			return false;

		DisplayWaiter waiter= new DisplayWaiter(display);
		DisplayWaiter.Timeout timeoutState= waiter.start(timeout);
		boolean condition;
		try {
			do {
				if (display.sleep())
					driveEventQueue(display);
				condition= condition();
			} while (!condition && !timeoutState.hasTimedOut());
		} finally {
			waiter.stop();
		}
		return condition;
	}

	public static void sleep(long millis) {
		sleep(Display.getCurrent(), millis);
	}

	public static void sleep(Display display, long millis) {
		new DisplayHelper() {
			public boolean condition() {
				return false;
			}
		}.waitForCondition(display, millis);
	}

	public static boolean runEventLoop(Display display, long timeout) {
		if (timeout < 0)
			return false;

		if (timeout == 0)
			return driveEventQueue(display);

		DisplayWaiter waiter= new DisplayWaiter(display);
		DisplayWaiter.Timeout timeoutState= waiter.start(timeout);
		boolean events= false;
		if (display.sleep() && !timeoutState.hasTimedOut()) {
			driveEventQueue(display);
			events= true;
		}
		waiter.stop();
		return events;
	}

	protected abstract boolean condition();

	private static boolean driveEventQueue(Display display) {
		boolean events= false;
		while (display.readAndDispatch()) {
			events= true;
		}
		return events;
	}

	public final boolean waitForCondition(Display display, long timeout, long interval) {
		if (condition())
			return true;

		if (timeout < 0)
			return false;

		driveEventQueue(display);
		if (condition())
			return true;

		if (timeout == 0)
			return false;

		DisplayWaiter waiter= new DisplayWaiter(display, true);
		long currentTimeMillis= System.currentTimeMillis();
		long finalTimeout= timeout + currentTimeMillis;
		if (finalTimeout < currentTimeMillis)
			finalTimeout= Long.MAX_VALUE;
		boolean condition;
		try {
			do {
				waiter.restart(interval);
				if (display.sleep())
					driveEventQueue(display);
				condition= condition();
			} while (!condition && finalTimeout > System.currentTimeMillis());
		} finally {
			waiter.stop();
		}
		return condition;
	}

}
