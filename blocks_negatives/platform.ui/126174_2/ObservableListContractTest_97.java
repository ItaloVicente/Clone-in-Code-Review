		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.subList(0, 1);
			}
		}, "List.subList(int, int)", list);
