
	@SuppressWarnings("unchecked")
	default <T> List<T> toCheckedList(Class<T> type) {
		return Collections.<T>checkedList(toList(), type);
	}

	@Override
	default Stream stream() {
		if (isEmpty()) {
			return Stream.empty();
		}
		return Arrays.stream(toArray());
	}

