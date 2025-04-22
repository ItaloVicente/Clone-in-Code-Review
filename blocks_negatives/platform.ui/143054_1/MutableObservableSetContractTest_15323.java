		assertAddDiffEntry(new Runnable() {
			@Override
			public void run() {
				set.addAll(Arrays.asList(new Object[] { element }));
			}
		}, "Set.addAll(Collection)", set, element);
