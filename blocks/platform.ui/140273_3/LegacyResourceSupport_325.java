	public static Object getAdaptedContributorResourceMapping(Object object) {
		Class resourceMappingClass = LegacyResourceSupport.getResourceMappingClass();
		if (resourceMappingClass == null) {
			return null;
		}
		if (resourceMappingClass.isInstance(object)) {
			return null;
		}
		if (object instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) object;
			Class contributorResourceAdapterClass = LegacyResourceSupport.getIContributorResourceAdapterClass();
			if (contributorResourceAdapterClass == null) {
				return adaptable.getAdapter(resourceMappingClass);
			}
			Class contributorResourceAdapter2Class = LegacyResourceSupport.getIContributorResourceAdapter2Class();
			if (contributorResourceAdapter2Class == null) {
				return adaptable.getAdapter(resourceMappingClass);
			}
			Object resourceAdapter = adaptable.getAdapter(contributorResourceAdapterClass);
			Object resourceMappingAdapter;
