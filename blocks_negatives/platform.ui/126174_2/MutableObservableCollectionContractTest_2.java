		assertContainsDuringChangeEvent(new Runnable() {
			@Override
			public void run() {
				collection.add(element);
			}
		}, "Collection.add(Object)", collection, element);
