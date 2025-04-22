		sendEvent(EVENT_EXPORT_BEGIN);
		boolean success = mainPage.finish();
		sendEvent(EVENT_EXPORT_END);
		return success;
	}

	private void sendEvent(String topic) {
		if (eventBroker != null) {
			eventBroker.send(topic, null);
		}
