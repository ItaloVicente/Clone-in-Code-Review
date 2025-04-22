    /**
     * Returns whether the types of the resources in the given selection are among
     * the specified resource types.
     *
     * @param selection the selection
     * @param resourceMask resource mask formed by bitwise OR of resource type
     *   constants (defined on <code>IResource</code>)
     * @return <code>true</code> if all selected elements are resources of the right
     *  type, and <code>false</code> if at least one element is either a resource
     *  of some other type or a non-resource
     * @see IResource#getType()
     */
    public static boolean allResourcesAreOfType(IStructuredSelection selection,
            int resourceMask) {
