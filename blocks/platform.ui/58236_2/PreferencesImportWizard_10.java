
	private void sendEvent(String topic) {
		if (eventBroker != null) {
			eventBroker.send(topic, null);
		}
	}
