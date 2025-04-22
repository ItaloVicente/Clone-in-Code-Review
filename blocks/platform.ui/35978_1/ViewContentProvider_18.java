		Object[] children = childMap.get(element);
		if (children == null) {
			children = createChildren(element);
			childMap.put(element, children);
		}
		return children;
	}

	private Object[] createChildren(Object element) {
		if (element instanceof MApplication) {
			return determineTopLevelElements(element).toArray();
		} else if (element instanceof String) {
			return determineViewsInCategory((String) element).toArray();
		}
		return new Object[0];
	}

	private Set<MPartDescriptor> determineViewsInCategory(String categoryDescription) {
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
		return categoryDescriptors;
	}

	private Set<Object> determineTopLevelElements(Object element) {
		List<MPartDescriptor> descriptors = ((MApplication) element).getDescriptors();
		Set<String> categoryTags = new HashSet<String>();
		Set<MPartDescriptor> visibleViews = new HashSet<MPartDescriptor>();
		for (MPartDescriptor descriptor : descriptors) {
			if (!isView(descriptor) || isFilteredByActivity(descriptor.getElementId())) {
				continue;
			}

			String category = getCategory(descriptor);

			if (category == null) {
				visibleViews.add(descriptor);
			} else {
				categoryTags.add(category);
			}
		}

		Set<Object> combinedTopElements = new HashSet<Object>();
		combinedTopElements.addAll(categoryTags);
		combinedTopElements.addAll(visibleViews);
		return combinedTopElements;
	}

