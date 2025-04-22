		assertRemoveDiffEntry(new Runnable() {
			@Override
			public void run() {
				set.retainAll(Arrays.asList(new Object[] { element1 }));
			}
		}, "Set.retainAll(Collection)", set, element2);
