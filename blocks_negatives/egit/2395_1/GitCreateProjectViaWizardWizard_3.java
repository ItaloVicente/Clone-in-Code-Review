		for (IProject current : currentProjects) {
			boolean found = false;
			for (IProject previous : previousProjects) {
				if (previous.equals(current)) {
					found = true;
					break;
				}
			}
			if (!found) {
				newProjects.add(current);
