		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.removeAll(Collections.singletonList(element));
			}
		}, "List.removeAll(Collection)", list, Collections.EMPTY_LIST);
