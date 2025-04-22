    /**
     * Check the children of the container to see if they are read only.
     * @return int
     * one of
     * 	YES_TO_ALL_ID - all elements were selected
     * 	NO_ID - No was hit at some point
     * 	CANCEL_ID - cancel was hit
     * @param itemsToCheck IResource[]
     * @param allSelected the List of currently selected resources to add to.
     */
    private int checkReadOnlyResources(IResource[] itemsToCheck,
            List allSelected) throws CoreException {
