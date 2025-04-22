		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.equals(collection);
			}
		}, "Collection.equals(Object)", collection);
