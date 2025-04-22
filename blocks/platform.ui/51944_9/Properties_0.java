	public static <K, V> List<IObservableMap<K, ?>> observeEach(IObservableMap<K, V> domainMap,
			List<IValueProperty<? super V, ?>> properties) {
		List<IObservableMap<K, ?>> maps = new ArrayList<IObservableMap<K, ?>>(properties.size());
		for (IValueProperty<? super V, ?> property : properties) {
			maps.add(property.observeDetail(domainMap));
		}
