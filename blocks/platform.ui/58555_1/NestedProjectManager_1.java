		IPath location = folder.getLocation();
		if (location == null) {
			return null;
		}
		IProject res = locationsToProjects.get(location);
		if (res != null && (!res.exists() || !location.equals(res.getLocation()))) {
