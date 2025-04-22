
	<M> IValueProperty<S, M> value(Function<? super T, M> detailGetter);

	<M> IListProperty<S, M> list(Function<? super T, List<M>> detailGetter);

	<M> ISetProperty<S, M> set(Function<? super T, Set<M>> detailGetter);

	<K, V> IMapProperty<S, K, V> map(Function<? super T, Map<K, V>> detailGetter);

