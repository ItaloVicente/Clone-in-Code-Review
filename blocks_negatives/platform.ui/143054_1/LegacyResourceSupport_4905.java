    /**
     * Returns the adapted resource using the <code>IContributorResourceAdapter</code>
     * registered for the given object. If the Resources plug-in is not loaded
     * the object can not be adapted.
     *
     * @param object the object to adapt to <code>IResource</code>.
     * @return returns the adapted resource using the <code>IContributorResourceAdapter</code>
     * or <code>null</code> if the Resources plug-in is not loaded.
     *
     * @since 3.1
     */
    public static Object getAdaptedContributorResource(Object object) {
