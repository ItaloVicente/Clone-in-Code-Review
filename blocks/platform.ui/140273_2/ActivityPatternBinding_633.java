		this.activityId = activityId;
		this.isEqualityPattern = isEqualityPattern;
		if (isEqualityPattern) {
			this.patternString = pattern;
			this.pattern = null;
		} else {
			this.patternString = null;
			this.pattern = Pattern.compile(pattern);
		}
	}

	public ActivityPatternBinding(String activityId, Pattern pattern) {
		if (pattern == null) {
