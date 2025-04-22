        return null;
    }

    /**
     * Returns the container name if it is defined, or empty string if not.
     */
    public static String getContainerName(IMarker marker) {
        IPath path = marker.getResource().getFullPath();
        int n = path.segmentCount() - 1;
        if (n <= 0) {
