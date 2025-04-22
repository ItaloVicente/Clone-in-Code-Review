	private IActivityManager getActivityManager() {
		return  PlatformUI.getWorkbench()
		.getActivitySupport().getActivityManager();
	}

	public void testNonRegExpressionPattern() {
		final String ACTIVITY_NON_REG_EXP = "org.eclipse.activityNonRegExp";

		IActivityManager manager = getActivityManager();
		IActivity activity = manager.getActivity(ACTIVITY_NON_REG_EXP);
