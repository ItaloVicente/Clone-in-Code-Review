        return true;
    }

    /**
     * Returns a new subcollection containing only those resources which are not
     * local.
     *
     * @param originalList the original list of resources (element type:
     *   <code>IResource</code>)
     * @return the new list of non-local resources (element type:
     *   <code>IResource</code>)
     */
    protected List extractNonLocalResources(List originalList) {
        Vector result = new Vector(originalList.size());
        Iterator resourcesEnum = originalList.iterator();

        while (resourcesEnum.hasNext()) {
            IResource currentResource = (IResource) resourcesEnum.next();
            if (!currentResource.isLocal(IResource.DEPTH_ZERO)) {
				result.addElement(currentResource);
