	}

	public static boolean isResourceType(String objectClassName) {
		for (String resourceClassName : resourceClassNames) {
			if (resourceClassName.equals(objectClassName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isResourceMappingType(String objectClassName) {
		return objectClassName.equals("org.eclipse.core.resources.mapping.ResourceMapping"); //$NON-NLS-1$
	}

	private static boolean isInstanceOf(Class clazz, String type) {
