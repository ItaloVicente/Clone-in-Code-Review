		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.add(delegate.createElement(collection));
			}
		}, "Collection.add(Object)", collection);
