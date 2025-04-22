				if (!result.contains(adapter)) {
					result.add(adapter);
				}
			}
		}
		return new ArrayList(result);
	}

	private Class getCommonResourceClass(List objects) {
		if (objects == null || objects.isEmpty()) {
			return null;
		}
		Class resourceClass = LegacyResourceSupport.getResourceClass();
		if (resourceClass == null) {
