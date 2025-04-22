		if (data instanceof Dictionary<?, ?>) {
			Dictionary<String, Object> d = new Hashtable<>((Map<? extends String, ? extends Object>) data);
			if (d.get(EventConstants.EVENT_TOPIC) == null) {
				d.put(EventConstants.EVENT_TOPIC, topic);
			}
			if (d.get(IEventBroker.DATA) == null) {
				d.put(IEventBroker.DATA, data);
			}
			event = new Event(topic, d);
		} else if (data instanceof Map<?, ?>) {
			Map<String, Object> eventMap = new HashMap<>((Map<String, Object>) data);
			if (!eventMap.containsKey(EventConstants.EVENT_TOPIC)) {
				eventMap.put(EventConstants.EVENT_TOPIC, topic);
			}
			if (!eventMap.containsKey(IEventBroker.DATA)) {
				eventMap.put(IEventBroker.DATA, data);
			}
			event = new Event(topic, eventMap);
