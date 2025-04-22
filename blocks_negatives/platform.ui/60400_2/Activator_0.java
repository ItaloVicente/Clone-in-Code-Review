	/**
	 * @return the instance Location service
	 */
	public Location getInstanceLocation() {
		if (locationTracker == null) {
			Filter filter = null;
			try {
				filter = context.createFilter(Location.INSTANCE_FILTER);
			} catch (InvalidSyntaxException e) {
			}
			locationTracker = new ServiceTracker<>(context, filter, null);
			locationTracker.open();
		}
		return locationTracker.getService();
	}

