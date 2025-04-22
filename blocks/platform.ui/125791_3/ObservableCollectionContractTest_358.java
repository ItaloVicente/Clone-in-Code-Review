		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.containsAll(Arrays.asList(new Object[] { delegate
						.createElement(collection) }));
			}
		}, "Collection.containsAll(Collection)", collection);
