
	private boolean isDefaultLocation(IPath path) {
		if (path.segmentCount() < 2)
			return false;
		return path.removeLastSegments(2).toFile()
				.equals(Platform.getLocation().toFile());
	}

