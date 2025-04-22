
	private void log(Request request, Response response) {
		synchronized (events) {
			events.add(new AccessEvent(request, response));
		}
	}
