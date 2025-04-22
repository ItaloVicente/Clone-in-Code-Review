		findToolbar.addSelectionListener(new Listener() {
			@Override
			public void handleEvent(Event event) {
				graph.selectCommit((RevCommit) event.data);
			}
		});
