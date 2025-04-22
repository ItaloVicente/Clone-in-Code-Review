		keySet.addDisposeListener(new IDisposeListener() {
			@Override
			public void handleDispose(DisposeEvent disposeEvent) {
				ComputedObservableMap.this.dispose();
			}
		});
