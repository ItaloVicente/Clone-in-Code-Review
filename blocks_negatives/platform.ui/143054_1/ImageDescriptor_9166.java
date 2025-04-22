    /**
     * Creates and returns a new image descriptor from a file.
     *
     * @param location the class whose resource directory contain the file
     * @param filename the file name
     * @return a new image descriptor
     */
    public static ImageDescriptor createFromFile(Class<?> location, String filename) {
        return new FileImageDescriptor(location, filename);
    }
