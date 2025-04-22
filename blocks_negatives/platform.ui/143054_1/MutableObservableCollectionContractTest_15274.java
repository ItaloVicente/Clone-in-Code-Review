		assertContainsDuringChangeEvent(new Runnable() {
			@Override
			public void run() {
				collection.addAll(Arrays.asList(new Object[] { element }));
			}
		}, "Collection.addAll(Collection)", collection, element);
