        Version version = readWorkspaceVersion(url);
        if (version == null) {
			return true;
		}

        final Version ide_version = toMajorMinorVersion(WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION);
        Version workspace_version = toMajorMinorVersion(version);
        int versionCompareResult = workspace_version.compareTo(ide_version);
