		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.add(element);
			}
		}, "List.add(Object)", list, Collections.singletonList(element));
