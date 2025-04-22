	private Object[] createChildren(Object element) {
		if (element instanceof MApplication) {
			List<MPartDescriptor> descriptors = ((MApplication) element).getDescriptors();
			Set<String> categoryTags = new HashSet<String>();
			Set<MPartDescriptor> visibleViews = new HashSet<MPartDescriptor>();
			for (MPartDescriptor descriptor : descriptors) {
				if (isFilteredByActivity(descriptor.getElementId())) {
