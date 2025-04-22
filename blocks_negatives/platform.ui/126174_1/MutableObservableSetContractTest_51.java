		assertAddDiffEntry(new Runnable() {
			@Override
			public void run() {
				set.add(element);
			}
		}, "Set.add(Object)", set, element);
