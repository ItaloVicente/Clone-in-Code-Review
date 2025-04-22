		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.indexOf(delegate.createElement(list));
			}
		}, "List.indexOf(int)", list);
