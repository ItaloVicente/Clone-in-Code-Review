			if (!saving && event.getProperty().startsWith(PREFIX)) {
				String activityId = event.getProperty().substring(PREFIX.length());
				IWorkbenchActivitySupport support = PlatformUI.getWorkbench().getActivitySupport();
				IActivityManager activityManager = support.getActivityManager();

				boolean enabled = Boolean.parseBoolean(event.getNewValue().toString());
				Set<String> set = new HashSet<>(activityManager.getEnabledActivityIds());
				if (enabled == false) {
					Set<String> dependencies = buildDependencies(activityManager, activityId);
					set.removeAll(dependencies);
				} else {
					set.add(activityId);
				}
				support.setEnabledActivityIds(set);
			}
		}
	};

	protected boolean saving = false;

	public static ActivityPersistanceHelper getInstance() {
		if (singleton == null) {
			singleton = new ActivityPersistanceHelper();
		}
		return singleton;
	}

	protected Set<String> buildDependencies(IActivityManager activityManager, String activityId) {
		Set<String> set = new HashSet<>();
		for (Iterator<String> i = activityManager.getDefinedActivityIds().iterator(); i.hasNext();) {
			IActivity activity = activityManager.getActivity(i.next());
			for (Iterator<IActivityRequirementBinding> j = activity.getActivityRequirementBindings().iterator(); j
					.hasNext();) {
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

	protected void loadEnabledStates(Set<String> previouslyEnabledActivities, Set<String> activityIdsToProcess) {
		if (activityIdsToProcess.isEmpty()) {
