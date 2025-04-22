		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.addAll(Collections.singletonList(element1));
			}
		}, "List.addAll(Collection)", list,
