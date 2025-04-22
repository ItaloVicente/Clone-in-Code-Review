    /**
     * The marker type targetted by this query.
     * May be <code>null</code>.
     */
    private String type;

    /**
     * A sorted list of the attributes targetted by this query.
     * The list is sorted from least to greatest according to
     * <code>Sting.compare</code>
     */
    private String[] attributes;

    /**
     * Cached hash code value
     */
    private int hashCode;

    /**
     * Creates a new marker query with the given type
     * and attributes.
     * <p>
     * The type may be <code>null</code>. The attributes may
     * be empty, but not <code>null</code>.
     * </p>
     *
     * @param markerType the targetted marker type
     * @param markerAttributes the targetted marker attributes
     */
    public MarkerQuery(String markerType, String[] markerAttributes) {
        if (markerAttributes == null) {
            throw new IllegalArgumentException();
        }

        type = markerType;
        attributes = markerAttributes;
        computeHashCode();
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
            if (type != null && !type.equals(marker.getType())) {
				return null;
