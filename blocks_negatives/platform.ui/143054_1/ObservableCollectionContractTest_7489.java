		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.toArray(new Object[collection.size()]);
			}
		}, "Collection.toArray(Object[])", collection);
