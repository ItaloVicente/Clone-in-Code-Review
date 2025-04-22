		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.add(element1);
			}
		}, "List.add(Object)", list,
