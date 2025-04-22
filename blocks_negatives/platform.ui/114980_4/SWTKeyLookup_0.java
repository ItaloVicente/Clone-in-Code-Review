		final Object value = naturalKeyTable.get(name);
		if (value instanceof Integer) {
			return ((Integer) value).intValue();
		}

		if (name.length() > 0) {
