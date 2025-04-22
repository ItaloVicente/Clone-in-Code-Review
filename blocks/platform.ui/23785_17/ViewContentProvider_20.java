	private Object[] determineTopLevelElements(Object element) {
		List<MPartDescriptor> descriptors = ((MApplication) element).getDescriptors();
		Set<String> categoryTags = new HashSet<String>();
		Set<MPartDescriptor> visibleViews = new HashSet<MPartDescriptor>();
		for (MPartDescriptor descriptor : descriptors) {
			if (!isView(descriptor) || isFilteredByActivity(descriptor.getElementId())) {
				continue;
			}
