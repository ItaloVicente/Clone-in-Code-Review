		final int length = resources.length;
		int actualLength = 0;
		String[] fileNames = new String[length];
		for (int i = 0; i < length; i++) {
			IPath location = resources[i].getLocation();
			if (location != null) {
