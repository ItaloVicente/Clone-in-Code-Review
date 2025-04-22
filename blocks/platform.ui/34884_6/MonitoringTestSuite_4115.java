package org.eclipse.ui.internal.monitoring;

import org.eclipse.ui.monitoring.IUiFreezeEventLogger;
import org.eclipse.ui.monitoring.UiFreezeEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockUiFreezeEventLogger implements IUiFreezeEventLogger {
	private static final List<UiFreezeEvent> loggedEvents =
			Collections.synchronizedList(new ArrayList<UiFreezeEvent>());

	@Override
	public void log(UiFreezeEvent event) {
		loggedEvents.add(event);
	}

	public List<UiFreezeEvent> getLoggedEvents() {
		return loggedEvents;
	}
}
