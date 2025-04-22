		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.remove(element);
			}
		}, "List.remove(Object)", list, Collections.EMPTY_LIST);
