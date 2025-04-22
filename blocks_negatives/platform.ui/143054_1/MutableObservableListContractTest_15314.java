		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.removeAll(Arrays.asList(new Object[] { element1 }));
			}
		}, "List.removeAll(Collection)", list,
