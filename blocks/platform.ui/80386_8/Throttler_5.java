package org.eclipse.ui.internal.progress;

import java.time.Duration;
import org.eclipse.swt.widgets.Display;

public class Throttler {
	private final Runnable timerExec;

	private final Display display;

	private volatile boolean scheduled;

	public Throttler(Display display, Duration minWaitTime, Runnable runnable) {
		this.display = display;
		if (minWaitTime.isNegative()) {
			throw new IllegalArgumentException("Minimum wait time must be positive"); //$NON-NLS-1$
		}
		if (minWaitTime.toMillis() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(
					"Minimum wait time must be smaller than " + Integer.MAX_VALUE); //$NON-NLS-1$
		}
		int minWaitBetweenRunMillis = (int) minWaitTime.toMillis();
		this.timerExec = () -> {
			if (!display.isDisposed()) {
				display.timerExec(minWaitBetweenRunMillis, () -> {
					scheduled = false;
					runnable.run();
				});
			}
		};
	}

	public void throttledExec() {
		if (!scheduled && !display.isDisposed()) {
			scheduled = true;
			if (Thread.currentThread() == display.getThread()) {
				timerExec.run();
			} else {
				display.asyncExec(timerExec);
			}
		}
	}
}
