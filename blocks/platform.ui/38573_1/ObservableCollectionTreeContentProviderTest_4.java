		final IObservableList rootElementList = new WritableList(Collections.singletonList("element"), null);
		contentProvider = new ObservableListTreeContentProvider(new IObservableFactory() {
			@Override
			public IObservable createObservable(Object target) {
				if (target == input)
					return rootElementList;
				return null;
			}
		}, null);
