	public PlatformAdmin getPlatformAdmin() {
		if (platformAdminTracker == null) {
			if (context == null)
				return null;
			platformAdminTracker = new ServiceTracker<PlatformAdmin, PlatformAdmin>(
					context, PlatformAdmin.class, null);
			platformAdminTracker.open();
		}
		return platformAdminTracker.getService();
	}

