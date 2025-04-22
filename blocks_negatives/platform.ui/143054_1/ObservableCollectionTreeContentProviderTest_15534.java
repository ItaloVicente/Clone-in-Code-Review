		Object[] rootElements = new Object[] { "one", "two", "three" };
		final IObservableList rootElementList = new WritableList(Arrays.asList(rootElements), null);
		contentProvider = new ObservableListTreeContentProvider(new IObservableFactory() {
			@Override
			public IObservable createObservable(Object target) {
				if (target == input)
					return rootElementList;
				return null;
			}
