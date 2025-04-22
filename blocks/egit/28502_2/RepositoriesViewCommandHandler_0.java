
	static <T> T getFirstOrNull(List<T> list) {
		if (list.isEmpty())
			return null;
		return list.get(0);
	}
