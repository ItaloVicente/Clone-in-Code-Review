	public StatusCommand addPath(String path) {
		if (pathes == null)
			pathes = new LinkedList<String>();
		pathes.add(path);
		return this;
	}

	public List<String> getPaths() {
		return pathes;
	}

