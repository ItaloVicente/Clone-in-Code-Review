	private Bundle getBundle(URI uri) throws BundleException {
		Bundle bundle = CSSActivator.getDefault().getBundleForName(uri.lastSegment());
		if (bundle != null && (bundle.getState() & Bundle.ACTIVE) == 0) {
			bundle.start(); // Bundle is lazy init
		}
		return bundle;
