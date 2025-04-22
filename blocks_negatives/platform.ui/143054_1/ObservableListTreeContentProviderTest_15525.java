		final IObservableList children = new WritableList();
		final IObservableList children2 = new WritableList();
		initContentProvider(new IObservableFactory() {
			@Override
			public IObservable createObservable(Object target) {
				if (target == input)
					return children;
				if (target == input2)
					return children2;
				return null;
			}
