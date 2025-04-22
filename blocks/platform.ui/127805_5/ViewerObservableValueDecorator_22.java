
	@SuppressWarnings("unchecked")
	@Override
	public <T2> IViewerObservableValue<T2> cast(Class<T2> targetType) {
		return (IViewerObservableValue<T2>) this;
	}
