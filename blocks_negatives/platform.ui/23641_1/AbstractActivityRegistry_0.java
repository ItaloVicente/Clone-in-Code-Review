                ((IActivityRegistryListener) activityRegistryListeners.get(i))
                        .activityRegistryChanged(activityRegistryEvent);
            }
        }
    }

    public List getActivityRequirementBindingDefinitions() {
        return activityRequirementBindingDefinitions;
    }

    public List getActivityDefinitions() {
        return activityDefinitions;
    }

    public List getActivityPatternBindingDefinitions() {
        return activityPatternBindingDefinitions;
    }

    public List getCategoryActivityBindingDefinitions() {
        return categoryActivityBindingDefinitions;
    }

    public List getCategoryDefinitions() {
        return categoryDefinitions;
    }

    public void removeActivityRegistryListener(
            IActivityRegistryListener activityRegistryListener) {
        if (activityRegistryListener == null) {
