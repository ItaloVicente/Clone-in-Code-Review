    /**
     * Creates the Font described by this descriptor.
     *
     * @since 3.1
     *
     * @param device device on which to allocate the font
     * @return a newly allocated Font (never null)
     * @throws DeviceResourceException if unable to allocate the Font
     */
    public abstract Font createFont(Device device) throws DeviceResourceException;
