		} else {
			if (LegacyResourceSupport.isResourceType(objectClass)) {
				adaptedElement = getAdaptedResource(element);
			} else if (LegacyResourceSupport.isResourceMappingType(objectClass)) {
				adaptedElement = getAdaptedResourceMapping(element);
				if (adaptedElement == null) {
					Object resource = getAdaptedResource(element);
					if (resource != null) {
						adaptedElement = ((IAdaptable) resource)
								.getAdapter(LegacyResourceSupport.getResourceMappingClass());
					}
