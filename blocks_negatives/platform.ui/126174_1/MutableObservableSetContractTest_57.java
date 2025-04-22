		assertRemoveDiffEntry(new Runnable() {
			@Override
			public void run() {
				set.remove(element);
			}
		}, "Set.remove(Object)", set, element);
