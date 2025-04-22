	public IObservableList<E> observe(Realm realm, S source) {
		List<IObservableList<E>> lists = new ArrayList<IObservableList<E>>(
				properties.length);
		for (int i = 0; i < properties.length; i++) {
			lists.add(properties[i].observe(realm, source));
		}
		IObservableList<E> multiList = new MultiList<E>(lists, elementType);
