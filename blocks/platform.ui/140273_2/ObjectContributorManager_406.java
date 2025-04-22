
		List testList = new ArrayList(objects.size());

		for (int i = 0; i < objects.size(); i++) {
			Object object = objects.get(i);

			if (object instanceof IAdaptable) {
				if (resourceClass.isInstance(object)) {
					continue;
				}

				Object resource = LegacyResourceSupport.getAdaptedContributorResource(object);

				if (resource == null) {
					return null;
				}
				testList.add(resource);
			} else {
				return null;
			}
		}

		return getCommonClass(testList);
	}

	private Class getResourceMappingClass(List objects) {
		if (objects == null || objects.isEmpty()) {
			return null;
		}
		Class resourceMappingClass = LegacyResourceSupport.getResourceMappingClass();
		if (resourceMappingClass == null) {
			return null;
		}

		for (int i = 0; i < objects.size(); i++) {
			Object object = objects.get(i);

			if (object instanceof IAdaptable) {
				if (resourceMappingClass.isInstance(object)) {
					continue;
				}

				Object resourceMapping = LegacyResourceSupport.getAdaptedContributorResourceMapping(object);

				if (resourceMapping == null) {
					return null;
				}
			} else {
				return null;
			}
		}
		return resourceMappingClass;
	}

	private Class getCommonClass(List objects) {
		if (objects == null || objects.isEmpty()) {
			return null;
		}
		Class commonClass = objects.get(0).getClass();
		if (objects.size() == 1) {
