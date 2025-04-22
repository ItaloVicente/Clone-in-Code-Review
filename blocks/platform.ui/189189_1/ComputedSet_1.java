	public static <E> IObservableSet<E> create(Supplier<Set<E>> supplier) {
		Objects.requireNonNull(supplier);
		return new ComputedSet<E>() {
			@Override
			protected Set<E> calculate() {
				return supplier.get();
			}
		};
	}

