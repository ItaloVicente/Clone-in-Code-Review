    /**
     * Creates a DeviceResourceException indicating an error attempting to
     * create a resource
     *
     * @param missingResource
     */
    public DeviceResourceException(DeviceResourceDescriptor missingResource) {
        this(missingResource, null);
    }
