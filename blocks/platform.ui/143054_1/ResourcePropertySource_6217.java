	protected static String NOT_LOCAL_TEXT = IDEPropertiesMessages.PropertySource_notLocal;

	protected static String FILE_NOT_FOUND = IDEPropertiesMessages.PropertySource_notFound;

	protected static String UNDEFINED_PATH_VARIABLE = IDEPropertiesMessages.PropertySource_undefinedPathVariable;

	protected static String FILE_NOT_EXIST_TEXT = IDEPropertiesMessages.PropertySource_fileNotExist;

	protected IResource element;

	protected String errorMessage = IDEPropertiesMessages.PropertySource_readOnly;

	static protected IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[7];

	static protected IPropertyDescriptor[] propertyDescriptorsLinkVariable = new IPropertyDescriptor[8];
	static {
		PropertyDescriptor descriptor;

		descriptor = new PropertyDescriptor(IBasicPropertyConstants.P_TEXT,
				IResourcePropertyConstants.P_LABEL_RES);
		descriptor.setAlwaysIncompatible(true);
		descriptor
				.setCategory(IResourcePropertyConstants.P_FILE_SYSTEM_CATEGORY);
		propertyDescriptors[0] = descriptor;
		propertyDescriptorsLinkVariable[0] = descriptor;

		descriptor = new PropertyDescriptor(
				IResourcePropertyConstants.P_PATH_RES,
				IResourcePropertyConstants.P_DISPLAYPATH_RES);
		descriptor.setAlwaysIncompatible(true);
		descriptor
				.setCategory(IResourcePropertyConstants.P_FILE_SYSTEM_CATEGORY);
		propertyDescriptors[1] = descriptor;
		propertyDescriptorsLinkVariable[1] = descriptor;

		descriptor = new PropertyDescriptor(
				IResourcePropertyConstants.P_EDITABLE_RES,
				IResourcePropertyConstants.P_DISPLAYEDITABLE_RES);
		descriptor.setAlwaysIncompatible(true);
		descriptor
				.setCategory(IResourcePropertyConstants.P_FILE_SYSTEM_CATEGORY);
		propertyDescriptors[2] = descriptor;
		propertyDescriptorsLinkVariable[2] = descriptor;

		descriptor = new PropertyDescriptor(
				IResourcePropertyConstants.P_DERIVED_RES,
				IResourcePropertyConstants.P_DISPLAYDERIVED_RES);
		descriptor.setAlwaysIncompatible(true);
		descriptor
				.setCategory(IResourcePropertyConstants.P_FILE_SYSTEM_CATEGORY);
		propertyDescriptors[3] = descriptor;
		propertyDescriptorsLinkVariable[3] = descriptor;

		descriptor = new PropertyDescriptor(
				IResourcePropertyConstants.P_LAST_MODIFIED_RES,
				IResourcePropertyConstants.P_DISPLAY_LAST_MODIFIED);
		descriptor.setAlwaysIncompatible(true);
		descriptor
				.setCategory(IResourcePropertyConstants.P_FILE_SYSTEM_CATEGORY);
		propertyDescriptors[4] = descriptor;
		propertyDescriptorsLinkVariable[4] = descriptor;

		descriptor = new PropertyDescriptor(
				IResourcePropertyConstants.P_LINKED_RES,
				IResourcePropertyConstants.P_DISPLAYLINKED_RES);
		descriptor.setAlwaysIncompatible(true);
		descriptor
				.setCategory(IResourcePropertyConstants.P_FILE_SYSTEM_CATEGORY);
		propertyDescriptors[5] = descriptor;
		propertyDescriptorsLinkVariable[5] = descriptor;

		descriptor = new PropertyDescriptor(
				IResourcePropertyConstants.P_LOCATION_RES,
				IResourcePropertyConstants.P_DISPLAYLOCATION_RES);
		descriptor.setAlwaysIncompatible(true);
		descriptor
				.setCategory(IResourcePropertyConstants.P_FILE_SYSTEM_CATEGORY);
		propertyDescriptors[6] = descriptor;
		propertyDescriptorsLinkVariable[6] = descriptor;

		descriptor = new PropertyDescriptor(
				IResourcePropertyConstants.P_RESOLVED_LOCATION_RES,
				IResourcePropertyConstants.P_DISPLAYRESOLVED_LOCATION_RES);
		descriptor.setAlwaysIncompatible(true);
		descriptor
				.setCategory(IResourcePropertyConstants.P_FILE_SYSTEM_CATEGORY);
		propertyDescriptorsLinkVariable[7] = descriptor;

	}

	public ResourcePropertySource(IResource res) {
		Assert.isNotNull(res);
		this.element = res;
	}

	@Override
