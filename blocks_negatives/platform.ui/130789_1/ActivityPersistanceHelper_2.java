	        IPreferenceStore store = WorkbenchPlugin.getDefault()
	                .getPreferenceStore();

	        IWorkbenchActivitySupport support = PlatformUI.getWorkbench()
	                .getActivitySupport();
	        IActivityManager activityManager = support.getActivityManager();
	        Iterator values = activityManager.getDefinedActivityIds().iterator();
	        while (values.hasNext()) {
	            IActivity activity = activityManager.getActivity((String) values
	                    .next());
	            if (activity.getExpression() != null) {
	            	continue;
	            }

	            store.setValue(createPreferenceKey(activity.getId()), activity
	                    .isEnabled());
	        }
