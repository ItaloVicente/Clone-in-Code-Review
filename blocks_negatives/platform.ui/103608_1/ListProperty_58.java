		return new IObservableFactory<S, IObservableList<E>>() {
			@Override
			public IObservableList<E> createObservable(S target) {
				return observe(target);
			}
		};
