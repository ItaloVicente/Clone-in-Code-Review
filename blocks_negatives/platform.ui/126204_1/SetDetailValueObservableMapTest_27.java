		IObservableFactory detailValueFactory = new IObservableFactory() {
			@Override
			public IObservable createObservable(Object target) {
				WritableValue detailObservable = new WritableValue();
				detailObservables.put(target, detailObservable);
				return detailObservable;
			}
