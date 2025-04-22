
	@Override
	public <T2> IBeanValueProperty<S, T2> castValue(Class<T2> targetType) {
		return (IBeanValueProperty<S, T2>) super.castValue(targetType);
	}

