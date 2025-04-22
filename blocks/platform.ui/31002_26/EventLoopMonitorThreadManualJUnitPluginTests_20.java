package org.eclipse.ui.internal.monitoring;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;

public class DelayHandler extends AbstractHandler {
	private static final int NS_PER_MS = 1000 * 1000;
	private static final Display display = Display.getDefault();

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String syncStr =
				event.getParameter("org.eclipse.ui.monitoring.manualtesting.commands.delay.sync");
		final boolean sync = syncStr != null && !syncStr.isEmpty() && Boolean.parseBoolean(syncStr);

		String durationStr =
				event.getParameter("org.eclipse.ui.monitoring.manualtesting.commands.delay.duration");
		if (durationStr == null) {
			throw new IllegalArgumentException(
					"org.eclipse.ui.monitoring.manualtesting.commands.delay.duration parameter not provided");
		}
		final long durationNs = Long.parseLong(durationStr) * NS_PER_MS;

		Runnable doDelay = new Runnable() {
			@Override
			public void run() {
				double durationMs = (double) durationNs / (double) NS_PER_MS;
				System.out.printf("Starting delay for %.6fms%n", durationMs);
				long startTime = System.nanoTime();
				monitoringTestSleep(durationNs);
				long actualDuration = System.nanoTime() - startTime;
				System.out.printf("Delay for %.6fms complete. Actual duration: %.6fms%n",
						durationMs, (double) actualDuration / (double) NS_PER_MS);
			}
		};

		if (sync) {
			display.syncExec(doDelay);
		} else {
			display.asyncExec(doDelay);
		}

		return null;
	}

	private static void monitoringTestSleep(long nanoseconds) {
		long endTime = System.nanoTime() + nanoseconds;

		try {
			while (true) {
				long nsRemaining = endTime - System.nanoTime();
				if (nsRemaining > NS_PER_MS) {
					Thread.sleep(nsRemaining / NS_PER_MS, (int) (nsRemaining % NS_PER_MS));
				} else if (nsRemaining <= 0) {
					return;
				} // < 1ms remaining, just spin-wait
			}
		} catch (InterruptedException e) {
		}
	}
}
