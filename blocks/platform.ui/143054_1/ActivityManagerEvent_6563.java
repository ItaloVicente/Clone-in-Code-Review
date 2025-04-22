		if (definedActivityIdsChanged) {
			this.previouslyDefinedActivityIds = Util.safeCopy(previouslyDefinedActivityIds, String.class);
		} else {
			this.previouslyDefinedActivityIds = null;
		}

		if (definedCategoryIdsChanged) {
			this.previouslyDefinedCategoryIds = Util.safeCopy(previouslyDefinedCategoryIds, String.class);
		} else {
			this.previouslyDefinedCategoryIds = null;
		}

		if (enabledActivityIdsChanged) {
			this.previouslyEnabledActivityIds = Util.safeCopy(previouslyEnabledActivityIds, String.class);
		} else {
			this.previouslyEnabledActivityIds = null;
		}

		this.activityManager = activityManager;
		this.definedActivityIdsChanged = definedActivityIdsChanged;
		this.definedCategoryIdsChanged = definedCategoryIdsChanged;
		this.enabledActivityIdsChanged = enabledActivityIdsChanged;
	}

	public IActivityManager getActivityManager() {
		return activityManager;
	}

	public Set<String> getPreviouslyDefinedActivityIds() {
		return previouslyDefinedActivityIds;
	}

	public Set<String> getPreviouslyDefinedCategoryIds() {
		return previouslyDefinedCategoryIds;
	}

	public Set<String> getPreviouslyEnabledActivityIds() {
		return previouslyEnabledActivityIds;
	}

	public boolean haveDefinedActivityIdsChanged() {
		return definedActivityIdsChanged;
	}

	public boolean haveDefinedCategoryIdsChanged() {
		return definedCategoryIdsChanged;
	}

	public boolean haveEnabledActivityIdsChanged() {
		return enabledActivityIdsChanged;
	}
