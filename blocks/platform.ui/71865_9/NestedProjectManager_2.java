		synchronized (locationsToProjects) {
			while (queriedLocation.segmentCount() > 0) {
				if (locationsToProjects.containsKey(queriedLocation)) {
					return true;
				}
				queriedLocation = queriedLocation.removeLastSegments(1);
