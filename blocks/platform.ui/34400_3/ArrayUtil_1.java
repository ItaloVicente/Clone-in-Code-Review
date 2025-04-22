	public static boolean contains(Object[] array, Object element) {
		if (array == null || element == null)
			return false;
		for (int i = 0; i < array.length; i++)
			if (array[i] == element)
				return true;
		return false;
	}
