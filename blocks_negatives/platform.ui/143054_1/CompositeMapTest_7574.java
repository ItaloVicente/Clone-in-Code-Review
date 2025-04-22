		composedMap = new CompositeMap(first, new IObservableFactory() {
			@Override
			public IObservable createObservable(Object target) {
				return BeansObservables.observeMap((IObservableSet) target,
						SimpleCart.class, "numItems");
			}
		});
