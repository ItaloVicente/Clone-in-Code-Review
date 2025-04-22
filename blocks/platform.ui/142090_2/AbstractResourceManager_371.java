	private HashMap<DeviceResourceDescriptor, RefCount> map = null;

	private static class RefCount {
		Object resource;
		int count = 1;

		RefCount(Object resource) {
			this.resource = resource;
		}
	}

	protected abstract Object allocate(DeviceResourceDescriptor descriptor) throws DeviceResourceException;

	protected abstract void deallocate(Object resource, DeviceResourceDescriptor descriptor);

	@Override
