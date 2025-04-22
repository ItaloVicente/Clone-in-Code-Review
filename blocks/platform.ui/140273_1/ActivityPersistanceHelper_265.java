				IActivityRequirementBinding binding = j.next();
				if (activityId.equals(binding.getRequiredActivityId())) {
					set.addAll(buildDependencies(activityManager, activity.getId()));
				}
			}
		}
		set.add(activityId);
		return set;
	}

	private ActivityPersistanceHelper() {
		loadEnabledStates();
		hookListeners();
	}

	private void hookListeners() {
		IWorkbenchActivitySupport support = PlatformUI.getWorkbench().getActivitySupport();

		IActivityManager activityManager = support.getActivityManager();

		activityManager.addActivityManagerListener(activityManagerListener);

		IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();

		store.addPropertyChangeListener(propertyChangeListener);
	}

	private void unhookListeners() {
		IWorkbenchActivitySupport support = PlatformUI.getWorkbench().getActivitySupport();

		IActivityManager activityManager = support.getActivityManager();

		activityManager.removeActivityManagerListener(activityManagerListener);

		IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();

		store.removePropertyChangeListener(propertyChangeListener);
	}

	private String createPreferenceKey(String activityId) {
		return PREFIX + activityId;
	}

	void loadEnabledStates() {
		loadEnabledStates(Collections.EMPTY_SET,
				PlatformUI.getWorkbench().getActivitySupport().getActivityManager().getDefinedActivityIds());
	}

