
	@Override
	public Stream<E> stream() {
		getterCalled();
		return IObservableSet.super.stream();
	}

	@Override
	public Spliterator<E> spliterator() {
		getterCalled();
		return IObservableSet.super.spliterator();
	}

	@Override
	public Stream<E> parallelStream() {
		getterCalled();
		return IObservableSet.super.parallelStream();
	}
