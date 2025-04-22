		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.clear();
			}
		}, "List.clear()", list, Collections.EMPTY_LIST);
