            if (!saving && event.getProperty().startsWith(PREFIX)) {
                String activityId = event.getProperty().substring(PREFIX.length());
                IWorkbenchActivitySupport support = PlatformUI.getWorkbench().getActivitySupport();
                IActivityManager activityManager = support.getActivityManager();

                boolean enabled = Boolean.valueOf(event.getNewValue().toString()).booleanValue();
                Set set = new HashSet(activityManager.getEnabledActivityIds());
                if (enabled == false) {
                    Set dependencies = buildDependencies(activityManager, activityId);
                    set.removeAll(dependencies);
                }
                else {
                    set.add(activityId);
                }
                support.setEnabledActivityIds(set);
            }
        }
    };

    /**
     * Whether we are currently saving the state of activities to the preference
     * store.
     */
    protected boolean saving = false;

    /**
     * Get the singleton instance of this class.
     *
     * @return the singleton instance of this class.
     */
    public static ActivityPersistanceHelper getInstance() {
        if (singleton == null) {
            singleton = new ActivityPersistanceHelper();
        }
        return singleton;
    }

    /**
     * Returns a set of activity IDs that depend on the provided ID in order to be enabled.
     *
     * @param activityManager the activity manager to query
     * @param activityId the activity whos dependencies should be added
     * @return a set of activity IDs
     */
    protected Set buildDependencies(IActivityManager activityManager, String activityId) {
        Set set = new HashSet();
        for (Iterator i = activityManager.getDefinedActivityIds().iterator(); i.hasNext(); ) {
            IActivity activity = activityManager.getActivity((String) i.next());
            for (Iterator j = activity.getActivityRequirementBindings().iterator(); j.hasNext(); ) {
                IActivityRequirementBinding binding = (IActivityRequirementBinding) j.next();
                if (activityId.equals(binding.getRequiredActivityId())) {
                    set.addAll(buildDependencies(activityManager, activity.getId()));
                }
            }
        }
        set.add(activityId);
        return set;
    }

    /**
     * Create a new <code>ActivityPersistanceHelper</code> which will restore
     * previously enabled activity states.
     */
    private ActivityPersistanceHelper() {
        loadEnabledStates();
        hookListeners();
    }

    /**
     * Hook the listener that will respond to any activity state changes.
     */
    private void hookListeners() {
        IWorkbenchActivitySupport support = PlatformUI.getWorkbench()
                .getActivitySupport();

        IActivityManager activityManager = support.getActivityManager();

        activityManager.addActivityManagerListener(activityManagerListener);

        IPreferenceStore store = WorkbenchPlugin.getDefault()
                .getPreferenceStore();

        store.addPropertyChangeListener(propertyChangeListener);
    }

    /**
     * Hook the listener that will respond to any activity state changes.
     */
    private void unhookListeners() {
        IWorkbenchActivitySupport support = PlatformUI.getWorkbench()
                .getActivitySupport();

        IActivityManager activityManager = support.getActivityManager();

        activityManager.removeActivityManagerListener(activityManagerListener);

        IPreferenceStore store = WorkbenchPlugin.getDefault()
                .getPreferenceStore();

        store.removePropertyChangeListener(propertyChangeListener);
    }

    /**
     * Create the preference key for the activity.
     *
     * @param activityId the activity id.
     * @return String a preference key representing the activity.
     */
    private String createPreferenceKey(String activityId) {
        return PREFIX + activityId;
    }

    /**
     * Loads the enabled states from the preference store.
     */
    void loadEnabledStates() {
        loadEnabledStates(Collections.EMPTY_SET, PlatformUI.getWorkbench()
                .getActivitySupport().getActivityManager()
                .getDefinedActivityIds());
    }

    /**
     * Load the enabled states for the given activity IDs.
     *
     * @param previouslyEnabledActivities the activity states to maintain.  This set must be writabe.
     * @param activityIdsToProcess the activity ids to process
     */
    protected void loadEnabledStates(Set previouslyEnabledActivities, Set activityIdsToProcess) {
        if (activityIdsToProcess.isEmpty()) {
