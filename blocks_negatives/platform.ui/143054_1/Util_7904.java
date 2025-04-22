    /**
     * Makes a type-safe copy of the given set. This method should be used when
     * a set is crossing an API boundary (i.e., from a hostile plug-in into
     * internal code, or vice versa).
     *
     * @param set
     *            The set which should be copied; must not be <code>null</code>.
     * @param c
     *            The class that all the values must be; must not be
     *            <code>null</code>.
     * @return A copy of the set; may be empty, but never <code>null</code>.
     *         None of its element will be <code>null</code>.
     */
