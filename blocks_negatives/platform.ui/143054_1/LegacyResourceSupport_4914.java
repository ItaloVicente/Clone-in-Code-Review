    /**
     * Adapt the given element to an <code>ResourceMapping</code> using the following
     * search order:
     * <ol>
     * <li> using the IContributorResourceAdapter2 registered for the given element, or
     * <li> directly asking the element if it adapts.
     * </ol>
     *
     * @param element the element to adapt
     * @return an <code>ResourceMapping</code> instance if the element could be adapted or <code>null</code>
     * otherwise.
     * @since 3.1
     */
    public static Object getAdaptedResourceMapping(Object element) {
        Class resourceMappingClass = LegacyResourceSupport.getResourceMappingClass();
        Object adaptedValue = null;
        if (resourceMappingClass != null) {
            if (resourceMappingClass.isInstance(element)) {
                adaptedValue = element;
            } else {
                adaptedValue = LegacyResourceSupport.getAdaptedContributorResourceMapping(element);
            }
        }
        return adaptedValue;
    }

    /**
     * Prevents construction
     */
    private LegacyResourceSupport() {
    }
