    /**
     * Returns the shared missing image descriptor instance.
     *
     * @return the image descriptor for a missing image
     */
    static MissingImageDescriptor getInstance() {
        if (instance == null) {
            instance = new MissingImageDescriptor();
        }
        return instance;
    }
