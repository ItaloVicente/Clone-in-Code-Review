    private List<Runnable> disposeExecs = null;

    /**
     * Returns the Device for which this ResourceManager will create resources
     *
     * @since 3.1
     *
     * @return the Device associated with this ResourceManager
     */
    public abstract Device getDevice();

    /**
     * Returns the resource described by the given descriptor. If the resource already
     * exists, the reference count is incremented and the exiting resource is returned.
     * Otherwise, a new resource is allocated. Every call to this method should have
     * a corresponding call to {@link #destroy(DeviceResourceDescriptor)}.
     *
     * <p>If the resource is intended to live for entire lifetime of the resource manager,
     * a subsequent call to {@link #destroy(DeviceResourceDescriptor)} may be omitted and the
     * resource will be cleaned up when the resource manager is disposed. This pattern
     * is useful for short-lived {@link LocalResourceManager}s, but should never be used
     * with the global resource manager since doing so effectively leaks the resource.</p>
     *
     * <p>The resources returned from this method are reference counted and may be shared
     * internally with other resource managers. They should never be disposed outside of the
     * ResourceManager framework, or it will cause exceptions in other code that shares
     * them. For example, never call {@link org.eclipse.swt.graphics.Resource#dispose()}
     * on anything returned from this method.</p>
     *
     * <p>Callers may safely downcast the result to the resource type associated with
     * the descriptor. For example, when given an ImageDescriptor, the return
     * value of this method will always be an Image.</p>
     *
     * @since 3.1
     *
     * @param descriptor descriptor for the resource to allocate
     * @return the newly allocated resource (not null)
     * @throws DeviceResourceException if unable to allocate the resource
     */
    public abstract Object create(DeviceResourceDescriptor descriptor);

    /**
     * Deallocates a resource previously allocated by {@link #create(DeviceResourceDescriptor)}.
     * Descriptors are compared by equality, not identity. If the same resource was
     * created multiple times, this may decrement a reference count rather than
     * disposing the actual resource.
     *
     * @since 3.1
     *
     * @param descriptor identifier for the resource
     */
    public abstract void destroy(DeviceResourceDescriptor descriptor);

    /**
     * <p>Returns a previously-allocated resource or allocates a new one if none
     * exists yet. The resource will remain allocated for at least the lifetime
     * of this resource manager. If necessary, the resource will be deallocated
     * automatically when the resource manager is disposed.</p>
     *
     * <p>The resources returned from this method are reference counted and may be shared
     * internally with other resource managers. They should never be disposed outside of the
     * ResourceManager framework, or it will cause exceptions in other code that shares
     * them. For example, never call {@link org.eclipse.swt.graphics.Resource#dispose()}
     * on anything returned from this method.</p>
     *
     * <p>
     * Callers may safely downcast the result to the resource type associated with
     * the descriptor. For example, when given an ImageDescriptor, the return
     * value of this method may be downcast to Image.
     * </p>
     *
     * <p>
     * This method should only be used for resources that should remain
     * allocated for the lifetime of the resource manager. To allocate shorter-lived
     * resources, manage them with <code>create</code>, and <code>destroy</code>
     * rather than this method.
     * </p>
     *
     * <p>
     * This method should never be called on the global resource manager,
     * since all resources will remain allocated for the lifetime of the app and
     * will be effectively leaked.
     * </p>
     *
     * @param descriptor identifier for the requested resource
     * @return the requested resource. Never null.
     * @throws DeviceResourceException if the resource does not exist yet and cannot
     * be created for any reason.
     *
     * @since 3.3
     */
    public final Object get(DeviceResourceDescriptor descriptor) {
    	Object result = find(descriptor);

    	if (result == null) {
    		result = create(descriptor);
    	}

    	return result;
    }

    /**
     * <p>Creates an image, given an image descriptor. Images allocated in this manner must
     * be disposed by {@link #destroyImage(ImageDescriptor)}, and never by calling
     * {@link Image#dispose()}.</p>
     *
     * <p>
     * If the image is intended to remain allocated for the lifetime of the ResourceManager,
     * the call to destroyImage may be omitted and the image will be cleaned up automatically
     * when the ResourceManager is disposed. This should only be done with short-lived ResourceManagers,
     * as doing so with the global manager effectively leaks the resource.
     * </p>
     *
     * @since 3.1
     *
     * @param descriptor descriptor for the image to create
     * @return the Image described by this descriptor (possibly shared by other equivalent
     * ImageDescriptors)
     * @throws DeviceResourceException if unable to allocate the Image
     */
    public final Image createImage(ImageDescriptor descriptor) {
    	Assert.isNotNull(descriptor);

        return (Image)create(descriptor);
    }

    /**
     * Creates an image, given an image descriptor. Images allocated in this manner must
     * be disposed by {@link #destroyImage(ImageDescriptor)}, and never by calling
     * {@link Image#dispose()}.
     *
     * @since 3.1
     *
     * @param descriptor descriptor for the image to create
     * @return the Image described by this descriptor (possibly shared by other equivalent
     * ImageDescriptors)
     */
    public final Image createImageWithDefault(ImageDescriptor descriptor) {
        if (descriptor == null) {
        	return getDefaultImage();
        }

        try {
