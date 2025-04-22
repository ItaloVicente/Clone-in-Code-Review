			if (currentActivity.getId().equals(activityId)) {
				activityDefinitions.remove(currentActivity);
				activityDefinitions.add(new ActivityDefinition(activityId,
						currentActivity.getName(), currentActivity
								.getSourceId(), activityDescription));
				fireActivityRegistryChanged();
				return;
			}
		}
	}
