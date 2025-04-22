    /**
     * Map of ResourceDescriptor onto RefCount. (null when empty)
     */
    private HashMap<DeviceResourceDescriptor, RefCount> map = null;

    /**
     * Holds a reference count for a previously-allocated resource
     */
    private static class RefCount {
        Object resource;
        int count = 1;

        RefCount(Object resource) {
            this.resource = resource;
        }
    }

    /**
     * Called the first time a resource is requested. Should allocate and return a resource
     * of the correct type.
     *
     * @since 3.1
     *
     * @param descriptor identifier for the resource to allocate
     * @return the newly allocated resource
     * @throws DeviceResourceException Thrown when allocation of an SWT device resource fails
     */
    protected abstract Object allocate(DeviceResourceDescriptor descriptor) throws DeviceResourceException;

    /**
     * Called the last time a resource is dereferenced. Should release any resources reserved by
     * allocate(...).
     *
     * @since 3.1
     *
     * @param resource resource being deallocated
     * @param descriptor identifier for the resource
     */
    protected abstract void deallocate(Object resource, DeviceResourceDescriptor descriptor);

    @Override
