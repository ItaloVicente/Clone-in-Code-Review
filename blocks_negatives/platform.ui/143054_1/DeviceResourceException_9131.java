    public DeviceResourceException(DeviceResourceDescriptor missingResource, Throwable cause) {
        this.cause = cause;
    }

    /**
     * Creates a DeviceResourceException indicating an error attempting to
     * create a resource
     *
     * @param missingResource
     */
    public DeviceResourceException(DeviceResourceDescriptor missingResource) {
        this(missingResource, null);
    }

    /**
     * Returns the cause of this throwable or <code>null</code> if the
     * cause is nonexistent or unknown.
     *
     * @return the cause or <code>null</code>
     * @since 3.1
     */
    @Override
