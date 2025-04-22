		return new IObservableFactory<S, IObservableValue<T>>() {
			@Override
			public IObservableValue<T> createObservable(S target) {
				return observe(realm, target);
			}
		};
