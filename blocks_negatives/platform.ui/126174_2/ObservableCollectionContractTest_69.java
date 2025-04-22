		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.iterator();
			}
		}, "Collection.iterator()", collection);
