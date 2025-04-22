	 * Returns <code>true</code> if there is no applicable activity for the
	 * given identifier. Otherwise, if the boolean argument <code>
	 * disabledExpressionActivitiesTakePrecedence</code> is
	 * <code>false</code>, returns true if any of the applicable activities
	 * is enabled. If the boolean argument is <code>true</code>, this method
	 * returns <code>false</code> if there is at least one disabled
	 * expression-based activity; and it returns <code>true</code> if there
	 * are no disabled expression-based activities and there is at least one
	 * applicable activity that is enabled.
	 * @param activityManager the activity manager
	 * @param identifier
	 *            the identifier to update
