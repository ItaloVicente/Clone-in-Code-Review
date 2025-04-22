
	@SuppressWarnings("unchecked")
	@Override
	public <E2> ISetProperty<S, E2> castElements(Class<E2> targetType) {
		Objects.requireNonNull(targetType);
		return (ISetProperty<S, E2>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S2> ISetProperty<S2, E> castSource(Class<S2> targetType) {
		Objects.requireNonNull(targetType);
		return (ISetProperty<S2, E>) this;
	}

