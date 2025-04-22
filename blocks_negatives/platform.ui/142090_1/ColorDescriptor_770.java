    /**
     * Returns the Color described by this descriptor.
     *
     * @param device SWT device on which to allocate the Color
     * @return a newly allocated SWT Color object (never null)
     * @throws DeviceResourceException if unable to allocate the Color
     */
    public abstract Color createColor(Device device) throws DeviceResourceException;
