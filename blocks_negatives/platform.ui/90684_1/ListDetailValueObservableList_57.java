		masterList.addDisposeListener(new IDisposeListener() {
			@Override
			public void handleDispose(DisposeEvent event) {
				ListDetailValueObservableList.this.dispose();
			}
		});
