
	public static <S, E> IListProperty<S, E> convertedList(Object elementType,
			Function<? super S, ? extends List<E>> converter) {
		Objects.requireNonNull(converter);
		return new ConvertedListProperty<>(elementType, converter);
	}

	public static <S, E> IListProperty<S, E> convertedList(Function<? super S, ? extends List<E>> converter) {
		return convertedList(null, converter);
	}

	public static <S, E> ISetProperty<S, E> convertedSet(Object elementType,
			Function<? super S, ? extends Set<E>> converter) {
		Objects.requireNonNull(converter);
		return new ConvertedSetProperty<>(elementType, converter);
	}

	public static <S, E> ISetProperty<S, E> convertedSet(Function<? super S, ? extends Set<E>> converter) {
		return convertedSet(null, converter);
	}

	public static <S, K, V> IMapProperty<S, K, V> convertedMap(Object keyType, Object valueType,
			Function<? super S, ? extends Map<K, V>> converter) {
		Objects.requireNonNull(converter);
		return new ConvertedMapProperty<>(keyType, valueType, converter);
	}

	public static <S, K, V> IMapProperty<S, K, V> convertedMap(
			Function<? super S, ? extends Map<K, V>> converter) {
		return convertedMap(null, null, converter);
	}
