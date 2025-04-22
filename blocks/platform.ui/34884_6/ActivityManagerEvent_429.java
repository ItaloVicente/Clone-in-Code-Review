
package org.eclipse.ui.activities;

public final class ActivityEvent {
    private IActivity activity;

    private boolean activityRequirementBindingsChanged;

    private boolean activityPatternBindingsChanged;

    private boolean definedChanged;

    private boolean enabledChanged;
    
    private boolean defaultEnabledChanged;

    private boolean nameChanged;

    private boolean descriptionChanged;

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

    public ActivityEvent(IActivity activity,
            boolean activityRequirementBindingsChanged,
            boolean activityPatternBindingsChanged, boolean definedChanged,
            boolean descriptionChanged, boolean enabledChanged,
            boolean nameChanged,
            boolean defaultEnabledChanged) {
        if (activity == null) {
			throw new NullPointerException();
		}

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
}
