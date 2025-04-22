
		<T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter);

		default <T> ConvertToModelStep<T, M> convertToTarget(Function<? super M, ? extends T> converter) {
			return convertToTarget(IConverter.create(converter));
		}
