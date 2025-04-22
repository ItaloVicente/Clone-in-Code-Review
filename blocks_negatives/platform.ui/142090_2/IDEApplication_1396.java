        try {
            Properties props = new Properties();
            try (FileInputStream is = new FileInputStream(versionFile)) {
                props.load(is);
            }

            String versionString = props.getProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME);
            if (versionString != null) {
                return Version.parseVersion(versionString);
            }
            versionString= props.getProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME_LEGACY);
            if (versionString != null) {
                return Version.parseVersion(versionString);
            }
            return null;
        } catch (IOException e) {
            IDEWorkbenchPlugin.log("Could not read version file " + versionFile, new Status( //$NON-NLS-1$
                    IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH,
                    IStatus.ERROR,
                    e.getMessage() == null ? "" : e.getMessage(), //$NON-NLS-1$
                    e));
            return null;
        } catch (IllegalArgumentException e) {
            IDEWorkbenchPlugin.log("Could not parse version in " + versionFile, new Status( //$NON-NLS-1$
                    IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH,
                    IStatus.ERROR,
                    e.getMessage() == null ? "" : e.getMessage(), //$NON-NLS-1$
                    e));
            return null;
        }
    }

    /**
     * Write the version of the metadata into a known file overwriting any
     * existing file contents. Writing the version file isn't really crucial,
     * so the function is silent about failure
     */
    private static void writeWorkspaceVersion() {
        if (WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION == null) {
            return;
        }

        Location instanceLoc = Platform.getInstanceLocation();
        if (instanceLoc == null || instanceLoc.isReadOnly()) {
