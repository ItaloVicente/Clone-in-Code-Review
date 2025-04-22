    /**
     * Returns the selection adapted to IResource. Returns null
     * if any of the entries are not adaptable.
     *
     * @param selection the selection
     * @param resourceMask resource mask formed by bitwise OR of resource type
     *   constants (defined on <code>IResource</code>)
     * @return IStructuredSelection or null if any of the entries are not adaptable.
     * @see IResource#getType()
     */
    public static IStructuredSelection allResources(
            IStructuredSelection selection, int resourceMask) {
        Iterator adaptables = selection.iterator();
        List result = new ArrayList();
        while (adaptables.hasNext()) {
            Object next = adaptables.next();
            if (next instanceof IAdaptable) {
                Object resource = ((IAdaptable) next)
                        .getAdapter(IResource.class);
                if (resource == null) {
					return null;
				} else if (resourceIsType((IResource) resource, resourceMask)) {
					result.add(resource);
				}
            } else {
