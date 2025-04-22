		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.addAll(Arrays.asList(new Object[] { delegate
						.createElement(collection) }));
			}
		}, "Collection.addAll(Collection)", collection);
