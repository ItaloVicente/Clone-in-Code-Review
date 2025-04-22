    /**
     * Return the resource that the supplied adaptable
     * adapts to. An IContributorResourceAdapter assumes
     * that any object passed to it adapts to one equivalent
     * resource.
     *
     * @param adaptable the adaptable being queried
     * @return a resource, or <code>null</code> if there
     * 	is no adapted resource for this type
     */
    public IResource getAdaptedResource(IAdaptable adaptable);
