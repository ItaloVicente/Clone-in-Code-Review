		return (IThemeDescriptor) findDescriptor(getThemes(), id);
	}

	private IThemeElementDefinition findDescriptor(IThemeElementDefinition[] descriptors, String id) {
		int idx = Arrays.binarySearch(descriptors, id, ID_COMPARATOR);
		if (idx < 0) {
