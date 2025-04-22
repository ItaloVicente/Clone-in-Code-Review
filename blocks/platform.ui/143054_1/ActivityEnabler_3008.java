		Set<String> defaultEnabled = new HashSet<>();
		Set<String> activityIds = activitySupport.getDefinedActivityIds();
		for (Iterator<String> i = activityIds.iterator(); i.hasNext();) {
			String activityId = i.next();
			IActivity activity = activitySupport.getActivity(activityId);
			try {
				if (activity.isDefaultEnabled()) {
					defaultEnabled.add(activityId);
				}
			} catch (NotDefinedException e) {
			}
		}
