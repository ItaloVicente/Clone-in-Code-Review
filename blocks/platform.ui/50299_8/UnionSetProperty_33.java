	public IObservableSet<E> observe(Realm realm, S source) {
		Set<IObservableSet<? extends E>> sets = new HashSet<IObservableSet<? extends E>>(
				properties.length);
		for (ISetProperty<S, E> property : properties) {
			sets.add(property.observe(realm, source));
		}
		IObservableSet<E> unionSet = new UnionSet<>(sets, elementType);
