	/**
	 * Appends array2 to the end of array1 and returns the result
	 *
	 * @param array1
	 * @param array2
	 * @return
	 * @since 3.1
	 */
	public static Object[] appendArray(Object[] array1, Object[] array2) {
		Object[] result = new Object[array1.length + array2.length];
		System.arraycopy(array1, 0, result, 0, array1.length);
		System.arraycopy(array2, 0, result, array1.length, array2.length);
		return result;
	}

