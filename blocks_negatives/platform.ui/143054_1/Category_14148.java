    	String unparsedPath = getRawParentPath();
        if (unparsedPath != null) {
            StringTokenizer stok = new StringTokenizer(unparsedPath, "/"); //$NON-NLS-1$
            parentPath = new String[stok.countTokens()];
            for (int i = 0; stok.hasMoreTokens(); i++) {
                parentPath[i] = stok.nextToken();
            }
        }

        return parentPath;
    }

    /**
     * Return the unparsed parent path.  May be <code>null</code>.
     *
     * @return the unparsed parent path or <code>null</code>
     */
    public String getRawParentPath() {
        return configurationElement == null ? null
                : configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_PARENT_CATEGORY);
    }

    /**
     * Return the root path for this category.
     *
     * @return the root path
     */
    public String getRootPath() {
        String[] path = getParentPath();
        if (path != null && path.length > 0) {
