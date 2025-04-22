		assertSetChangeEventFired(new Runnable() {
			@Override
			public void run() {
				set.removeAll(Arrays.asList(new Object[] { element }));
			}
		}, "Set.removeAll(Collection)", set);
