		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.clear();
			}
		}, "List.clear()", collection);
