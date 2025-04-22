		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.set(0, delegate.createElement(list));
			}
		}, "List.set(int, Object)", list);
