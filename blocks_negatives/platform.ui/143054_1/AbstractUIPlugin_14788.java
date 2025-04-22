        return dialogSettings;
    }

    /**
     * Returns the image registry for this UI plug-in.
     * <p>
     * The image registry contains the images used by this plug-in that are very
     * frequently used and so need to be globally shared within the plug-in. Since
     * many OSs have a severe limit on the number of images that can be in memory at
     * any given time, a plug-in should only keep a small number of images in their
     * registry.
     * <p>
     * Subclasses should reimplement <code>initializeImageRegistry</code> if they have
     * custom graphic images to load.
     * </p>
     * <p>
     * Subclasses may override this method but are not expected to.
     * </p>
     *
     * @return the image registry
     */
    public ImageRegistry getImageRegistry() {
        if (imageRegistry == null) {
            imageRegistry = createImageRegistry();
            initializeImageRegistry(imageRegistry);
        }
        return imageRegistry;
    }

    /**
     * Returns the preference store for this UI plug-in.
     * This preference store is used to hold persistent settings for this plug-in in
     * the context of a workbench. Some of these settings will be user controlled,
     * whereas others may be internal setting that are never exposed to the user.
     * <p>
     * If an error occurs reading the preference store, an empty preference store is
     * quietly created, initialized with defaults, and returned.
     * </p>
     * <p>
     * <strong>NOTE:</strong> As of Eclipse 3.1 this method is
     * no longer referring to the core runtime compatibility layer and so
     * plug-ins relying on Plugin#initializeDefaultPreferences
     * will have to access the compatibility layer themselves.
     * </p>
     *
     * @return the preference store
     */
    public IPreferenceStore getPreferenceStore() {
        if (preferenceStore == null) {
