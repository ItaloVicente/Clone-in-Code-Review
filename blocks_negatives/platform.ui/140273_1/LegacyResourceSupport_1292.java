     * Returns the adapted resource mapping using the <code>IContributorResourceAdapter2</code>
     * registered for the given object. If the Resources plug-in is not loaded
     * the object can not be adapted.
     *
     * @param object the object to adapt to <code>ResourceMapping</code>.
     * @return returns the adapted resource using the <code>IContributorResourceAdapter2</code>
     * or <code>null</code> if the Resources plug-in is not loaded.
     *
     * @since 3.1
     */
    public static Object getAdaptedContributorResourceMapping(Object object) {
        Class resourceMappingClass = LegacyResourceSupport.getResourceMappingClass();
        if (resourceMappingClass == null) {
            return null;
        }
        if (resourceMappingClass.isInstance(object)) {
            return null;
        }
        if (object instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) object;
            Class contributorResourceAdapterClass = LegacyResourceSupport.getIContributorResourceAdapterClass();
            if (contributorResourceAdapterClass == null) {
                return adaptable.getAdapter(resourceMappingClass);
            }
            Class contributorResourceAdapter2Class = LegacyResourceSupport.getIContributorResourceAdapter2Class();
            if (contributorResourceAdapter2Class == null) {
                return adaptable.getAdapter(resourceMappingClass);
            }
            Object resourceAdapter = adaptable.getAdapter(contributorResourceAdapterClass);
            Object resourceMappingAdapter;
