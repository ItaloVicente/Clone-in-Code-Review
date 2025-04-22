
	@SuppressWarnings("unchecked")
	@Override
	public <S2> IMapProperty<S2, K, V> castSource(Class<S2> targetType) {
		return (IMapProperty<S2, K, V>) this;
	}

