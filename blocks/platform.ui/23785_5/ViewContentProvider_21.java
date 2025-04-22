			Set<Object> combinedTopElements = new HashSet<Object>();
			combinedTopElements.addAll(categoryTags);
			combinedTopElements.addAll(visibleViews);
			return combinedTopElements.toArray();
		} else if (element instanceof String) {
			List<MPartDescriptor> descriptors = application.getDescriptors();
			Set<MPartDescriptor> categoryDescriptors = new HashSet<MPartDescriptor>();
			for (MPartDescriptor descriptor : descriptors) {
				if (isFilteredByActivity(descriptor.getElementId())) {
					continue;
				}
				List<String> tags = descriptor.getTags();
				for (String tag : tags) {
					if (!tag.startsWith(CATEGORY_TAG))
						continue;
					String categoryTag = tag.substring(CATEGORY_TAG_LENGTH);
					if (element.equals(categoryTag))
						categoryDescriptors.add(descriptor);
				}
			}
			return categoryDescriptors.toArray();
		}
		return new Object[0];
	}

	private boolean isFilteredByActivity(String elementId) {
		IViewDescriptor[] views = viewRegistry.getViews();
		for (IViewDescriptor descriptor : views) {
			if (descriptor.getId().equals(elementId)
					&& WorkbenchActivityHelper.filterItem(descriptor)) {
