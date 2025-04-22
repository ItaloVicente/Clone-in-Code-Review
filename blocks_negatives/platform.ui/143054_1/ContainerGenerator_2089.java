    /**
     * Creates a generator for the container resource (folder or project) at the
     * given workspace path. Assumes the path has already been validated.
     * <p>
     * Call <code>getContainer</code> to create any missing resources along the
     * path.
     * </p>
     *
     * @param containerPath the workspace path of the container
     */
    public ContainerGenerator(IPath containerPath) {
        super();
        this.containerFullPath = containerPath;
    }
