class TestRequestLog extends AbstractLifeCycle implements RequestLog {
	private final List<AccessEvent> events = new ArrayList<AccessEvent>();

	/** Reset the log back to its original empty state. */
	synchronized void clear() {
		events.clear();
	}

	/** @return all of the events made since the last clear. */
	synchronized List<AccessEvent> getEvents() {
		return events;
	}

	public synchronized void log(Request request, Response response) {
		events.add(new AccessEvent(request, response));
	}
