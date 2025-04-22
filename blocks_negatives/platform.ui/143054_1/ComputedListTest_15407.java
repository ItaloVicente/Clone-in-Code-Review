		list.addStaleListener(new IStaleListener() {
			@Override
			public void handleStale(StaleEvent staleEvent) {
				fail("Should not fire stale when list is already dirty");
			}
		});
