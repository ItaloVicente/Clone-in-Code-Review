	@Override
	public Stream<E> stream() {
		getterCalled();
		return IObservableList.super.stream();
	}

	@Override
	public Spliterator<E> spliterator() {
		getterCalled();
		return IObservableList.super.spliterator();
	}

	@Override
	public Stream<E> parallelStream() {
		getterCalled();
		return IObservableList.super.parallelStream();
	}

