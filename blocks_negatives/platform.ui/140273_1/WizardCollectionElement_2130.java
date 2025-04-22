     * Returns the wizard collection child object corresponding to the passed
     * path (relative to this object), or <code>null</code> if such an object
     * could not be found.
     *
     * @param searchPath
     *            org.eclipse.core.runtime.IPath
     * @return WizardCollectionElement
     */
    public WizardCollectionElement findChildCollection(IPath searchPath) {
        String searchString = searchPath.segment(0);
