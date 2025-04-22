	private void executeNewPropertySheetHandler() throws Exception {
		String bundleId = "org.eclipse.ui.views";
		boolean activated = BundleUtility.isActivated(bundleId);
		if (!activated) {
			Bundle bundle = Platform.getBundle(bundleId);
			bundle.start();
		}
