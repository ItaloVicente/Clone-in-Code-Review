			if (!saving && event.getProperty().startsWith(PREFIX)) {
				String activityId = event.getProperty().substring(PREFIX.length());
				IWorkbenchActivitySupport support = PlatformUI.getWorkbench().getActivitySupport();
				IActivityManager activityManager = support.getActivityManager();

				boolean enabled = Boolean.parseBoolean(event.getNewValue().toString());
