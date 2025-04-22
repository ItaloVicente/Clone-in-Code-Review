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
		return combinedTopElements.toArray();
	}

	private String getCategory(MPartDescriptor descriptor) {
		List<String> tags = descriptor.getTags();
		String category = null;
		for (String tag : tags) {
			if (tag.startsWith(CATEGORY_TAG)) {
				category = tag.substring(CATEGORY_TAG_LENGTH);
