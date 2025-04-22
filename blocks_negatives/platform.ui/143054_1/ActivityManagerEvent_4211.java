    private IActivityManager activityManager;

    private boolean definedActivityIdsChanged;

    private boolean definedCategoryIdsChanged;

    private boolean enabledActivityIdsChanged;

    /**
     * The set of activity identifiers (strings) that were defined before the
     * change occurred. If the defined activities did not changed, then this
     * value is <code>null</code>.
     */
    private final Set previouslyDefinedActivityIds;

    /**
     * The set of category identifiers (strings) that were defined before the
     * change occurred. If the defined category did not changed, then this value
     * is <code>null</code>.
     */
    private final Set previouslyDefinedCategoryIds;

    /**
     * The set of activity identifiers (strings) that were enabled before the
     * change occurred. If the enabled activities did not changed, then this
     * value is <code>null</code>.
     */
    private final Set previouslyEnabledActivityIds;

    /**
     * Creates a new instance of this class.
     *
     * @param activityManager
     *            the instance of the interface that changed.
     * @param definedActivityIdsChanged
     *            <code>true</code>, iff the definedActivityIds property
     *            changed.
     * @param definedCategoryIdsChanged
     *            <code>true</code>, iff the definedCategoryIds property
     *            changed.
     * @param enabledActivityIdsChanged
     *            <code>true</code>, iff the enabledActivityIds property
     *            changed.
     * @param previouslyDefinedActivityIds
     *            the set of identifiers to previously defined activities. This
     *            set may be empty. If this set is not empty, it must only
     *            contain instances of <code>String</code>. This set must be
     *            <code>null</code> if definedActivityIdsChanged is
     *            <code>false</code> and must not be null if
     *            definedActivityIdsChanged is <code>true</code>.
     * @param previouslyDefinedCategoryIds
     *            the set of identifiers to previously defined category. This
     *            set may be empty. If this set is not empty, it must only
     *            contain instances of <code>String</code>. This set must be
     *            <code>null</code> if definedCategoryIdsChanged is
     *            <code>false</code> and must not be null if
     *            definedCategoryIdsChanged is <code>true</code>.
     * @param previouslyEnabledActivityIds
     *            the set of identifiers to previously enabled activities. This
     *            set may be empty. If this set is not empty, it must only
     *            contain instances of <code>String</code>. This set must be
     *            <code>null</code> if enabledActivityIdsChanged is
     *            <code>false</code> and must not be null if
     *            enabledActivityIdsChanged is <code>true</code>.
     */
    public ActivityManagerEvent(IActivityManager activityManager,
            boolean definedActivityIdsChanged,
            boolean definedCategoryIdsChanged,
            boolean enabledActivityIdsChanged,
            final Set previouslyDefinedActivityIds,
            final Set previouslyDefinedCategoryIds,
            final Set previouslyEnabledActivityIds) {
        if (activityManager == null) {
