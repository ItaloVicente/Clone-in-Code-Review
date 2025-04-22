            if (currentActivity.getId().equals(activityId)) {
                activityDefinitions.remove(currentActivity);
                activityDefinitions.add(new ActivityDefinition(activityId,
                        activityName, currentActivity.getSourceId(),
                        currentActivity.getDescription()));
                fireActivityRegistryChanged();
                return;
            }
        }
    }
