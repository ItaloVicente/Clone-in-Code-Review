		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.addAll(0, Collections.singletonList(element));
			}
		}, "List.addAll(int, Collection)", list,
