		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.remove(1);
			}
		}, "List.remove(int)", list, Collections.singletonList(element0));
