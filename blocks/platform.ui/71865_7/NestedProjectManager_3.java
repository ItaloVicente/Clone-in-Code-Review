		synchronized (locationsToProjects) {
			while (mostDirectParentProject == null && queriedLocation.segmentCount() > 0) {
				mostDirectParentProject = locationsToProjects.get(queriedLocation);
				if (mostDirectParentProject != null && mostDirectParentProject.getLocation() == null) {
					mostDirectParentProject = null;
				}
				queriedLocation = queriedLocation.removeLastSegments(1);
