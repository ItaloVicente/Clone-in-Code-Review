		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.remove(0);
			}
		}, "List.remove(int)", list, Collections.EMPTY_LIST);
