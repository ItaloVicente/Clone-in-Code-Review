	private static int hashCode(Object[] array) {
		final int prime = 31;
		if (array == null)
			return 0;
		int result = 1;
		for (Object element : array) {
			result = prime * result + (element == null ? 0 : element.hashCode());
		}
		return result;
	}

