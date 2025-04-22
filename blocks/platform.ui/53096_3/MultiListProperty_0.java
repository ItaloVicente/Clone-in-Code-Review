	public IObservableList<E> observe(Realm realm, S source) {
		List<IObservableList<E>> lists = new ArrayList<>(properties.length);
		for (int i = 0; i < properties.length; i++) {
			lists.add(properties[i].observe(realm, source));
		}
		IObservableList<E> multiList = new MultiList<>(lists, elementType);
