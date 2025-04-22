	private Object[] createChildren(Object element) {
		if (element instanceof MApplication) {
			return determineTopLevelElements(element);
		} else if (element instanceof String) {
			return determineViewsInCategory((String) element);
		}
		return new Object[0];
	}

	private Object[] determineViewsInCategory(String categoryDescription) {
		List<MPartDescriptor> descriptors = application.getDescriptors();
		Set<MPartDescriptor> categoryDescriptors = new HashSet<MPartDescriptor>();
		for (MPartDescriptor descriptor : descriptors) {
			if (isFilteredByActivity(descriptor.getElementId()) || isIntroView(descriptor.getElementId())) {
				continue;
			}
			String categoryTag = getCategory(descriptor);
			if (categoryDescription.equals(categoryTag)) {
				categoryDescriptors.add(descriptor);
			}
		}
		return categoryDescriptors.toArray();
	}
