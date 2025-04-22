	private void sendEvent(int eventType) {
		Event event = new Event();
		event.type = eventType;
		monitoringThread.handleEvent(event);
	}

