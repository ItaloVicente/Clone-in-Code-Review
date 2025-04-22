    /**
     * Returns <code>false</code> if there are no resolutions for
     * the given marker. Returns <code>true</code> if their may
     * be resolutions. In most cases a <code>true</code> value
     * means there are resolutions but due to plugin loading
     * issues getResolutions may sometimes return an empty array
     * after this method returns <code>true</code>.
     *
     * @param marker the marker for which to determine if there
     * are resolutions
     * @return <code>true</code> if there may be resolutions
     * @since 2.0
     */
    public boolean hasResolutions(IMarker marker);
