		assertSetChangeEventFired(new Runnable() {
			@Override
			public void run() {
				set.add(delegate.createElement(set));
			}
		}, "Set.add(Object)", set);
