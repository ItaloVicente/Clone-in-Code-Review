		IPath path = resource.getLocation();
		if (path != null) {
			File file = path.toFile();
			if (file != null) {
				return get(indexDiffData, file);
			}
