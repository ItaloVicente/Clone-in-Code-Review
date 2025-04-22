        Set defaultEnabled = new HashSet();
        Set activityIds = workingCopy.getDefinedActivityIds();
        for (Iterator i = activityIds.iterator(); i.hasNext();) {
            String activityId = (String) i.next();
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
