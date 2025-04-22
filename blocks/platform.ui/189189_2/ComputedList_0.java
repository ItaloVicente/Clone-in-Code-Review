	public static <E> IObservableList<E> create(Supplier<List<E>> supplier) {
		Objects.requireNonNull(supplier);
		return new ComputedList<E>() {
			@Override
			protected List<E> calculate() {
				return supplier.get();
			}
		};
	}

