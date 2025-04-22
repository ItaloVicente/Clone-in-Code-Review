	    Set defaultEnabled = new HashSet();
	    Set activityIds = activitySupport.getDefinedActivityIds();
	    for (Iterator i = activityIds.iterator(); i.hasNext();) {
            String activityId = (String) i.next();
            IActivity activity = activitySupport.getActivity(activityId);
            try {
                if (activity.isDefaultEnabled()) {
                    defaultEnabled.add(activityId);
                }
            } catch (NotDefinedException e) {
            }
        }
