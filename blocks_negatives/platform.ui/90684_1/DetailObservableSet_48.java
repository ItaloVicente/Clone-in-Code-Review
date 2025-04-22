		outerObservableValue.addDisposeListener(new IDisposeListener() {
			@Override
			public void handleDispose(DisposeEvent disposeEvent) {
				dispose();
			}
		});
