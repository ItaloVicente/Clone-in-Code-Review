	public static void isNotNull(Object object) {
		if (object != null) {
			return;
		}
		isNotNull(object, "");//$NON-NLS-1$
	}
