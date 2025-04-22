		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.isEmpty();
			}
		}, "Collection.isEmpty()", collection);
