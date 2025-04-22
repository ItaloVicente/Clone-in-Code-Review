		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.add(1, element1);
			}
		}, "List.add(int, Object)", list,
