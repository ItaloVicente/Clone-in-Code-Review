
	public static <S, T> IValueProperty<S, T> convertedValue(Object valueType,
			Function<? super S, ? extends T> converter) {
		Objects.requireNonNull(converter);
		return new ConvertedValueProperty<>(valueType, converter);
	}

	public static <S, T> IValueProperty<S, T> convertedValue(Function<? super S, ? extends T> converter) {
		return new ConvertedValueProperty<>(null, converter);
	}
