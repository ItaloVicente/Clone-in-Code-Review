
	@Override
	public <M> IValueProperty<S, M> value(Function<? super T, M> detailGetter) {
		return value(Properties.convertedValue(detailGetter));
	}

	@Override
	public <M> IListProperty<S, M> list(Function<? super T, List<M>> detailGetter) {
		return list(Properties.convertedList(detailGetter));
	}

	@Override
	public <M> ISetProperty<S, M> set(Function<? super T, Set<M>> detailGetter) {
		return set(Properties.convertedSet(detailGetter));
	}

	@Override
	public <K, V> IMapProperty<S, K, V> map(Function<? super T, Map<K, V>> detailGetter) {
		return map(Properties.convertedMap(detailGetter));
	}
