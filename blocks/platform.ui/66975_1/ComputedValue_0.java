	public static <T> IObservableValue<T> create(Supplier<T> supplier) {
		return new ComputedValue<T>() {

			@Override
			protected T calculate() {
				return supplier.get();
			}
		};
	}

