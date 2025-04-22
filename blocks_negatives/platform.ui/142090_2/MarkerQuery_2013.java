    }

    /**
     * Performs a query against the given marker.
     * <p>
     * Returns a <code>MarkerQueryResult</code> if the marker
     * is appropriate for this query (correct type and has
     * all of the query attributes), otherwise <code>null</code>
     * is returned.
     *
     * @param marker the marker to perform the query against
     * @return a marker query result or <code>null</code>
     */
    public MarkerQueryResult performQuery(IMarker marker) {
        try {
