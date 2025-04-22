
package org.eclipse.jgit.http.test.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

class TestRequestLog extends AbstractLifeCycle implements RequestLog {
	private final List<AccessEvent> events = new ArrayList<AccessEvent>();

	synchronized void clear() {
		events.clear();
	}

	synchronized List<AccessEvent> getEvents() {
		return events;
	}

	public synchronized void log(Request request
		events.add(new AccessEvent(request
	}
}
