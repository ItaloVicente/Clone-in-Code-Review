		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.listIterator(0);
			}
		}, "List.listIterator(int)", list);
