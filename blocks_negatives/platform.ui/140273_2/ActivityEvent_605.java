    private IActivity activity;

    private boolean activityRequirementBindingsChanged;

    private boolean activityPatternBindingsChanged;

    private boolean definedChanged;

    private boolean enabledChanged;

    private boolean defaultEnabledChanged;

    private boolean nameChanged;

    private boolean descriptionChanged;

    /**
     * Creates a new instance of this class. Since 3.1 this method will assume
     * that the default enabled state has not changed.
     *
     * @param activity
     *            the instance of the interface that changed.
     * @param activityRequirementBindingsChanged
     *            <code>true</code>, iff the activityRequirementBindings
     *            property changed.
     * @param activityPatternBindingsChanged
     *            <code>true</code>, iff the activityPatternBindings property
     *            changed.
     * @param definedChanged
     *            <code>true</code>, iff the defined property changed.
     * @param descriptionChanged
     *            <code>true</code>, iff the description property changed.
     * @param enabledChanged
     *            <code>true</code>, iff the enabled property changed.
     * @param nameChanged
     *            <code>true</code>, iff the name property changed.
     */
    public ActivityEvent(IActivity activity,
            boolean activityRequirementBindingsChanged,
            boolean activityPatternBindingsChanged, boolean definedChanged,
            boolean descriptionChanged, boolean enabledChanged,
            boolean nameChanged) {

        this(activity,
                activityRequirementBindingsChanged,
                activityPatternBindingsChanged,
                definedChanged,
                descriptionChanged,
                enabledChanged,
                nameChanged,
                false);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param activity
     *        the instance of the interface that changed.
     * @param activityRequirementBindingsChanged
     *        <code>true</code>, iff the activityRequirementBindings property changed.
     * @param activityPatternBindingsChanged
     *        <code>true</code>, iff the activityPatternBindings property changed.
     * @param definedChanged
     *        <code>true</code>, iff the defined property changed.
     * @param descriptionChanged
     * 		  <code>true</code>, iff the description property changed.
     * @param enabledChanged
     *      <code>true</code>, iff the enabled property changed.
     * @param nameChanged
     *        <code>true</code>, iff the name property changed.
     * @param defaultEnabledChanged
     * 		  <code>true</code>, iff the default enabled property changed.
     * @since 3.1
     */
    public ActivityEvent(IActivity activity,
            boolean activityRequirementBindingsChanged,
            boolean activityPatternBindingsChanged, boolean definedChanged,
            boolean descriptionChanged, boolean enabledChanged,
            boolean nameChanged,
            boolean defaultEnabledChanged) {
        if (activity == null) {
