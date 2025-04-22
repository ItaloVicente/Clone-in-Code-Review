		synchronized (locationsToProjects) {
			locationsToProjects.clear();
			for (IProject project : knownProjects) {
				IPath location = project.getLocation();
				if (location != null) {
					locationsToProjects.put(location, project);
				}
