        if (WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION == null) {
            return true;
        }

        Version version = readWorkspaceVersion(url);
        if (version == null) {
