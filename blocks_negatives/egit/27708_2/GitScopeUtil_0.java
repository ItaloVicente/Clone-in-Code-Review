	private static ResourceMapping getResourceMapping(Object o) {
		if (o instanceof ResourceMapping)
			return (ResourceMapping) o;
		if (o instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) o;
			Object adapted = adaptable.getAdapter(ResourceMapping.class);
			if (adapted instanceof ResourceMapping)
				return (ResourceMapping) adapted;
			adapted = adaptable.getAdapter(IContributorResourceAdapter.class);
			if (adapted instanceof IContributorResourceAdapter2) {
				IContributorResourceAdapter2 cra = (IContributorResourceAdapter2) adapted;
				return cra.getAdaptedResourceMapping(adaptable);
			}
		} else {
			Object adapted = Platform.getAdapterManager().getAdapter(o,
					ResourceMapping.class);
			if (adapted instanceof ResourceMapping)
				return (ResourceMapping) adapted;
		}
		return null;
	}

