		observable.addDisposeListener(new IDisposeListener() {
			@Override
			public void handleDispose(DisposeEvent staleEvent) {
				assertTrue(observable.isDisposed());
			}
		});
