     * Deallocates a resource previously allocated by {@link #create(DeviceResourceDescriptor)}. 
     * Descriptors are compared by equality, not identity. If the same resource was 
     * created multiple times, this may decrement a reference count rather than 
     * disposing the actual resource.  
     * 
     * @since 3.1 
