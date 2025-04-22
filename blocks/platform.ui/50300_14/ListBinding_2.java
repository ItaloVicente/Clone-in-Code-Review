
	@SuppressWarnings("unchecked")
	@Override
	public IObservableList<T> getTarget() {
		return (IObservableList<T>) super.getTarget();
	}

	@SuppressWarnings("unchecked")
	@Override
	public IObservableList<M> getModel() {
		return (IObservableList<M>) super.getModel();
	}
