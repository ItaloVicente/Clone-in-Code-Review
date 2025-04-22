	public static <S, E> List<IObservableMap<E, ?>> observeEach(IObservableSet<E> domainSet,
			List<IValueProperty<? super E, ?>> properties) {
		List<IObservableMap<E, ?>> maps = new ArrayList<IObservableMap<E, ?>>(properties.size());
		for (IValueProperty<? super E, ?> property : properties) {
			maps.add(property.observeDetail(domainSet));
		}
		return maps;
	}

	public static <K, V> List<IObservableMap<K, ?>> observeEach(IObservableMap<K, V> domainMap,
			List<IValueProperty<? super V, ?>> properties) {
		List<IObservableMap<K, ?>> maps = new ArrayList<IObservableMap<K, ?>>(properties.size());
		for (IValueProperty<? super V, ?> property : properties) {
			maps.add(property.observeDetail(domainMap));
		}
		return maps;
	}

