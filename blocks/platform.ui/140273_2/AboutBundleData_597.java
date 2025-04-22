	public AboutBundleData(Bundle bundle) {
		super(getResourceString(bundle, Constants.BUNDLE_VENDOR), getResourceString(bundle, Constants.BUNDLE_NAME),
				getResourceString(bundle, Constants.BUNDLE_VERSION), bundle.getSymbolicName());

		this.bundle = bundle;

	}

	public int getState() {
		return bundle.getState();
	}

	public String getStateName() {
		switch (getState()) {
		case Bundle.INSTALLED:
			return WorkbenchMessages.AboutPluginsDialog_state_installed;
		case Bundle.RESOLVED:
			return WorkbenchMessages.AboutPluginsDialog_state_resolved;
		case Bundle.STARTING:
			return WorkbenchMessages.AboutPluginsDialog_state_starting;
		case Bundle.STOPPING:
			return WorkbenchMessages.AboutPluginsDialog_state_stopping;
		case Bundle.UNINSTALLED:
			return WorkbenchMessages.AboutPluginsDialog_state_uninstalled;
		case Bundle.ACTIVE:
			return WorkbenchMessages.AboutPluginsDialog_state_active;
		default:
			return WorkbenchMessages.AboutPluginsDialog_state_unknown;
		}
	}

	private static String getResourceString(Bundle bundle, String headerName) {
		String value = bundle.getHeaders().get(headerName);
		return value == null ? null : Platform.getResourceString(bundle, value);
	}

	public boolean isSignedDetermined() {
		return isSignedDetermined;
	}

	public boolean isSigned() throws IllegalStateException {
