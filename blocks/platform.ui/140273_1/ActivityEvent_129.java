		this.activity = activity;
		this.activityRequirementBindingsChanged = activityRequirementBindingsChanged;
		this.activityPatternBindingsChanged = activityPatternBindingsChanged;
		this.definedChanged = definedChanged;
		this.enabledChanged = enabledChanged;
		this.nameChanged = nameChanged;
		this.descriptionChanged = descriptionChanged;
		this.defaultEnabledChanged = defaultEnabledChanged;
	}

	public IActivity getActivity() {
		return activity;
	}

	public boolean hasDefinedChanged() {
		return definedChanged;
	}

	public boolean hasEnabledChanged() {
		return enabledChanged;
	}

	public boolean hasDefaultEnabledChanged() {
		return defaultEnabledChanged;
	}

	public boolean hasNameChanged() {
		return nameChanged;
	}

	public boolean hasDescriptionChanged() {
		return descriptionChanged;
	}

	public boolean haveActivityRequirementBindingsChanged() {
		return activityRequirementBindingsChanged;
	}

	public boolean haveActivityPatternBindingsChanged() {
		return activityPatternBindingsChanged;
	}
