	protected Set<String> buildDependencies(IActivityManager activityManager, String activityId) {
		Set<String> set = new HashSet<>();
		for (Iterator<String> i = activityManager.getDefinedActivityIds().iterator(); i.hasNext();) {
            IActivity activity = activityManager.getActivity(i.next());
			for (Iterator<IActivityRequirementBinding> j = activity.getActivityRequirementBindings().iterator(); j
					.hasNext();) {
                IActivityRequirementBinding binding = j.next();
