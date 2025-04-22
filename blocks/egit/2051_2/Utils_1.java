	public static <T> T[] copyOf(T[] source) {
		int length = source.length;
		@SuppressWarnings("unchecked")
		T[] result = (T[]) newInstance(source.getClass()
				.getComponentType(), length);
		arraycopy(source, 0, result, 0, length);

		return result;
	}

