	public static Object getValue(Object object) {
		while (object instanceof Container) {
			object = ((Container) object).getValue();
		}
		return object;
	}
