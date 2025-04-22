		assertContainsDuringChangeEvent(new Runnable() {
			@Override
			public void run() {
				list.addAll(0, Arrays.asList(new Object[] { element }));
			}
		}, "List.addAll(int, Collection)", list, element);
