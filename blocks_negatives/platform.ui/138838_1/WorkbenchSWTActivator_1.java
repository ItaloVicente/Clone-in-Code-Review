	/**
	 * @return the PackageAdmin service from this bundle
	 */
	public PackageAdmin getBundleAdmin() {
		if (pkgAdminTracker == null) {
			if (context == null) {
				return null;
			}
			pkgAdminTracker = new ServiceTracker<>(context, PackageAdmin.class, null);
			pkgAdminTracker.open();
		}
		return pkgAdminTracker.getService();
	}

