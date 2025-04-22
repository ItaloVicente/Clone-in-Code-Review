		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.retainAll(Arrays.asList(new Object[] { element }));
			}
		}, "List.retainAll(Collection)", list,
