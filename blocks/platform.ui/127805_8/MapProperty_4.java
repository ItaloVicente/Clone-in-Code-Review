
	@SuppressWarnings("unchecked")
	@Override
	public <S2> IMapProperty<S2, K, V> castSource(Class<S2> targetType) {
		return (IMapProperty<S2, K, V>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K2, V2> IMapProperty<S, K2, V2> castEntries(Class<K2> targetKeyType, Class<V2> targetValueType) {
		return (IMapProperty<S, K2, V2>) this;
	}
