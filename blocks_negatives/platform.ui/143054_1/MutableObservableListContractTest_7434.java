		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				list.addAll(0, Arrays.asList(new Object[] { delegate
						.createElement(list) }));
			}
		}, "List.addAll(int, Collection)", list);
