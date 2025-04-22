		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.remove(element);
			}
		}, "Collection.remove(Object)", collection);
