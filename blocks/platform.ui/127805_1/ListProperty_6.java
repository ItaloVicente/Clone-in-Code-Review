
	@SuppressWarnings("unchecked")
	@Override
	public <E2> IListProperty<S, E2> castElements(Class<E2> elemType) {
		return (ListProperty<S, E2>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S2> IListProperty<S2, E> castSource(Class<S2> cls) {
		return (IListProperty<S2, E>) this;
	}

