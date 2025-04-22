		return new IObservableFactory<S, IObservableMap<K, V>>() {
			@Override
			public IObservableMap<K, V> createObservable(S target) {
				return observe(target);
			}
		};
