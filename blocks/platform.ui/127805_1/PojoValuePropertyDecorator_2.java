
	@Override
	public <T2> IBeanValueProperty<S, T2> castValue(Class<T2> targetType) {
		return (IBeanValueProperty<S, T2>) super.castValue(targetType);
	}

	@Override
	public <S2> IBeanValueProperty<S2, T> castSource(Class<S2> targetType) {
		return (IBeanValueProperty<S2, T>) super.castSource(targetType);
	}

