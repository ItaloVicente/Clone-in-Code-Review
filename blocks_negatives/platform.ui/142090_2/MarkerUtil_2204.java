    /**
     * Don't allow instantiation.
     */
    private MarkerUtil() {
    }

    /**
     * Returns the ending character offset of the given marker.
     */
    static int getCharEnd(IMarker marker) {
        return marker.getAttribute(IMarker.CHAR_END, -1);
    }

    /**
     * Returns the starting character offset of the given marker.
     */
    static int getCharStart(IMarker marker) {
        return marker.getAttribute(IMarker.CHAR_START, -1);
    }

    /**
     * Returns the container name if it is defined, or empty string if not.
     */
    static String getContainerName(IMarker marker) {
        IPath path = marker.getResource().getFullPath();
        if (n <= 0) {
