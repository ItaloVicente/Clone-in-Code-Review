    /**
     * Returns whether the type of the given resource is among the specified
     * resource types.
     *
     * @param resource the resource
     * @param resourceMask resource mask formed by bitwise OR of resource type
     *   constants (defined on <code>IResource</code>)
     * @return <code>true</code> if the resources has a matching type, and
     *   <code>false</code> otherwise
     * @see IResource#getType()
     */
    public static boolean resourceIsType(IResource resource, int resourceMask) {
        return (resource.getType() & resourceMask) != 0;
    }
