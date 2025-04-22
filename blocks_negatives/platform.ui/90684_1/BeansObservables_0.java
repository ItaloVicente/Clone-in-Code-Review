		return new IObservableFactory() {
			@Override
			public IObservable createObservable(Object target) {
				return observeMap((IObservableSet) target, beanClass,
						propertyName);
			}
		};
