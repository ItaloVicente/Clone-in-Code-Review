
	@SuppressWarnings("unchecked")
	@Override
	public <T2> IValueProperty<S, T2> castValue(Class<T2> cls) {
		return (IValueProperty<S, T2>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S2> IValueProperty<S2, T> castSource(Class<S2> cls) {
		return (IValueProperty<S2, T>) this;
	}

