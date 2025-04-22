	/**
	 * Checks if the given {@link EClass} is marked as "deprecated".
	 * 
	 * <p>
	 * The method will check if the {@link EClass} was annotated with
	 * will be logged via the {@link LogService}.
	 * </p>
	 * 
	 * @param eClass
	 *            the class to check
	 */
	private static void checkDeprecation(EClass eClass) {
		if (eClass == null)
			return;

		EAnnotation deprecated = eClass
		if (deprecated != null) {

			if (since != null) {
			}

			Activator.log(LogService.LOG_WARNING, sb.toString());
		}
	}

