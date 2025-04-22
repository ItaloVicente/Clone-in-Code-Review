			Dictionary<String, Object> d = (Dictionary<String, Object>) data;
			if (d.get(EventConstants.EVENT_TOPIC) == null) {
				d.put(EventConstants.EVENT_TOPIC, topic);
			}
			if (d.get(IEventBroker.DATA) == null) {
				d.put(IEventBroker.DATA, data);
			}
			event = new Event(topic, d);
