	/**
	 * @return the PackageAdmin service from this bundle
	 */
	public PackageAdmin getBundleAdmin() {
		if (pkgAdminTracker == null) {
			if (context == null)
				return null;
			pkgAdminTracker = new ServiceTracker(context, PackageAdmin.class.getName(), null);
			pkgAdminTracker.open();
		}
		return (PackageAdmin) pkgAdminTracker.getService();
	}

