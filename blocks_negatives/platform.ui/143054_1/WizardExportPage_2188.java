            setTypesToExport(result);
        }
    }

    /**
     * Returns whether the extension of the given resource name is an extension that
     * has been specified for export by the user.
     *
     * @param resourceName the resource name
     * @return <code>true</code> if the resource name is suitable for export based
     *   upon its extension
     */
    protected boolean hasExportableExtension(String resourceName) {
        if (selectedTypes == null) {
