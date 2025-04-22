        OutputStream output = null;
        try {
            output = new FileOutputStream(versionFile);
            Properties props = new Properties();

            props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME, WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION.toString());

            props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME_LEGACY, WORKSPACE_CHECK_LEGACY_VERSION_INCREMENTED);

            props.store(output, null);
        } catch (IOException e) {
            IDEWorkbenchPlugin.log("Could not write version file", //$NON-NLS-1$
                    StatusUtil.newStatus(IStatus.ERROR, e.getMessage(), e));
        } finally {
            try {
                if (output != null) {
					output.close();
				}
            } catch (IOException e) {
            }
        }
    }

    /**
     * The version file is stored in the metadata area of the workspace. This
     * method returns an URL to the file or null if the directory or file does
     * not exist (and the create parameter is false).
     *
     * @param create
     *            If the directory and file does not exist this parameter
     *            controls whether it will be created.
     * @return An url to the file or null if the version file does not exist or
     *         could not be created.
     */
    private static File getVersionFile(URL workspaceUrl, boolean create) {
        if (workspaceUrl == null) {
