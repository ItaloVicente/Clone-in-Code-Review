	private String getSuggestedName() {
		String path = validatedRepoSelection.getURI().getPath();
		int s = path.lastIndexOf('/');
		if (s != -1)
			path = path.substring(s + 1);
			path = path.substring(0, path.length() - 4);
		return path;
	}

