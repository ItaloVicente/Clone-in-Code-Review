		return new IObservableFactory<S, IObservableSet<E>>() {
			@Override
			public IObservableSet<E> createObservable(S target) {
				return observe(target);
			}
		};
