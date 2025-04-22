    /**
     * Return the system activity manager.
     *
     * @return the system activity manager
     */
    private IActivityManager getActivityManager() {
        return  PlatformUI.getWorkbench()
        .getActivitySupport().getActivityManager();
    }

    /**
     * Tests non-regular Expression Pattern bindings.
     */
    public void testNonRegExpressionPattern() {
    	final String ACTIVITY_NON_REG_EXP = "org.eclipse.activityNonRegExp";

    	IActivityManager manager = getActivityManager();
    	IActivity activity = manager.getActivity(ACTIVITY_NON_REG_EXP);
