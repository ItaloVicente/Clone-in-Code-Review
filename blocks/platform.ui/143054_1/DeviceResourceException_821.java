	public DeviceResourceException(DeviceResourceDescriptor missingResource, Throwable cause) {
		super("Unable to create resource " + missingResource.toString()); //$NON-NLS-1$
		this.cause = cause;
	}

	public DeviceResourceException(DeviceResourceDescriptor missingResource) {
		this(missingResource, null);
	}

