	public static Bundle installBundle(String pluginLocation)
			throws BundleException, IllegalStateException {
		Bundle target = context.installBundle(pluginLocation);
		int state = target.getState();
		if (state != Bundle.INSTALLED) {
			throw new IllegalStateException("Bundle " + target
					+ " is in a wrong state: " + state);
		}
		refreshPackages(new Bundle[] { target });
		return target;
	}
