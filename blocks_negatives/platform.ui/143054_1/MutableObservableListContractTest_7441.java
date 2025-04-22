		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.set(1, newElement1);
			}
		}, "List.set(int, Object)", list,
