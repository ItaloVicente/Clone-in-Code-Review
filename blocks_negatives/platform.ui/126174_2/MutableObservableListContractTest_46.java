		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.retainAll(Arrays.asList(new Object[] { element0 }));
			}
		}, "List.retainAll(Collection", list,
