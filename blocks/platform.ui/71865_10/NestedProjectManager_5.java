		synchronized (locationsToProjects) {
			for (Entry<IPath, IProject> entry : locationsToProjects.tailMap(containerLocation).entrySet()) {
				if (entry.getValue().equals(container.getProject())) {
				} else if (containerLocation.isPrefixOf(entry.getKey())) {
					if (entry.getKey().segmentCount() == containerLocation.segmentCount() + 1) {
						return true;
					}
				} else { // moved to another branch, not worth continuing
					break;
