		IContainer dir = root.getContainerForLocation(location);
		if (dir == null) {
			return null;
		}
		if (isValid(dir)) {
			return dir;
		}
