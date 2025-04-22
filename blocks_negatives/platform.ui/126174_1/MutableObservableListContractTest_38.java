		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.remove(element1);
			}
		}, "List.remove(Object)", list, Collections.singletonList(element0));
