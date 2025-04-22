		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.toArray();
			}
		}, "Collection.toArray()", collection);
