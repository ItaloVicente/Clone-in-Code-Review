		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.hashCode();
			}
		}, "Collection.hashCode()", collection);
