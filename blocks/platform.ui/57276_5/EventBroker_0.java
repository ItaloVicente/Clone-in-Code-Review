		if (data instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>)data;
			if(map.containsKey(EventConstants.EVENT_TOPIC) && map.containsKey(IEventBroker.DATA)) {
				return new Event(topic, map);
			}
			Map<String, Object> eventMap = new HashMap<>(map);
			if (!eventMap.containsKey(EventConstants.EVENT_TOPIC)) {
				eventMap.put(EventConstants.EVENT_TOPIC, topic);
			}
			if (!eventMap.containsKey(IEventBroker.DATA)) {
				eventMap.put(IEventBroker.DATA, data);
			}
			event = new Event(topic, eventMap);
		} else if (data instanceof Dictionary<?, ?>) {
			Dictionary<String, Object> d = (Dictionary<String, Object>) data;
			if (d.get(EventConstants.EVENT_TOPIC) != null && d.get(IEventBroker.DATA) != null) {
				return new Event(topic, d);
			}
			Map<String, Object> map = convertToMap(d);
			if (map.get(EventConstants.EVENT_TOPIC) == null) {
				map.put(EventConstants.EVENT_TOPIC, topic);
			}
			if (map.get(IEventBroker.DATA) == null) {
				map.put(IEventBroker.DATA, map);
			}
			event = new Event(topic, map);
