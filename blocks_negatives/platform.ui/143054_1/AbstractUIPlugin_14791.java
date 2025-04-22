
        loadPreferenceStore();
        initializeDefaultPreferences(getPreferenceStore());
    }

    /**
     * Initializes an image registry with images which are frequently used by the
     * plugin.
     * <p>
     * The image registry contains the images used by this plug-in that are very
     * frequently used and so need to be globally shared within the plug-in. Since
     * many OSs have a severe limit on the number of images that can be in memory
     * at any given time, each plug-in should only keep a small number of images in
     * its registry.
     * </p><p>
     * Implementors should create a JFace image descriptor for each frequently used
     * image.  The descriptors describe how to create/find the image should it be needed.
     * The image described by the descriptor is not actually allocated until someone
     * retrieves it.
     * </p><p>
     * Subclasses may override this method to fill the image registry.
     * </p>
     * @param reg the registry to initialize
     *
     * @see #getImageRegistry
     */
    protected void initializeImageRegistry(ImageRegistry reg) {
    }

    /**
