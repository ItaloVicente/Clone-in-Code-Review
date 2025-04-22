
	@Override
	@SuppressWarnings("unchecked")
	public <T2> IObservableValue<T2> cast(Class<T2> targetType) {
		return (IObservableValue<T2>) this;
	}
