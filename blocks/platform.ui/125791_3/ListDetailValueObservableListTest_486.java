		IObservableFactory detailValueFactory = new IObservableFactory() {
			@Override
			public IObservable createObservable(Object target) {
				WritableValue detailObservable = new WritableValue();
				detailObservables.add(detailObservable);
				return detailObservable;
			}
