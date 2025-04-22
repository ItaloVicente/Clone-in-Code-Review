    private static final String WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME = "org.eclipse.platform"; //$NON-NLS-1$
	private static final Version WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION;
	static {
		Bundle bundle = Platform.getBundle(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME);
		WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION = bundle != null ? bundle.getVersion() : null/*not installed*/;
	}
