	/**
	 * Determines the category of the part descriptor
	 *
	 * @param descriptor
	 */
	private String getCategory(MPartDescriptor descriptor) {
		List<String> tags = descriptor.getTags();
		String category = null;
		for (String tag : tags) {
			if (tag.startsWith(CATEGORY_TAG)) {
				category = tag.substring(CATEGORY_TAG_LENGTH);
			}
		}
		return category;
	}
