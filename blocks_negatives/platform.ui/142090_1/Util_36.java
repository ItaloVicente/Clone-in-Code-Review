    /**
     * Makes a type-safe copy of the given map. This method should be used when
     * a map is crossing an API boundary (i.e., from a hostile plug-in into
     * internal code, or vice versa).
     *
     * @param map
     *            The map which should be copied; must not be <code>null</code>.
     * @param keyClass
     *            The class that all the keys must be; must not be
     *            <code>null</code>.
     * @param valueClass
     *            The class that all the values must be; must not be
     *            <code>null</code>.
     * @param allowNullKeys
     *            Whether <code>null</code> keys should be allowed.
     * @param allowNullValues
     *            Whether <code>null</code> values should be allowed.
     * @return A copy of the map; may be empty, but never <code>null</code>.
     */
