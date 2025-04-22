		assertSetChangeEventFired(new Runnable() {
			@Override
			public void run() {
				set.clear();
			}
		}, "Set.clear()", set);
