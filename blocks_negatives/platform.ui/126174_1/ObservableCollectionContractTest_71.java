		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.size();
			}
		}, "Collection.size()", collection);
