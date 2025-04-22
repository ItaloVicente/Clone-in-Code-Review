    /**
     * Returns whether the given marker is of the given type (either directly or indirectly).
     */
    private boolean isMarkerType(IMarker marker, String type) {
        try {
            return marker.isSubtypeOf(type);
        } catch (CoreException e) {
            return false;
        }
    }
