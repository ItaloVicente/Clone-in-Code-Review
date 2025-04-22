	private static String PREFIX = "UIActivities."; //$NON-NLS-1$
	private static String ID = "org.eclipse.ui.PT.A2"; //$NON-NLS-1$

	public ActivityPreferenceTest(String testName) {
		super(testName);
	}

	public void testActivityPreference() {
		IActivityManager manager = fWorkbench.getActivitySupport().getActivityManager();

		boolean initialState = manager.getEnabledActivityIds().contains(ID);
		IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
		assertEquals(initialState, store.getBoolean(PREFIX + ID));

		fWorkbench.getActivitySupport().setEnabledActivityIds(initialState ? Collections.EMPTY_SET : Collections.singleton(ID));
		assertEquals(!initialState, store.getBoolean(PREFIX + ID));
	}
