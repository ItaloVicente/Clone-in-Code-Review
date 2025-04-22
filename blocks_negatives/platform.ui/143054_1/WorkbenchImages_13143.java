    /**
     * Declares a workbench image given the path of the image file (relative to
     * the workbench plug-in). This is a helper method that creates the image
     * descriptor and passes it to the main <code>declareImage</code> method.
     *
     * @param key the symbolic name of the image
     * @param path the path of the image file relative to the base of the workbench
     * plug-ins install directory
     * @param shared <code>true</code> if this is a shared image, and
     * <code>false</code> if this is not a shared image
     */
    private final static void declareImage(String key, String path,
            boolean shared) {
        URL url = BundleUtility.find(PlatformUI.PLUGIN_ID, path);
        ImageDescriptor desc = ImageDescriptor.createFromURL(url);
        declareImage(key, desc, shared);
    }
