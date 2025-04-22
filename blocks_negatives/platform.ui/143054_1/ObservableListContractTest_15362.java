		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.get(0);
			}
		}, "List.get(int)", list);
