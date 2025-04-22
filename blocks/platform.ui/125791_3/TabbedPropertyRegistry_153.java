	protected List sortTabDescriptorsByCategory(List descriptors) {
		Collections.sort(descriptors, new Comparator() {

			@Override
			public int compare(Object arg0, Object arg1) {
				TabDescriptor one = (TabDescriptor) arg0;
				TabDescriptor two = (TabDescriptor) arg1;
				String categoryOne = one.getCategory();
				String categoryTwo = two.getCategory();
				int categoryOnePosition = getIndex(
						propertyCategories.toArray(), categoryOne);
				int categoryTwoPosition = getIndex(
						propertyCategories.toArray(), categoryTwo);
				return categoryOnePosition - categoryTwoPosition;
			}
