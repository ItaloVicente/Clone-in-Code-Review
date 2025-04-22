	public PackageAdmin getBundleAdmin() {
		if (pkgAdminTracker == null) {
			if (context == null) {
				return null;
			}
			pkgAdminTracker = new ServiceTracker<PackageAdmin, PackageAdmin>(
					context, PackageAdmin.class.getName(), null);
			pkgAdminTracker.open();
		}
		return pkgAdminTracker.getService();
	}

	/**
	 * @param bundleName
	 *            the bundle id
	 * @return A bundle if found, or <code>null</code>
	 */
	public Bundle getBundleForName(String bundleName) {
		Bundle[] bundles = getBundleAdmin().getBundles(bundleName, null);
		if (bundles == null) {
			return null;
		}
		for (Bundle bundle : bundles) {
			if ((bundle.getState() & (Bundle.INSTALLED | Bundle.UNINSTALLED)) == 0) {
				return bundle;
			}
		}
		return null;
	}

