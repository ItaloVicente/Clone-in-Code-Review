		assertListChangeEventFired(new Runnable() {
			@Override
			public void run() {
				assertSame(element0, list.move(0, 1));
			}
		}, "IObservableList.move(int, int)", list,
