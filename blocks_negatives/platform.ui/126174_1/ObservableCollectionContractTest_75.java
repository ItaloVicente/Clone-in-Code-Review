		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.contains(delegate.createElement(collection));
			}
		}, "Collection.contains(...)", collection);
