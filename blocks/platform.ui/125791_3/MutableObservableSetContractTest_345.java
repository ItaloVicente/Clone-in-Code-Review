		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				set.retainAll(Collections.EMPTY_SET);
			}
		}, "Set.retainAll(Collection)", set);
