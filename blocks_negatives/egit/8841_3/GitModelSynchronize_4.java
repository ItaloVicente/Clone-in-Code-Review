		return providerMappings.toArray(new ResourceMapping[providerMappings
				.size()]);
	}

	/**
	 * Copied from TeamAction#getResourceMapping(Object)
	 *
	 * @param object
	 * @return resource mapping
	 */
	private static Object getResourceMapping(Object object) {
		if (object instanceof ResourceMapping)
			return object;

		if (object instanceof IAdaptable)
			return ((IAdaptable) object).getAdapter(ResourceMapping.class);

		return null;
