		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				set.removeAll(Collections.singleton(element));
			}
		}, "Set.removeAll(Collection)", set);
