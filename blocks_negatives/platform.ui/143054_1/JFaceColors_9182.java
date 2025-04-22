    /**
     * Clear out the cached color for name. This is generally
     * done when the color preferences changed and any cached colors
     * may be disposed. Users of the colors in this class should add a IPropertyChangeListener
     * to detect when any of these colors change.
     * @param colorName name of the color
     *
     * @deprecated JFaceColors no longer maintains a cache of colors.  This job
     * is now handled by the ColorRegistry.
     */
    @Deprecated
