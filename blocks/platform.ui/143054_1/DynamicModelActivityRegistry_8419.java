	public void addActivityPatternBinding(String activityId, String pattern) {
		if (activityPatternBindingDefinitions
				.add(new ActivityPatternBindingDefinition(activityId, pattern,
						sourceId))) {
			fireActivityRegistryChanged();
			return;
		}
	}
