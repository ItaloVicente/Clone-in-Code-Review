		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.addAll(1, Collections.singletonList(element1));
			}
		}, "List.addAll(int, Collection)", list,
