		Set<String> defaultEnabled = new HashSet<>();
		Set<String> activityIds = workingCopy.getDefinedActivityIds();
		for (String activityId : activityIds) {
			IActivity activity = workingCopy.getActivity(activityId);
			try {
				if (activity.isDefaultEnabled()) {
					defaultEnabled.add(activityId);
				}
			} catch (NotDefinedException e) {
			}
		}

		workingCopy.setEnabledActivityIds(defaultEnabled);
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) {
		if (data instanceof Hashtable) {
			Hashtable<?, ?> table = (Hashtable<?, ?>) data;
			allowAdvanced = Boolean.parseBoolean((String) table.remove(ALLOW_ADVANCED));
			strings.putAll(table);
		}
	}
