    /**
     * Preference prefix - must match the one specified in ActivityPreferenceHelper
     */
    /**
     * The activity id
     */

    /**
     * @param testName
     */
    public ActivityPreferenceTest(String testName) {
        super(testName);
    }

    /**
     * Tests whether activity preferences are persisted as soon as the activity set changes.
     */
    public void testActivityPreference() {
        IActivityManager manager = fWorkbench.getActivitySupport().getActivityManager();

        boolean initialState = manager.getEnabledActivityIds().contains(ID);
        IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
        assertEquals(initialState, store.getBoolean(PREFIX + ID));

        fWorkbench.getActivitySupport().setEnabledActivityIds(initialState ? Collections.EMPTY_SET : Collections.singleton(ID));
        assertEquals(!initialState, store.getBoolean(PREFIX + ID));
    }
