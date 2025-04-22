    /**
     * Runs the operation created in <code>createOperation</code>
     *
     * @param resources source resources to pass to the operation
     * @param destination destination container to pass to the operation
     */
    protected void runOperation(IResource[] resources, IContainer destination) {
        operation.copyResources(resources, destination);
    }
