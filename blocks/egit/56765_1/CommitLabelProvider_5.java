	private void setRelativeDate(boolean relative) {
		if (dateFormatter.get() instanceof PreferenceBasedDateFormatter) {
			if (relative) {
				setDateFormatter(
						new GitDateFormatter(GitDateFormatter.Format.RELATIVE));
			}
		} else if (!relative) {
			setDateFormatter(new PreferenceBasedDateFormatter());
		}
