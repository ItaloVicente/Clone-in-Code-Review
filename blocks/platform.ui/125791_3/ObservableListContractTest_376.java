		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.lastIndexOf(delegate.createElement(list));
			}
		}, "List.lastIndexOf(Object)", list);
