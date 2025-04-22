
	@Override
	public Stream<E> stream() {
		getterCalled();
		return decorated.stream();
	}

	@Override
	public Spliterator<E> spliterator() {
		getterCalled();
		return decorated.spliterator();
	}

	@Override
	public Stream<E> parallelStream() {
		getterCalled();
		return decorated.parallelStream();
	}
