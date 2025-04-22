		for (IProject other : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (!project.equals(other) && other.isOpen()) {
				IPath otherLocation = other.getLocation();
				if ((mostDirectParentProject == null || otherLocation.segmentCount() > mostDirectParentProject.getLocation().segmentCount())
					&& other.getLocation().isPrefixOf(project.getLocation())) {
					mostDirectParentProject = other;
				}
