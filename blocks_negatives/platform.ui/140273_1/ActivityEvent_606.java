        this.activity = activity;
        this.activityRequirementBindingsChanged = activityRequirementBindingsChanged;
        this.activityPatternBindingsChanged = activityPatternBindingsChanged;
        this.definedChanged = definedChanged;
        this.enabledChanged = enabledChanged;
        this.nameChanged = nameChanged;
        this.descriptionChanged = descriptionChanged;
        this.defaultEnabledChanged = defaultEnabledChanged;
    }


    /**
     * Returns the instance of the interface that changed.
     *
     * @return the instance of the interface that changed. Guaranteed not to be
     *         <code>null</code>.
     */
    public IActivity getActivity() {
        return activity;
    }

    /**
     * Returns whether or not the defined property changed.
     *
     * @return <code>true</code>, iff the defined property changed.
     */
    public boolean hasDefinedChanged() {
        return definedChanged;
    }

    /**
     * Returns whether or not the enabled property changed.
     *
     * @return <code>true</code>, iff the enabled property changed.
     */
    public boolean hasEnabledChanged() {
        return enabledChanged;
    }

    /**
     * Returns whether or not the default enabled property changed.
     *
     * @return <code>true</code>, iff the default enabled property changed.
     * @since 3.1
     */
    public boolean hasDefaultEnabledChanged() {
        return defaultEnabledChanged;
    }

    /**
     * Returns whether or not the name property changed.
     *
     * @return <code>true</code>, iff the name property changed.
     */
    public boolean hasNameChanged() {
        return nameChanged;
    }

    /**
     * Returns whether or not the description property changed.
     *
     * @return <code>true</code>, iff the description property changed.
     */
    public boolean hasDescriptionChanged() {
        return descriptionChanged;
    }

    /**
     * Returns whether or not the activityRequirementBindings property changed.
     *
     * @return <code>true</code>, iff the activityRequirementBindings property changed.
     */
    public boolean haveActivityRequirementBindingsChanged() {
        return activityRequirementBindingsChanged;
    }

    /**
     * Returns whether or not the activityPatternBindings property changed.
     *
     * @return <code>true</code>, iff the activityPatternBindings property changed.
     */
    public boolean haveActivityPatternBindingsChanged() {
        return activityPatternBindingsChanged;
    }
