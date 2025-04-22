	private List<Runnable> disposeExecs = null;

	public abstract Device getDevice();

	public abstract Object create(DeviceResourceDescriptor descriptor);

	public abstract void destroy(DeviceResourceDescriptor descriptor);

	public final Object get(DeviceResourceDescriptor descriptor) {
		Object result = find(descriptor);

		if (result == null) {
			result = create(descriptor);
		}

		return result;
	}

	public final Image createImage(ImageDescriptor descriptor) {
		Assert.isNotNull(descriptor);

		return (Image)create(descriptor);
	}

	public final Image createImageWithDefault(ImageDescriptor descriptor) {
		if (descriptor == null) {
			return getDefaultImage();
		}

		try {
