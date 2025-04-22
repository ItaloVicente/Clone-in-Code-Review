		masterMap.addDisposeListener(new IDisposeListener() {
			@Override
			public void handleDispose(DisposeEvent event) {
				MapDetailValueObservableMap.this.dispose();
			}
		});
