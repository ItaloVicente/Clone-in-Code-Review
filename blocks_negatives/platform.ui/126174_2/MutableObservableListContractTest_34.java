		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.move(0, 1);
			}
		}, "IObservableList.move(int, int)", list);
