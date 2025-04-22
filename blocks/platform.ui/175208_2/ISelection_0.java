
	default Stream<T> stream() {
		return Stream.empty();
	}

	default <X> Stream<X> filteredStream(Class<X> type) {
		return stream().filter(type::isInstance).map(type::cast);
	}

	static <S> ISelection<S> emptySelection() {
		return new ISelection<S>() {

			@Override
			public boolean isEmpty() {
				return true;
			}
		};
	}
