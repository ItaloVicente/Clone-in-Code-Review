    protected Set buildDependencies(IActivityManager activityManager, String activityId) {
        Set set = new HashSet();
        for (Iterator i = activityManager.getDefinedActivityIds().iterator(); i.hasNext(); ) {
            IActivity activity = activityManager.getActivity((String) i.next());
            for (Iterator j = activity.getActivityRequirementBindings().iterator(); j.hasNext(); ) {
                IActivityRequirementBinding binding = (IActivityRequirementBinding) j.next();
