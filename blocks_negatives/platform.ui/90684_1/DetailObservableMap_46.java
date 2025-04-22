		master.addDisposeListener(new IDisposeListener() {
			@Override
			public void handleDispose(DisposeEvent staleEvent) {
				dispose();
			}
		});
