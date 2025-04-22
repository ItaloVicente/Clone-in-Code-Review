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
