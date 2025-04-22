    /**
     * Returns the image descriptor for the workbench image with the given
     * symbolic name. Use this method to retrieve image descriptors for any
     * of the images named in this class.
     *
     * @param symbolicName the symbolic name of the image
     * @return the image descriptor, or <code>null</code> if none
     */
    public static ImageDescriptor getImageDescriptor(String symbolicName) {
        return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
                symbolicName);
    }
