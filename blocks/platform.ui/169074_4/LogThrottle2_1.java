package org.eclipse.ui.internal.services;

import java.util.concurrent.LinkedBlockingQueue;
import org.eclipse.e4.ui.internal.workbench.Activator;
import org.osgi.service.log.LogLevel;

public class LogThrottle {

	private LinkedBlockingQueue<String> fAlreadyLoggedEntries;
	private String fThrottleMessage;

	public LogThrottle(int queueSize) {
		fAlreadyLoggedEntries = new LinkedBlockingQueue<>(queueSize);
		fThrottleMessage = "The previous message has been throttled.\nThe previous message has already been logged and will not be logged again."; //$NON-NLS-1$
	}

	public boolean log(int logLevel, String message, Throwable e) {
		if (store(message)) {
			Activator.log(logLevel, message, e);
			Activator.log(LogLevel.WARN.ordinal(), fThrottleMessage);
			return true;
		}
		return false;
	}

	protected boolean store(String message) {
		boolean alreadyLogged = fAlreadyLoggedEntries.contains(message);
		if (alreadyLogged) {
			fAlreadyLoggedEntries.remove(message);
		}
		while (!fAlreadyLoggedEntries.offer(message)) {
			fAlreadyLoggedEntries.poll();
		}
		return !alreadyLogged;
	}
}
