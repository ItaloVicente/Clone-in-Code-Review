	public static boolean isCollection(Object value) {
		value = getValue(value);
		if (value == null) {
			return false;
		}
		if (value.getClass().isArray()) {
			return true;
		}
		if (value instanceof Collection) {
			return true;
		}
		return false;
	}
