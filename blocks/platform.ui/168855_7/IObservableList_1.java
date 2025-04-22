
	@Override
	public default Stream<E> stream() {
		return IObservableCollection.super.stream();
	}

	@Override
	public default Spliterator<E> spliterator() {
		return IObservableCollection.super.spliterator();
	}

	@Override
	public default Stream<E> parallelStream() {
		return IObservableCollection.super.parallelStream();
	}
