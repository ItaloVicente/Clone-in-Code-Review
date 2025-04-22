	    for (String activityId : activityIds) {
		IActivity activity = activitySupport.getActivity(activityId);
		try {
		    if (activity.isDefaultEnabled()) {
			defaultEnabled.add(activityId);
		    }
		} catch (NotDefinedException e) {
