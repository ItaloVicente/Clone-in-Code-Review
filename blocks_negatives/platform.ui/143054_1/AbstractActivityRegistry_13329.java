                ((IActivityRegistryListener) activityRegistryListeners.get(i))
                        .activityRegistryChanged(activityRegistryEvent);
            }
        }
    }

    @Override
	public List getActivityRequirementBindingDefinitions() {
        return activityRequirementBindingDefinitions;
    }

    @Override
	public List getActivityDefinitions() {
        return activityDefinitions;
    }

    @Override
	public List getActivityPatternBindingDefinitions() {
        return activityPatternBindingDefinitions;
    }

    @Override
	public List getCategoryActivityBindingDefinitions() {
        return categoryActivityBindingDefinitions;
    }

    @Override
	public List getCategoryDefinitions() {
        return categoryDefinitions;
    }

    @Override
	public void removeActivityRegistryListener(
            IActivityRegistryListener activityRegistryListener) {
        if (activityRegistryListener == null) {
