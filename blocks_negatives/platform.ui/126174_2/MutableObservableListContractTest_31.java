		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				assertSame(element0, list.set(0, element1));
			}
		}, "List.set(int, Object)", list,
