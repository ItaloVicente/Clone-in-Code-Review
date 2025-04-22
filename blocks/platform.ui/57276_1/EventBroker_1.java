			Map<String, Object> map = (Map<String, Object>)data;
			if (!map.containsKey(EventConstants.EVENT_TOPIC)) {
				map.put(EventConstants.EVENT_TOPIC, topic);
			}
			if (!map.containsKey(IEventBroker.DATA)) {
				map.put(IEventBroker.DATA, data);
			}
			event = new Event(topic, map);
