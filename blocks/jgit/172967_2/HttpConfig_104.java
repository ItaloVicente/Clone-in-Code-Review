	private int findLastEmpty(String[] values) {
		for (int i = values.length - 1; i >= 0; i--) {
			if (values[i] == null) {
				return i;
			}
		}
		return -1;
	}

