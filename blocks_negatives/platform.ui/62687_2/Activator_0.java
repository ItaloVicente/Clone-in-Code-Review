	public EventAdmin getEventAdmin() {
		if (eventAdminTracker == null) {
			BundleContext bundleContext = plugin.getBundle().getBundleContext();
			if (bundleContext == null)
				return null;
			eventAdminTracker = new ServiceTracker(bundleContext,
					EventAdmin.class.getName(), null);
			eventAdminTracker.open();
		}
		return (EventAdmin) eventAdminTracker.getService();
	}
