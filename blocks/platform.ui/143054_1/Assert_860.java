	public static boolean isLegal(boolean expression) {
		if (expression) {
			return true;
		}
		return isLegal(expression, "");//$NON-NLS-1$
	}
