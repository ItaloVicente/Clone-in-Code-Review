		assertSetChangeEventFired(new Runnable() {
			@Override
			public void run() {
				set.addAll(Arrays.asList(new Object[] { delegate
						.createElement(set) }));
			}
		}, "Set.addAll(Collection", set);
