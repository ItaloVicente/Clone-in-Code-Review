		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.addAll(Collections.singletonList(element));
			}
		}, "List.addAll(Collection", list, Collections.singletonList(element));
