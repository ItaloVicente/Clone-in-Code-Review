

	public static <S, D> UpdateSetStrategy<S, D> create(IConverter<S, D> converter) {
		Objects.requireNonNull(converter);
		return new UpdateSetStrategy<S, D>().setConverter(converter);
	}
