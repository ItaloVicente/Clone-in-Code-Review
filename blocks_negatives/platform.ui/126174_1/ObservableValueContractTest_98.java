		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				observable.getValue();
			}
		}, formatFail("IObservableValue.getValue()"), observable);
