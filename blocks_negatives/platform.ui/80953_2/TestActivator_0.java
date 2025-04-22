	public PackageAdmin getBundleAdmin() {
		return serviceContext.get(PackageAdmin.class);
	}

	public Bundle getBundleForName(String bundleName) {
		Bundle[] bundles = getBundleAdmin().getBundles(bundleName, null);
		if (bundles == null)
			return null;
		for (int i = 0; i < bundles.length; i++) {
			if ((bundles[i].getState() & (Bundle.INSTALLED | Bundle.UNINSTALLED)) == 0) {
				return bundles[i];
			}
		}
		return null;
	}
