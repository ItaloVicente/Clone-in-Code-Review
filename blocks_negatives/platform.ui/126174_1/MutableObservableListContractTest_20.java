		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.add(0, element);
			}
		}, "List.add(int, Object)", list, Collections.singletonList(element));
