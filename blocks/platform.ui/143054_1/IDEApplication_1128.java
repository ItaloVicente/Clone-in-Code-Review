		File versionFile = getVersionFile(instanceLoc.getURL(), true);
		if (versionFile == null) {
			return;
		}

		Properties props = new Properties();

		props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME, WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION.toString());

		props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME_LEGACY, WORKSPACE_CHECK_LEGACY_VERSION_INCREMENTED);

		try (OutputStream output = new FileOutputStream(versionFile)) {
			props.store(output, null);
		} catch (IOException e) {
			IDEWorkbenchPlugin.log("Could not write version file", //$NON-NLS-1$
					StatusUtil.newError(e));
		}
	}

	protected static File getVersionFile(URL workspaceUrl, boolean create) {
		if (workspaceUrl == null) {
