	private IActivity activity;

	private boolean activityRequirementBindingsChanged;

	private boolean activityPatternBindingsChanged;

	private boolean definedChanged;

	private boolean enabledChanged;

	private boolean defaultEnabledChanged;

	private boolean nameChanged;

	private boolean descriptionChanged;

	public ActivityEvent(IActivity activity, boolean activityRequirementBindingsChanged,
			boolean activityPatternBindingsChanged, boolean definedChanged, boolean descriptionChanged,
			boolean enabledChanged, boolean nameChanged) {

		this(activity, activityRequirementBindingsChanged, activityPatternBindingsChanged, definedChanged,
				descriptionChanged, enabledChanged, nameChanged, false);
	}

	public ActivityEvent(IActivity activity, boolean activityRequirementBindingsChanged,
			boolean activityPatternBindingsChanged, boolean definedChanged, boolean descriptionChanged,
			boolean enabledChanged, boolean nameChanged, boolean defaultEnabledChanged) {
		if (activity == null) {
