		initContentProvider(new IObservableFactory() {
			@Override
			public IObservable createObservable(Object target) {
				return target == input ? elements : null;
			}
		});
