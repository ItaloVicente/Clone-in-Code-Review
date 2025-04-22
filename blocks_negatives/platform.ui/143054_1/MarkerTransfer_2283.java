    /**
     * Singleton instance.
     */
    private static final MarkerTransfer instance = new MarkerTransfer();


    private static final int TYPEID = registerType(TYPE_NAME);

    private IWorkspace workspace;

    /**
     * Creates a new transfer object.
     */
    private MarkerTransfer() {
    }

    /**
     * Locates and returns the marker associated with the given attributes.
     *
     * @param pathString the resource path
     * @param id the id of the marker to get (as per {@link IResource#getMarker
     *    IResource.getMarker})
     * @return the specified marker
     */
    private IMarker findMarker(String pathString, long id) {
        IPath path = new Path(pathString);
        IResource resource = workspace.getRoot().findMember(path);
        if (resource != null) {
            return resource.getMarker(id);
        }
        return null;
    }

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance
     */
    public static MarkerTransfer getInstance() {
        return instance;
    }

    @Override
