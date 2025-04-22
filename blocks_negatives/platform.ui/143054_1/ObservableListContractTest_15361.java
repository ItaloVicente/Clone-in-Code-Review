		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.listIterator();
			}
		}, "List.listIterator()", list);
