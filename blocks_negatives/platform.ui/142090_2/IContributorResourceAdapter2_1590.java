    /**
     * Return the resource mapping that the supplied adaptable
     * adapts to. An <code>IContributorResourceAdapter2</code> assumes
     * that any object passed to it adapts to one equivalent
     * resource mapping.
     *
     * @param adaptable the adaptable being queried
     * @return a resource mapping, or <code>null</code> if there
     *  is no adapted resource mapping for this type
     */
    public ResourceMapping getAdaptedResourceMapping(IAdaptable adaptable);
