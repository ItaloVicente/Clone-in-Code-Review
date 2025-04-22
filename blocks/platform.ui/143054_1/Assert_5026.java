	public static boolean isTrue(boolean expression) {
		if (expression) {
			return true;
		}
		return isTrue(expression, "");//$NON-NLS-1$
	}
