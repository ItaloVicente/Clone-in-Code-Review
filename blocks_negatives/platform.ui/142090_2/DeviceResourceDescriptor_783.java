    /**
     * Creates the resource described by this descriptor
     *
     * @since 3.1
     *
     * @param device the Device on which to allocate the resource
     * @return the newly allocated resource (not null)
     * @throws DeviceResourceException if unable to allocate the resource
     */
    public abstract Object createResource(Device device);
