	private static PropertyDescriptor fileDescriptor;
	static {
		fileDescriptor = new PropertyDescriptor(
				IResourcePropertyConstants.P_SIZE_RES,
				IResourcePropertyConstants.P_DISPLAY_SIZE);
		fileDescriptor.setAlwaysIncompatible(true);
		fileDescriptor
				.setCategory(IResourcePropertyConstants.P_FILE_SYSTEM_CATEGORY);
	}
