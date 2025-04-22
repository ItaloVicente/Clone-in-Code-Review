	public static ObjectActionContributorManager getManager() {
		if (sharedInstance == null) {
			sharedInstance = new ObjectActionContributorManager();
		}
		return sharedInstance;
	}
