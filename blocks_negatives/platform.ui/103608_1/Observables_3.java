		return new IObservableFactory<K, IObservableValue<V>>() {
			@Override
			public IObservableValue<V> createObservable(K key) {
				return observeMapEntry(map, key, valueType);
			}
		};
