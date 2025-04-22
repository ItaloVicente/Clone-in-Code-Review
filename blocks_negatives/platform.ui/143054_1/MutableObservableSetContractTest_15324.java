		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				set.addAll(Collections.singleton(delegate.createElement(set)));
			}
		}, "Set.addAll(Collection)", set);
