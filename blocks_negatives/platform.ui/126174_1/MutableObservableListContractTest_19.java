		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.add(0, delegate.createElement(list));
			}
		}, "List.add(int, Object)", list);
