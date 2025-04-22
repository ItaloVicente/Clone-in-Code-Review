	protected int compareWorkspaceAndIdeVersions(URL url) {
		Version version = readWorkspaceVersion(url);
		if (version == null) {
			return 0;
		}

		final Version ide_version = toMajorMinorVersion(WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION);
		Version workspace_version = toMajorMinorVersion(version);
		int versionCompareResult = workspace_version.compareTo(ide_version);
		return versionCompareResult;
	}

	protected static Version readWorkspaceVersion(URL workspace) {
		File versionFile = getVersionFile(workspace, false);
		if (versionFile == null || !versionFile.exists()) {
