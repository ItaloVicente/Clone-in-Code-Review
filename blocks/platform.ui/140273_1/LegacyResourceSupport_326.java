	public static Object getAdaptedResourceMapping(Object element) {
		Class resourceMappingClass = LegacyResourceSupport.getResourceMappingClass();
		Object adaptedValue = null;
		if (resourceMappingClass != null) {
			if (resourceMappingClass.isInstance(element)) {
				adaptedValue = element;
			} else {
				adaptedValue = LegacyResourceSupport.getAdaptedContributorResourceMapping(element);
			}
		}
		return adaptedValue;
	}

	private LegacyResourceSupport() {
	}
