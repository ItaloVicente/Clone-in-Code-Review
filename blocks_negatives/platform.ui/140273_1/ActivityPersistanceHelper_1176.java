                IActivityRequirementBinding binding = j.next();
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
