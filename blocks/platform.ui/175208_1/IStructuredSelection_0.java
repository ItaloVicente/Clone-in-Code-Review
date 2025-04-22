
	@SuppressWarnings("unchecked")
	default <T> Stream<T> stream(Class<T> type) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator(), Spliterator.ORDERED), false)
				.filter(type::isInstance).map(type::cast);
	}
