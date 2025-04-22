	protected List<TabDescriptor> sortTabDescriptorsByCategory(List<TabDescriptor> descriptors) {
		Collections.sort(descriptors, (one, two) -> {
			String categoryOne = one.getCategory();
			String categoryTwo = two.getCategory();
			int categoryOnePosition = getIndex(propertyCategories.toArray(), categoryOne);
			int categoryTwoPosition = getIndex(propertyCategories.toArray(), categoryTwo);
			return categoryOnePosition - categoryTwoPosition;
