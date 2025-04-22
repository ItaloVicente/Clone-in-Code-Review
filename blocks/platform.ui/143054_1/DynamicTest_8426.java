		fixedModelRegistry.removeActivity(activity_to_listen.getId(),
				activity_to_listen_name);
		assertTrue(listenerType == -1);
		listenerType = 5;
		fixedModelRegistry.addActivity(activity_to_listen.getId(),
				activity_to_listen_name);
		assertTrue(listenerType == -1);
		listenerType = 6;
