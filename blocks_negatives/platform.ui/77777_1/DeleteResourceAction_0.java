		if (resources.isEmpty()) {
			return false;
		}
		for (int i = 0; i < resources.size(); i++) {
			IResource resource = resources.get(i);
			if (resource.isPhantom()) {
