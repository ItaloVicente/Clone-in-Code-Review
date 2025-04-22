
		IContainer resource = root.getContainerForLocation(new Path(file
				.getAbsolutePath()));

		if (resource == null)
			return null;

		if (isValid(resource))
			return resource;

