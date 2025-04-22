		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.retainAll(Arrays.asList(new Object[] { element1 }));
			}

		}, "Collection.retainAll(Collection)", collection);
