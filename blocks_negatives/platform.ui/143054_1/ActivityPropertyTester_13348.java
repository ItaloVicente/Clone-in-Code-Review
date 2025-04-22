			IWorkbenchActivitySupport workbenchActivitySupport =
					workbench.getActivitySupport();
			IActivityManager activityManager = workbenchActivitySupport
					.getActivityManager();
			return WorkbenchActivityHelper.isEnabled(activityManager,
					categoryId);
