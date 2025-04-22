	private String getActivityText(IActivity activity) {
		try {
			return activity.getName();
		} catch (NotDefinedException e) {
			return activity.getId();
		}
	}
