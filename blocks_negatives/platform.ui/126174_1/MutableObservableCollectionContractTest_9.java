		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.removeAll(Arrays.asList(new Object[] { element }));
			}
		}, "Collection.removeAll(Collection)", collection);
