
	public static String getResourceNames(Iterable<IResource> resources) {
		final List<String> names = new LinkedList<>();
		for (IResource resource : resources) {
			if (resource.getName() != null) {
				names.add(resource.getName());
			}
		}

		return StringUtils.join(names, ", ", " & "); //$NON-NLS-1$ //$NON-NLS-2$
	}
