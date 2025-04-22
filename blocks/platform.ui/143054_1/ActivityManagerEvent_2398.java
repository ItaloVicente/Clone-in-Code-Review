	private IActivityManager activityManager;

	private boolean definedActivityIdsChanged;

	private boolean definedCategoryIdsChanged;

	private boolean enabledActivityIdsChanged;

	private final Set<String> previouslyDefinedActivityIds;

	private final Set<String> previouslyDefinedCategoryIds;

	private final Set<String> previouslyEnabledActivityIds;

	public ActivityManagerEvent(IActivityManager activityManager, boolean definedActivityIdsChanged,
			boolean definedCategoryIdsChanged, boolean enabledActivityIdsChanged,
			final Set<String> previouslyDefinedActivityIds, final Set<String> previouslyDefinedCategoryIds,
			final Set<String> previouslyEnabledActivityIds) {
		if (activityManager == null) {
