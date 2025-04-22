			IResource[] resources) {

		final List<String> names = new LinkedList<String>();
		for (IResource resource : resources) {
			if (resource.getName() != null) {
				names.add(resource.getName());
			}
		}

		final String resourceNames = StringUtils.join(names, ",", "&"); //$NON-NLS-1$ //$NON-NLS-2$

