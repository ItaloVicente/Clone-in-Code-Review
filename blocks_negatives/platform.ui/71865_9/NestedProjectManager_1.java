		for (Entry<IPath, IProject> entry : locationsToProjects.tailMap(projectLocation)
				.entrySet()) {
			if (entry.getValue().equals(container.getProject())) {
			} else if (containerLocation.isPrefixOf(entry.getKey())) {
				if (entry.getKey().segmentCount() == containerLocation.segmentCount() + 1) {
					res.add(entry.getValue());
